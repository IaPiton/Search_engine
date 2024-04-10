package searchengine.utils;

import lombok.NoArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import searchengine.config.ConnectionConfig;
import searchengine.config.StartAndStop;
import searchengine.dto.site.SiteDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import searchengine.services.datebase.DateBaseService;


import java.io.IOException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RecursiveAction;

@Log4j2
@Component
@NoArgsConstructor
public class ParseUtil extends RecursiveAction {

    private SiteDto siteDto;
    private ConcurrentSkipListSet<String> sitesMap = new ConcurrentSkipListSet<>();
    private String url;
    private DateBaseService dateBaseService;
    private ConnectionConfig connectionConfig;

    private Integer code;

    public ParseUtil(SiteDto siteDto, ConcurrentSkipListSet<String> sitesMap, String url, DateBaseService dateBaseService, ConnectionConfig connectionConfig) {
        this.siteDto = siteDto;
        this.sitesMap = sitesMap;
        this.url = url;
        this.dateBaseService = dateBaseService;
        this.connectionConfig = connectionConfig;
    }

    @Override
    public void compute() {
        try {
            if (StartAndStop.getStart()) {
                Document document = connection(url);
                if (document != null) {
                    dateBaseService.createPage(url, code, document.toString(), siteDto);
                    sitesMap.add(document.baseUri());
                    Elements elements = document.select("a");
                    List<String> list = new ArrayList<>();
                    for (Element element : elements) {
                        String url = siteDto.getUrl();
                        String href = element.absUrl("href");
                        if (href.contains(url)
                                && !href.contains("#")
                                && !isFile(href)) {
                            list.add(href);
                        }
                    }
                    List<ParseUtil> taskList = new ArrayList<>();
                    for (String child : list) {
                        if (!sitesMap.contains(child)) {
                            ParseUtil task = new ParseUtil(siteDto, sitesMap, child, dateBaseService, connectionConfig);
                            task.fork();
                            taskList.add(task);
                            sitesMap.add(child);
                        }
                    }
                    for (ParseUtil task : taskList) {
                        task.join();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private Document connection(String url) {
        Document document = null;
        try {
            Thread.sleep(650);
            document = Jsoup.connect(url)
                    .userAgent(connectionConfig.getUserAgent())
                    .referrer(connectionConfig.getReferer())
                    .get();
            code = document.connection().response().statusCode();
        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException | InterruptedException | RuntimeException e) {
            code = 404;
            log.info(url + " " + code);
        }
        if (code != 200) {
            return null;
        }
        return document;

    }

    private static boolean isFile(String link) {
        return link.contains(".jpg")
                || link.contains(".jpeg")
                || link.contains(".png")
                || link.contains(".gif")
                || link.contains(".webp")
                || link.contains(".pdf")
                || link.contains(".eps")
                || link.contains(".xlsx")
                || link.contains(".doc")
                || link.contains(".pptx")
                || link.contains(".docx")
                || link.contains("?_ga");
    }
}

