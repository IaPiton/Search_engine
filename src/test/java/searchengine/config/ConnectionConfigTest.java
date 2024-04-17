package searchengine.config;

import lombok.With;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.testng.annotations.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
@ExtendWith(SpringExtension.class)
@EnableConfigurationProperties(value = ConnectionConfig.class)
@SpringBootTest
@ActiveProfiles("test")
@ConfigurationProperties(prefix = "connection-config")
public class ConnectionConfigTest {
    private String userAgent;
    private String referer;


    @Test
    public void getConnectionConfigTest() {
        ConnectionConfig connectionConfig = new ConnectionConfig();
        connectionConfig.setReferer("url");
        connectionConfig.setUserAgent("username");


    }
}
