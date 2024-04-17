package searchengine.dto.site;

import org.testng.annotations.Test;
import searchengine.dto.page.PageDto;
import searchengine.entity.Status;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.junit.Assert.assertEquals;

public class SiteDtoTest {
    @Test
    public void getSiteDtoTest() {
        SiteDto siteDto = new SiteDto(1, Status.INDEXING, LocalDateTime.of(2021, 1, 1, 1, 1 ), "testError", "testUrl", "siteName", new HashSet<PageDto>());
     assertEquals(Integer.valueOf(1), siteDto.getId());
     assertEquals(Status.INDEXING, siteDto.getStatus());
     assertEquals(LocalDateTime.of(2021, 1, 1, 1, 1), siteDto.getStatusTime());
     assertEquals("testError", siteDto.getLastError());
     assertEquals("testUrl", siteDto.getUrl());
     assertEquals("siteName", siteDto.getName());
     assertEquals(new HashSet<PageDto>(), siteDto.getPages());
    }

    @Test
    public void setSiteDtoTest() {
        SiteDto siteDto = new SiteDto();
        siteDto.setId(1);
        siteDto.setStatus(Status.INDEXING);
        siteDto.setStatusTime(LocalDateTime.of(2021, 1, 1, 1, 1));
        siteDto.setLastError("testError");
        siteDto.setUrl("testUrl");
        siteDto.setName("siteName");
        siteDto.setPages(new HashSet<PageDto>());
        assertEquals(Integer.valueOf(1), siteDto.getId());
        assertEquals(Status.INDEXING, siteDto.getStatus());
        assertEquals(LocalDateTime.of(2021, 1, 1, 1, 1), siteDto.getStatusTime());
        assertEquals("testError", siteDto.getLastError());
        assertEquals("testUrl", siteDto.getUrl());
        assertEquals("siteName", siteDto.getName());
        assertEquals(new HashSet<PageDto>(), siteDto.getPages());
    }
}
