package searchengine.mapper;

import org.mapstruct.Mapper;
import searchengine.dto.page.PageDto;
import searchengine.entity.Page;

@Mapper(componentModel = "spring")
public interface PageMapper {
    Page pageDtoToPage(PageDto pageDto);
}
