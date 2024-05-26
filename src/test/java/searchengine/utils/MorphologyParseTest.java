package searchengine.utils;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;
@DisplayName("Тест лематизатора")
public class MorphologyParseTest {


    private  MorphologyParse morphologyParse = new MorphologyParse();


    @Test
    @DisplayName("Тест парсинга лемм")
    public void collectLemmasTest() {
        HashMap<String, Integer> lemmas = new HashMap<>();
        lemmas.put("лес", 1);
        lemmas.put("леса", 1);
        assertThat(lemmas, equalTo(morphologyParse.getLemmasFromText("лес")));
    }
}
