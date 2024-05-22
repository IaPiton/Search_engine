package searchengine.utils;

import lombok.NoArgsConstructor;

import lombok.SneakyThrows;
import lombok.extern.log4j.Log4j2;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import searchengine.config.ConnectionConfig;
import searchengine.config.StartAndStop;
import org.jsoup.nodes.Document;
import searchengine.entity.Site;
import searchengine.entity.Status;
import searchengine.services.datebase.DateBaseService;

import java.net.MalformedURLException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.RecursiveAction;

@Log4j2
@Component
@NoArgsConstructor
public class CreateSitesMap extends RecursiveAction {

    private Site site;
    private CopyOnWriteArraySet<String> sitesMap = new CopyOnWriteArraySet<>();
    private String url;
    private DateBaseService dateBaseService;
    private ConnectionConfig connectionConfig;


    public CreateSitesMap(Site site, CopyOnWriteArraySet<String> sitesMap, String url, DateBaseService dateBaseService, ConnectionConfig connectionConfig) {
        this.site = site;
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
                dateBaseService.createPage(url, Connection.getCode(), checkContent(document));
                Elements elements = document.select("a");
                List<CreateSitesMap> taskList = new ArrayList<>();
                for (Element element : elements) {
                    String url = site.getUrl();
                    String href = element.absUrl("href");
                    if (href.contains(url)
                            && !href.contains("#")
                            && !isFile(href)
                            && !sitesMap.contains(href)) {
                        CreateSitesMap task = new CreateSitesMap(site, sitesMap, href, dateBaseService, connectionConfig);
                        task.fork();
                        taskList.add(task);
                        sitesMap.add(href);
                    }
                }
                for (CreateSitesMap task : taskList) {
                    task.join();
                }
            }
        } catch (NullPointerException e) {
            try {
                dateBaseService.createPage(url, 404, " ");
                getPool().shutdown();
            } catch (MalformedURLException ex) {
                throw new RuntimeException(ex);
            }
            dateBaseService.updateStatusAndErrorSite(site, Status.INDEXING, "Page not found ");
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }catch (Exception e) {
            log.error(e);
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

    @SneakyThrows
    public void indexPage(String urlReplace, DateBaseService dateBaseService, ConnectionConfig connectionConfig) {
        Document document = Connection.getDocument(urlReplace, connectionConfig);
        dateBaseService.createPage(urlReplace, Connection.getCode(), checkContent(document));
    }
}

