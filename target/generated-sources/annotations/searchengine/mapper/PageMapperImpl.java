package searchengine.mapper;

import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;
import searchengine.dto.page.PageDto;
import searchengine.entity.Page;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2024-04-05T08:52:42+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 17.0.10 (Amazon.com Inc.)"
)
@Component
public class PageMapperImpl implements PageMapper {

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
        page.setSite( pageDto.getSite() );

        return page;
    }
}
