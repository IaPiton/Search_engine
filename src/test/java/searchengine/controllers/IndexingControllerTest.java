package searchengine.controllers;


import com.nimbusds.jose.shaded.json.JSONObject;
import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Container;
import org.testcontainers.junit.jupiter.Testcontainers;
import searchengine.config.StartAndStop;
import searchengine.dto.ResponseDto;

import java.net.MalformedURLException;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Testcontainers
@AutoConfigureMockMvc
@ActiveProfiles("test")
@DisplayName("Тест контроллера индексации")
public class IndexingControllerTest {
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
    private MockMvc mockMvc;


    @BeforeEach
    void setUp() throws MalformedURLException {
        RestAssured.baseURI = "http://localhost:" + port;
    }
    @Test
    @DisplayName("Тест метода startIndexing TRUE")
    public void startIndexingTrueTest() throws Exception {
        ResponseDto responseDto = new ResponseDto(true);
        JSONObject jsonObj = createResponseJsonObj(responseDto);
        StartAndStop.setStart(false);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/startIndexing"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().json(jsonObj.toString()));
    }

    @Test
    @DisplayName("Тест метода startIndexing FALSE")
    public void startIndexingFalseTest() throws Exception {
        ResponseDto responseDto = new ResponseDto(true, "Индексация уже запущена");
        StartAndStop.setStart(true);
        JSONObject jsonObj = createResponseJsonObj(responseDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/startIndexing"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().json(jsonObj.toString()));
    }
    @Test
    @DisplayName("Тест индексации страницы")
    public void IndexingPageTest() throws Exception {
        ResponseDto responseDto = new ResponseDto(true);
        StartAndStop.setStart(false);
        JSONObject jsonObj = createResponseJsonObj(responseDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/indexPage").content("URL=http://музей-ямщика.рф"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().json(jsonObj.toString()));
    }

    @Test
    @DisplayName("Тест остановки индексации FALSE")
    public void stopIndexingFalseTest() throws Exception {
        ResponseDto responseDto = new ResponseDto(false, "Индексация не выполняется");
        StartAndStop.setStart(false);
        JSONObject jsonObj = createResponseJsonObj(responseDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/stopIndexing"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().json(jsonObj.toString()));
    }

    @Test
    @DisplayName("Тест остановки индексации TRUE")
    public void stopIndexingTrueTest() throws Exception {
        ResponseDto responseDto = new ResponseDto(true);
        JSONObject jsonObj = createResponseJsonObj(responseDto);
        StartAndStop.setStart(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/stopIndexing"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().json(jsonObj.toString()));
    }

    @Test
    @DisplayName("Тест статистики")
    public void statisticsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }


    private JSONObject createResponseJsonObj(ResponseDto responseDto){
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", responseDto.getResult());
        jsonObj.put("error", responseDto.getError());
        return jsonObj;
    }


}
