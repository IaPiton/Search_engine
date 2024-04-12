package searchengine.services.datebase;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import searchengine.dto.page.PageDto;
import searchengine.dto.site.SiteDto;
import searchengine.entity.Site;
import searchengine.entity.Status;
import searchengine.mapper.PageMapper;
import searchengine.mapper.SiteMapper;
import searchengine.repository.PageRepository;
import searchengine.repository.SiteRepository;
@Log4j2
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
        try {
//        pageRepository.deleteAll();
            siteRepository.deleteAll();
            log.info("Очистка базы данных успешна");
        }catch (Exception e){
            log.info("Очистка базы данных не удалась");
        }
    }

    @Transactional(readOnly = true)
    public Integer countSitesByStatus(Status status) {
     return siteRepository.countByStatus(status);
    }
    @Transactional
    public SiteDto siteCreate(SiteDto siteDto, Status status, String error) {
        siteDto.setStatus(status);
        siteDto.setLastError(error);
        return siteMapper.siteToSiteDto(siteRepository.saveAndFlush(siteMapper.siteDtoToSite(siteDto)));
    }
@Transactional(readOnly = true)
    public Long countSite() {
        return siteRepository.count();
    }
    @Transactional(readOnly = true)
    public Long countPage() {
        return pageRepository.count();
    }
    @Transactional(readOnly = true)
    public Iterable<Site> getAllSites() {
      return   siteRepository.findAll();
    }
    @Transactional(readOnly = true)
    public Integer countPageBySiteId(Integer siteId) {
        return pageRepository.countBySiteId(siteId);
    }
}
