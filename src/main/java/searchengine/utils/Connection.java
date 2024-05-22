package searchengine.utils;

import lombok.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import searchengine.config.ConnectionConfig;


@Component
@Getter
@NoArgsConstructor
public class Connection {
    @Getter
    private static int code;
    public static Document getDocument(String url, ConnectionConfig connectionConfig) {
        Document document = null;
        try {
            code = 0;
            Thread.sleep(2600);
            document  = Jsoup.connect(url)
                    .userAgent(connectionConfig.getUserAgent())
                    .referrer(connectionConfig.getReferer())
                    .timeout(10000)
                    .ignoreContentType(true).get();
            code = document.connection().response().statusCode();
        } catch (Exception e) {
            code = 404;
        }

        return document;
    }

}
