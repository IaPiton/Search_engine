package searchengine.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

import searchengine.dto.site.SiteDto;

import java.util.List;

@Getter
@Setter
@Configuration
@ConfigurationProperties(prefix = "indexing-settings")
public class SitesList {
    private List<SiteDto> sites;

    public SitesList() {
        this.sites = sites;
    }
}
