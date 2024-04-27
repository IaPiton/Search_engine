package searchengine.dto.site;

import org.junit.jupiter.api.Test;
import searchengine.dto.page.PageDto;
import searchengine.entity.Status;

import java.time.LocalDateTime;
import java.util.HashSet;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

public class SiteDtoTest {
    @Test
    public void getSiteDtoTest() {

        SiteDto siteDto = new SiteDto();
        siteDto.setId(1);
        siteDto.setStatus(Status.INDEXING);
        siteDto.setLastError("testError");
        siteDto.setUrl("testUrl");
        siteDto.setName("siteName");
        siteDto.setPages(new HashSet<PageDto>());
        assertThat(1, equalTo(siteDto.getId()));
        assertThat(Status.INDEXING, equalTo(siteDto.getStatus()));
        assertThat("testError", equalTo(siteDto.getLastError()));
        assertThat("testUrl", equalTo(siteDto.getUrl()));
        assertThat("siteName", equalTo(siteDto.getName()));
        assertThat("SiteDto(id=1, status=INDEXING, statusTime=null, lastError=testError, url=testUrl, name=siteName, pages=[])", equalTo(siteDto.toString()));

    }


}
