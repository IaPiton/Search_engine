package searchengine.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import searchengine.dto.page.PageDto;
import searchengine.entity.Page;

import java.time.LocalDateTime;

@Mapper(componentModel = "spring", imports = {LocalDateTime.class, })
public interface PageMapper extends SiteMapper {




    @Mapping(target = "site", expression = "java(siteDtoToSite(pageDto.getSiteDto()))")
    Page pageDtoToPage(PageDto pageDto);

    PageDto pageToPageDto(Page page);
}
