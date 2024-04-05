package searchengine.config;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import searchengine.dto.site.SiteDto;

import java.util.List;

@Data
@Configuration
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "indexing-settings")
public class SitesList {
    private List<SiteDto> sites;
}
