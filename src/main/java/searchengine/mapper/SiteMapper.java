package searchengine.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Site;

@Mapper( componentModel = "spring")
public interface SiteMapper {

    @Mapping(target = "statusTime", expression = "java(LocalDateTime.now())")
    Site siteDtoToSite(SiteDto siteDto);

    SiteDto siteToSiteDto(Site site);

}
