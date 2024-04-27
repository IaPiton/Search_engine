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
import searchengine.config.ConnectionConfig;
import searchengine.config.StartAndStop;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Status;

import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.services.datebase.DateBaseService;

import searchengine.services.indexing.IndexingServiceImpl;


import java.net.MalformedURLException;


import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DataBaseServiceTest {
    @LocalServerPort
    private Integer port;
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16.2"
    );

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @DynamicPropertySource
    static void configureProperties(DynamicPropertyRegistry registry) {
        registry.add("spring.datasource.url", postgres::getJdbcUrl);
        registry.add("spring.datasource.username", postgres::getUsername);
        registry.add("spring.datasource.password", postgres::getPassword);
    }

    @Autowired
    private SiteRepository siteRepository;
    @Autowired
    private PageRepository pageRepository;
    @Autowired
    private DateBaseService dateBaseService;
    @Autowired
    private IndexingServiceImpl indexingService;
    @Autowired
    private ConnectionConfig connectionConfig;

    @BeforeEach
    void setUp() {
        RestAssured.baseURI = "http://localhost:" + port;
        pageRepository.deleteAll();
        siteRepository.deleteAll();
    }

    @SneakyThrows
    @Test
    public void createPageTest() {
        createDataBase();
        assertThat(1L, equalTo(pageRepository.count()));

    }

    @SneakyThrows
    @Test
    void createSiteTest() {
        createDataBase();
        assertThat(1L, equalTo(siteRepository.count()));
    }

    @SneakyThrows
    @Test
    void updateErrorSiteTest() {
        createDataBase();
        SiteDto siteDto = new SiteDto();
        siteDto.setName("test");
        dateBaseService.updateErrorSite(siteDto, "error");
        assertThat("error 0 pages", equalTo(siteRepository.findByName("test").getLastError()));
    }

    @SneakyThrows
    @Test
    void countSitesByStatusTest() {
        createDataBase();
        assertThat(1, equalTo(dateBaseService.countSitesByStatus(Status.INDEXING)));
    }

    @Test
    void deleterAllTest() throws MalformedURLException {
        createDataBase();
        dateBaseService.deleterAll();
        assertThat(0L, equalTo(siteRepository.count()));
        assertThat(0L, equalTo(pageRepository.count()));
    }

    @SneakyThrows
    @Test
    void updateStatusSite(){
        createDataBase();
        SiteDto siteDto = new SiteDto();
        siteDto.setName("test");
        dateBaseService.updateStatusSite(siteDto, Status.FAILED);
        assertThat(Status.FAILED, equalTo(siteRepository.findByName("test").getStatus()));
    }
    @Test
    void createThreadForIndexingSiteTest(){
        SiteDto siteDto = new SiteDto();
        siteDto.setUrl("http://музей-ямщика.рф");
        siteDto.setName("музей-ямщика.рф");
        StartAndStop.setStart(true);
        indexingService.createThreadForIndexingSite(siteDto);
        assertThat(1L, equalTo(siteRepository.count()));
        assertThat(5L, equalTo(pageRepository.count()));
    }
    @Test
    void indexPageTest() throws MalformedURLException {
        indexingService.indexPage("http://музей-ямщика.рф");
        indexingService.indexPage("http://музей-ямщика.рф");
        assertThat(1L, equalTo(pageRepository.count()));
    }
    @Test
    void indexPageRunningTrueTest() throws MalformedURLException {
        StartAndStop.setStart(true);
        indexingService.indexPage("http://музей-ямщика.рф");
        assertThat(0L, equalTo(pageRepository.count()));
    }

    @Test
    void indexPageSiteFalseTest() throws MalformedURLException {
        StartAndStop.setStart(true);
        indexingService.indexPage("http://gavyam.ru");
        assertThat(0L, equalTo(pageRepository.count()));
    }

    @Test
    void createThreadForIndexingSiteErrorTest(){
        SiteDto siteDto = new SiteDto();
        siteDto.setUrl("http://музей-ямщика.рфф");
        siteDto.setName("музей-ямщика.рф");
        StartAndStop.setStart(true);
        indexingService.createThreadForIndexingSite(siteDto);
        assertThat(1L, equalTo(pageRepository.count()));
    }

    @Test
    void finishedIndexing() {
        SiteDto siteDto = new SiteDto();
        siteDto.setUrl("http://музей-ямщика.рф");
        siteDto.setName("музей-ямщика.рф");
       SiteDto siteDtoTest = dateBaseService.createSite(siteDto, Status.INDEXING, "test");
        StartAndStop.setStart(false);
        indexingService.finishedIndexing(siteDtoTest);
        assertThat(Status.FAILED, equalTo(siteRepository.findByName(siteDtoTest.getName()).getStatus()));


    }


    void createDataBase() throws MalformedURLException {
        SiteDto siteDto = new SiteDto();
        siteDto.setName("test");
        siteDto.setUrl("http://музей-ямщика.рф");
        SiteDto siteDtoTest = dateBaseService.createSite(siteDto, Status.INDEXING, "test");
        dateBaseService.createPage("http://музей-ямщика.рф", 200, "test");
    }


}
