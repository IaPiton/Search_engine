package searchengine.services.datebase;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import searchengine.dto.page.PageDto;
import searchengine.dto.site.SiteDto;
import searchengine.mapper.PageMapper;
import searchengine.mapper.SiteMapper;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;

@Service
@RequiredArgsConstructor
public class DateBaseService {

    private final SiteRepository siteRepository;
    private final PageRepository pageRepository;
    private final SiteMapper siteMapper;
    private final PageMapper pageMapper;

    @Transactional
    public void createPage(String path, Integer code, String content, SiteDto siteDto) {
        try {
            PageDto pageDto = new PageDto();
            pageDto.setPath(path);
            pageDto.setCode(code);
            pageDto.setContent(content);
            pageDto.setSite(siteMapper.siteDtoToSite(siteDto));
            pageRepository.saveAndFlush(pageMapper.pageDtoToPage(pageDto));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Transactional
    public void deleteAll() {
        pageRepository.deleteAll();
        siteRepository.deleteAll();
    }
}
