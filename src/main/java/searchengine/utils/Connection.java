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
@AllArgsConstructor
@NoArgsConstructor
public class Connection {

   private String url;
   private ConnectionConfig connectionConfig;
    @Getter
    private static int code;
    public static Document getDocument(String url, ConnectionConfig connectionConfig) {
        Document document = null;
        try {
            code = 0;
            document = Jsoup.connect(url)
                    .userAgent(connectionConfig.getUserAgent())
                    .referrer(connectionConfig.getReferer())
                    .get();
            code = document.connection().response().statusCode();
        } catch (SocketException e) {
            code = 500;
        } catch (IOException |
                 RuntimeException e) {
            code = 404;
        }
        return document;
    }

}
