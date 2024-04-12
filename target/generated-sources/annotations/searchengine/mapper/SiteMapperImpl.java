package searchengine.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Page;
import searchengine.entity.Site;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-11T15:28:22+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Amazon.com Inc.)"
)
@Component
public class SiteMapperImpl implements SiteMapper {

    @Override
    public Site siteDtoToSite(SiteDto siteDto) {
        if ( siteDto == null ) {
            return null;
        }

        Site site = new Site();

        site.setId( siteDto.getId() );
        site.setStatus( siteDto.getStatus() );
        site.setLastError( siteDto.getLastError() );
        site.setUrl( siteDto.getUrl() );
        site.setName( siteDto.getName() );
        Set<Page> set = siteDto.getPages();
        if ( set != null ) {
            site.setPages( new ArrayList<Page>( set ) );
        }

        site.setStatusTime( LocalDateTime.now() );

        return site;
    }

    @Override
    public SiteDto siteToSiteDto(Site site) {
        if ( site == null ) {
            return null;
        }

        SiteDto siteDto = new SiteDto();

        siteDto.setId( site.getId() );
        siteDto.setStatus( site.getStatus() );
        siteDto.setStatusTime( site.getStatusTime() );
        siteDto.setLastError( site.getLastError() );
        siteDto.setUrl( site.getUrl() );
        siteDto.setName( site.getName() );
        List<Page> list = site.getPages();
        if ( list != null ) {
            siteDto.setPages( new LinkedHashSet<Page>( list ) );
        }

        return siteDto;
    }
}
