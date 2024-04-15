package searchengine.utils;

import lombok.NoArgsConstructor;

import lombok.extern.log4j.Log4j2;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import searchengine.config.ConnectionConfig;
import searchengine.config.StartAndStop;
import searchengine.dto.site.SiteDto;
import org.jsoup.nodes.Document;
import searchengine.services.datebase.DateBaseService;

import java.io.IOException;
import java.net.ConnectException;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.RecursiveAction;

@Log4j2
@Component
@NoArgsConstructor
public class CreateSitesMap extends RecursiveAction {

    private SiteDto siteDto;
    private CopyOnWriteArraySet<String> sitesMap = new CopyOnWriteArraySet<>();
    private String url;
    private DateBaseService dateBaseService;
    private ConnectionConfig connectionConfig;

    private Integer code;

    public CreateSitesMap(SiteDto siteDto, CopyOnWriteArraySet<String> sitesMap, String url, DateBaseService dateBaseService, ConnectionConfig connectionConfig) {
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
                Document document = Connection.getDocument(url, connectionConfig);
                dateBaseService.createPage(url, Connection.getCode(), checkContent(document), siteDto);
                Elements elements = document.select("a");
                List<CreateSitesMap> taskList = new ArrayList<>();
                for (Element element : elements) {
                    String url = siteDto.getUrl();
                    String href = element.absUrl("href");
                    if (href.contains(url)
                            && !href.contains("#")
                            && !isFile(href)
                            && !sitesMap.contains(href)) {
                        CreateSitesMap task = new CreateSitesMap(siteDto, sitesMap, href, dateBaseService, connectionConfig);
                        task.fork();
                        taskList.add(task);
                        sitesMap.add(href);
                    }
                }
                for (CreateSitesMap task : taskList) {
                    task.join();
                }
            }
        } catch (NullPointerException e){
            dateBaseService.createPage(url, Connection.getCode(), " ", siteDto);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }



    private String checkContent(Document document) {
        try {
            return document.toString();
        } catch (NullPointerException e) {
            return "";
        }
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

