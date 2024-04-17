package searchengine.config;

import lombok.*;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "connection-config")
public class ConnectionConfig {
    private String userAgent;
    private String referer;
}
