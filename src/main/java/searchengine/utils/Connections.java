package searchengine.utils;

import lombok.Getter;
import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import searchengine.config.ConnectionConfig;

@Log4j2
@Component
@Getter
public class Connections {
    @Getter
    private static int code;

    public static Document getDocument(String url, ConnectionConfig connectionConfig) {
        Document doc = null;
        try {
            doc = Jsoup
                    .connect(url)
                    .userAgent(connectionConfig.getUserAgent())
                    .referrer(connectionConfig.getReferer())
                    .timeout(connectionConfig.getTimeout())
                    .ignoreContentType(true)
                    .get();
            code = 200;
        } catch (Exception e) {
            log.info("Страница " + url + " не доступна");
            code = 404;
        }
        return doc;
    }
}