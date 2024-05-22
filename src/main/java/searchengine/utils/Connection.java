package searchengine.utils;

import lombok.*;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.springframework.stereotype.Component;
import searchengine.config.ConnectionConfig;

import java.io.IOException;
import java.net.SocketException;

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
            Thread.sleep(600);
            org.jsoup.Connection connection  = Jsoup.connect(url)
                    .userAgent(connectionConfig.getUserAgent())
                    .referrer(connectionConfig.getReferer())
                    .timeout(10000)
                    .ignoreContentType(true);

            document = connection.get();
            code = document.connection().response().statusCode();
        } catch (Exception e) {
            code = 404;
        }
        return document;
    }

}
