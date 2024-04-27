package searchengine.entity;

import org.junit.jupiter.api.Test;
import searchengine.dto.page.PageDto;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class PageTest {
    @Test
    public void getPageDtoTest() {
        Page page = new Page();
        page.setId(1);
        page.setPath("testPath");
        page.setCode(1);
        page.setContent("testContent");

        page.setSite(null);

        assertThat(1, equalTo(page.getId()));
        assertThat("testPath", equalTo(page.getPath()));
        assertThat(1, equalTo(page.getCode()));
        assertThat("testContent", equalTo(page.getContent()));
        assertThat(null, equalTo(page.getSite()));

    }
}
