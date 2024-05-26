package searchengine.utils;


import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import searchengine.config.ConnectionConfig;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@DisplayName("Тестирование соединения")
public class ConnectionTest {

    @Autowired
    private Connections connections;
    @Autowired
    private ConnectionConfig connectionConfig;
    private String url = "https://музей-ямщика.рф/";
    private String badUrl = "https://музе-ямщика.рф/";



    @Test
    @DisplayName("Проверка соединения, код 200")
    public void getDocumentTest() {
    connections.getDocument(url, connectionConfig);
    assertThat(200, equalTo(Connections.getCode()));
    }

    @Test
    @DisplayName("Проверка соединения, код 404")
    public void getDocumentTestError() {
        connections.getDocument(badUrl, connectionConfig);
        assertThat(404, equalTo(connections.getCode()));
    }

}
