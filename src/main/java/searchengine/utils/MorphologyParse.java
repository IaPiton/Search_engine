package searchengine.utils;


import lombok.extern.log4j.Log4j2;
import org.apache.lucene.morphology.LuceneMorphology;
import org.apache.lucene.morphology.WrongCharaterException;
import org.apache.lucene.morphology.russian.RussianLuceneMorphology;
import org.jsoup.Jsoup;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@Log4j2
public class MorphologyParse {
    private LuceneMorphology luceneMorphology;
    {
        try {
            luceneMorphology = new RussianLuceneMorphology();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    public Map<String, Integer> getLemmasFromText(String html) {
        Map<String, Integer> lemmasInText = new HashMap<>();
        String text = Jsoup.parse(html).text();
        List<String> words = new ArrayList<>(List.of(text.toLowerCase().split("[^a-zа-я]+")));
        words.forEach(w -> determineLemma(w, lemmasInText));
        return lemmasInText;
    }


    private void determineLemma(String word, Map<String, Integer> lemmasInText) {

              if (checkMatchWord(word)) {
                  return;
              }
              try {
                  luceneMorphology.getNormalForms(word).stream()
                          .filter(w -> !checkWordInfo(luceneMorphology.getMorphInfo(w).toString()))
                          .forEach(w -> lemmasInText.put(w, lemmasInText.containsKey(w) ? (lemmasInText.get(w) + 1) : 1));
              }catch (WrongCharaterException e){
                  log.info("WrongCharaterException: " + word);
              }
          }

    private boolean checkMatchWord(String word) {
        return word.isEmpty() || String.valueOf(word.charAt(0)).matches("[a-z]")
                              || String.valueOf(word.charAt(0)).matches("[0-9]");
    }

    private boolean checkWordInfo(String wordInfo) {
        return wordInfo.contains("ПРЕДЛ") || wordInfo.contains("СОЮЗ") || wordInfo.contains("МЕЖД");
    }
}

