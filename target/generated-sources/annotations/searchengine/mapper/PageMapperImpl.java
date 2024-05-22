package searchengine.mapper;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import searchengine.dto.page.PageDto;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Page;
import searchengine.entity.Site;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-05-22T09:42:04+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Amazon.com Inc.)"
)
@Component
public class PageMapperImpl implements PageMapper {

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
        site.setPages( pageDtoSetToPageList( siteDto.getPages() ) );

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
        siteDto.setPages( pageListToPageDtoSet( site.getPages() ) );

        return siteDto;
    }

    @Override
    public Page pageDtoToPage(PageDto pageDto) {
        if ( pageDto == null ) {
            return null;
        }

        Page page = new Page();

        page.setId( pageDto.getId() );
        page.setPath( pageDto.getPath() );
        page.setCode( pageDto.getCode() );
        page.setContent( pageDto.getContent() );

        page.setSite( siteDtoToSite(pageDto.getSiteDto()) );

        return page;
    }

    @Override
    public PageDto pageToPageDto(Page page) {
        if ( page == null ) {
            return null;
        }

        PageDto pageDto = new PageDto();

        pageDto.setId( page.getId() );
        pageDto.setPath( page.getPath() );
        pageDto.setCode( page.getCode() );
        pageDto.setContent( page.getContent() );

        return pageDto;
    }

    protected List<Page> pageDtoSetToPageList(Set<PageDto> set) {
        if ( set == null ) {
            return null;
        }

        List<Page> list = new ArrayList<Page>( set.size() );
        for ( PageDto pageDto : set ) {
            list.add( pageDtoToPage( pageDto ) );
        }

        return list;
    }

    protected Set<PageDto> pageListToPageDtoSet(List<Page> list) {
        if ( list == null ) {
            return null;
        }

        Set<PageDto> set = new LinkedHashSet<PageDto>( Math.max( (int) ( list.size() / .75f ) + 1, 16 ) );
        for ( Page page : list ) {
            set.add( pageToPageDto( page ) );
        }

        return set;
    }
}
