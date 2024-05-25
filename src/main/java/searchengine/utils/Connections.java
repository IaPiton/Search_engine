package searchengine.utils;

import lombok.*;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Connection;
import org.jsoup.HttpStatusException;
import org.jsoup.Jsoup;
import org.jsoup.UnsupportedMimeTypeException;
import org.jsoup.nodes.Document;

import org.springframework.stereotype.Component;
import searchengine.config.ConnectionConfig;

import java.io.IOException;
import java.net.SocketTimeoutException;

@Log4j2
@Component
@Getter
@NoArgsConstructor
public class Connections {
    @Getter
    private static int code;
    public static Document getDocument(String url, ConnectionConfig connectionConfig) {
        Document doc = null;
        try {
            Connection connection = Jsoup
                    .connect(url)
                    .userAgent(connectionConfig.getUserAgent())
                    .referrer(connectionConfig.getReferer())
                    .timeout(connectionConfig.getTimeout())
                    .ignoreContentType(true);
            doc = connection.get();
            code = connection.response().statusCode();
        } catch (SocketTimeoutException e){
            log.info("Превышен тайм-аут соединения");
        } catch (HttpStatusException e) {
            code = 503;
            log.info("Страница " + url + " не доступна" );
        } catch (UnsupportedMimeTypeException | NullPointerException e) {
            code = 404;
            log.info("Страница " + url + " не доступна" );
        } catch (IOException e) {
            log.info("Страница " + url + " не доступна" );
            code = 404;
        }
        return doc;
    }

}
