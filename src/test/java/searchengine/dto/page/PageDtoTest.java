package searchengine.dto.page;

import org.testng.annotations.Test;
import searchengine.dto.site.SiteDto;


import static org.junit.Assert.*;


public class PageDtoTest {

    @Test
    public void getPageTest() {
        PageDto pageDto = new PageDto(1, "testPath", 1, "testContent", new SiteDto());
        assertEquals(Integer.valueOf(1), pageDto.getId());
        assertEquals("testPath", pageDto.getPath());
        assertEquals(Integer.valueOf(1), pageDto.getCode());
        assertEquals("testContent", pageDto.getContent());
        assertEquals(new SiteDto(), pageDto.getSiteDto());
    }

    @Test
    public void setPageTest() {
        PageDto pageDto= new PageDto();
        pageDto.setId(1);
        pageDto.setPath("testPath");
        pageDto.setCode(1);
        pageDto.setContent("testContent");
        pageDto.setSiteDto(new SiteDto());
        assertEquals(Integer.valueOf(1), pageDto.getId());
        assertEquals("testPath", pageDto.getPath());
        assertEquals(Integer.valueOf(1), pageDto.getCode());
        assertEquals("testContent", pageDto.getContent());
        assertEquals(new SiteDto(), pageDto.getSiteDto());
    }


}
