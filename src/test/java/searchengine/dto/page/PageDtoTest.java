package searchengine.dto.page;


import org.junit.jupiter.api.Test;

import static org.hamcrest.Matchers.equalTo;

import static org.junit.Assert.assertThat;


public class PageDtoTest {

    @Test
    public void getPageDtoTest() {
        PageDto pageDto = new PageDto();
        pageDto.setPath("testPath");
        pageDto.setCode(1);
        pageDto.setContent("testContent");
        pageDto.setId(1);
        assertThat(1, equalTo(pageDto.getId()));
        assertThat("testPath", equalTo(pageDto.getPath()));
        assertThat(1, equalTo(pageDto.getCode()));
        assertThat("testContent", equalTo(pageDto.getContent()));
        assertThat("PageDto(id=1, path=testPath, code=1, content=testContent, siteDto=null)", equalTo(pageDto.toString()));
    }
}
