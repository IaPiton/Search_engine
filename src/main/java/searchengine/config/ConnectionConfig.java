package searchengine.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "connection-config")
public class ConnectionConfig {
    private String userAgent;
    private String referer;
}
