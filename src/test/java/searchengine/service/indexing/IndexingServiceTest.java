package searchengine.service.indexing;

import io.restassured.RestAssured;
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
import searchengine.repository.IndexesRepository;
import searchengine.repository.LemmaRepository;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
import searchengine.services.datebase.DateBaseService;
import searchengine.services.indexing.IndexingService;

import java.net.MalformedURLException;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@ActiveProfiles("test")
@DisplayName("Тестирование сервиса индексации")
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IndexingServiceTest {

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
    private IndexingService indexingService;
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
          RestAssured.baseURI = "http://localhost:" + port;

    }

    @Test
    @Order(1)
    @DisplayName("Тестирование индексации")
    public void createPageTest() throws InterruptedException {
        indexingService.startIndexing();
        Thread.sleep(10000);
        assertThat(1L, equalTo(dateBaseService.countSite()));
    }
}
