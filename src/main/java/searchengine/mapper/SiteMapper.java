package searchengine.mapper;


import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.springframework.stereotype.Component;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Site;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class})
public interface SiteMapper {

    @Mapping(target = "statusTime", expression = "java(LocalDateTime.now())")
    Site siteDtoToSite(SiteDto siteDto);

    SiteDto siteToSiteDto(Site site);

}
