package searchengine.config;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import searchengine.dto.site.SiteDto;

import java.util.List;

@Data
@Component
@RequiredArgsConstructor
@ConfigurationProperties(prefix = "indexing-settings")
public class SitesList {
    private List<SiteDto> sites;
}
