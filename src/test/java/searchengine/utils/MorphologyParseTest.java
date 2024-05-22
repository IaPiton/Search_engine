package searchengine.utils;

import lombok.RequiredArgsConstructor;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.util.HashMap;

import static org.hamcrest.Matchers.equalTo;
import static org.junit.Assert.assertThat;

public class MorphologyParseTest {


    private  MorphologyParse morphologyParse = new MorphologyParse();


    @Test
    public void collectLemmasTest() {
        HashMap<String, Integer> lemmas = new HashMap<>();
        lemmas.put("лес", 1);
        lemmas.put("леса", 1);
        assertThat(lemmas, equalTo(morphologyParse.collectLemmas("Леса")));
    }
}
