package searchengine.service.database;


import io.restassured.RestAssured;
import lombok.SneakyThrows;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Lemma;
import searchengine.entity.Page;
import searchengine.entity.Site;
import searchengine.entity.Status;
import searchengine.repository.IndexesRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.services.datebase.DateBaseService;

import java.net.MalformedURLException;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@DisplayName("Тестирование базы данных")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class DataBaseServiceTest {
    @LocalServerPort
    private Integer port;
    @Container
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16.2"
    );

    @BeforeEach
    public void beforeAll() {
        postgres.start();
    }


    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }


    @Autowired
    private DateBaseService dateBaseService;
    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private LemmaRepository lemmaRepository;
    @Autowired
    private IndexesRepository indexesRepository;


    @BeforeEach
    void setUp() throws MalformedURLException {
        dateBaseService.deleterAll();
        RestAssured.baseURI = "http://localhost:" + port;
        createDataBase();
    }

    @Test
    @Order(1)
    @DisplayName("Тестирование создания базы данных")
    public void createPageTest() {
        assertThat(1L, equalTo(dateBaseService.countSite()));
        assertThat(2L, equalTo(dateBaseService.countPage()));
        assertThat(14L, equalTo(dateBaseService.countLemma()));
    }

    @Test
    @Order(2)
    @DisplayName("Тестирование обновления сайта")
    public void updateStatusAndErrorSiteTest() {
        Site site = siteRepository.findByName("test");
        dateBaseService.updateStatusAndErrorSite(site, Status.FAILED, "error 0 pages");
        assertThat(Status.FAILED, equalTo(siteRepository.findByName("test").getStatus()));
        assertThat("error 0 pages", equalTo(siteRepository.findByName("test").getLastError()));
    }

    @Test
    @Order(3)
    @DisplayName("Тестирование колличество сайтов по статусу")
    public void countSitesByStatusTest() {
        assertThat(1, equalTo(dateBaseService.countSitesByStatus(Status.INDEXING)));
    }

    @Test
    @Order(4)
    @DisplayName("Тестирование поиска всех сайтов в базе")
    public void getAllSitesTest() {
        assertThat(1, equalTo(dateBaseService.getAllSites().size()));
    }

    @Test
    @Order(5)
    @DisplayName("Тестирование колличество сайтов по id сайта")
    public void countPageBySiteIdTest() {
        Site site = siteRepository.findByName("test");
        assertThat(2, equalTo(dateBaseService.countPageBySiteId(site.getId())));
    }

    @Test
    @Order(6)
    @DisplayName("Тестирование колличество лемм по id сайта")
    public void countLemmaBySiteIdTest() {
        Site site = siteRepository.findByName("test");
        assertThat(14, equalTo(dateBaseService.countLemmaBySiteId(site.getId())));
    }


    @Test
    @Order(7)
    @SneakyThrows
    @DisplayName("Тестирование получение страницы по пути сайта")
    public void findPageByUrlTest() {
        Page page = dateBaseService.findPageByUrl("https://музей-ямщика.рф");
        assertThat("Повторное появление леопарда в Осетии позволяет предположить,что леопард постоянно обитает в некоторых районах Северного Кавказа.Кавказа.", equalTo(page.getContent()));
    }

    @Test
    @Order(8)
    @SneakyThrows
    @DisplayName("Тестирование получение списка лемм по станице")
    public void findLemmaByPageIdTest() {
        Page page = dateBaseService.findPageByUrl("https://музей-ямщика.рф");
        assertThat("повторный", equalTo(dateBaseService.findLemmaByPageId(page).get(0).getLemma()));
    }

    @Test
    @Order(9)
    @SneakyThrows
    @DisplayName("Тестирование удаление отдельной страницы")
    public void deleteOnePageTest() {
        Page page = dateBaseService.findPageByUrl("https://музей-ямщика.рф");
        List<Lemma> lemmas = dateBaseService.findLemmaByPageId(page);
        dateBaseService.deleteIndexesByLemma(page);
        dateBaseService.deleteLemmaByPage(lemmas);
        dateBaseService.deletePageByPath("https://музей-ямщика.рф");
        assertThat(1L, equalTo(pageRepository.count()));
        assertThat(1L, equalTo(lemmaRepository.count()));
        assertThat(1L, equalTo(indexesRepository.count()));
    }

    @Test
    @Order(10)
    @DisplayName("Тестирование полного удаления базы данных")
    public void deleteAllTest() throws MalformedURLException {
        dateBaseService.deleterAll();
        createDataBase();
        dateBaseService.deleterAll();
        assertThat(0L, equalTo(pageRepository.count()));
        assertThat(0L, equalTo(lemmaRepository.count()));
        assertThat(0L, equalTo(indexesRepository.count()));
    }

    void createDataBase() throws MalformedURLException {
        SiteDto siteDto = new SiteDto();
        siteDto.setName("test");
        siteDto.setUrl("https://музей-ямщика.рф");
        dateBaseService.createSite(siteDto, Status.INDEXING, "test");
        dateBaseService.createPage("https://музей-ямщика.рф", 200, "Повторное появление леопарда в Осетии позволяет предположить," +
                "что леопард постоянно обитает в некоторых районах Северного Кавказа." +
                "Кавказа.");
        dateBaseService.createPage("https://музей-ямщика.рф/тест", 200, "леопард");
    }

}
