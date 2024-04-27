package searchengine.utils;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import searchengine.config.ConnectionConfig;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ConnectionTest {

    @Autowired
    private Connection connection;
    @Autowired
    private ConnectionConfig connectionConfig;
    private String url = "https://музей-ямщика.рф/";
    private String badUrl = "https://музе-ямщика.рф/";



    @Test
    public void getDocumentTest() {
    Connection.getDocument(url, connectionConfig);
    assertThat(200, equalTo(connection.getCode()));
    }

    @Test
    public void getDocumentTestError() {
        Connection.getDocument(badUrl, connectionConfig);
        assertThat(404, equalTo(connection.getCode()));
    }

}
