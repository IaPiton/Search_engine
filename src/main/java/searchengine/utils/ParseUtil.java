package searchengine.utils;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Component;
import searchengine.config.IsStart;
import searchengine.dto.site.SiteDto;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;


import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.RecursiveAction;

@Component
@NoArgsConstructor
public class ParseUtil extends RecursiveAction {

    private SiteDto siteDto;
    private ConcurrentSkipListSet<String> sitesMap = new ConcurrentSkipListSet<>();
    private String url;

    public ParseUtil(SiteDto siteDto, String url) {
        this.siteDto = siteDto;
        this.url = url;
    }

    public ParseUtil(SiteDto siteDto, ConcurrentSkipListSet<String> sitesMap, String url) {
        this.siteDto = siteDto;
        this.sitesMap = sitesMap;
        this.url = url;
    }

    @Override
    public void compute() {
        if (IsStart.getStart()) {

            Document document = connection(url);
            sitesMap.add(document.baseUri());
            Elements elements = document.select("a");
            List<String> list = new ArrayList<>();
            for (Element element : elements) {
                String url = siteDto.getUrl();
                String href = element.absUrl("href");
                if (href.contains(url)
                        && !href.contains("#")
                        && !isFile(href)) {
                    list.add(element.absUrl("href"));
                }
            }
                List<ParseUtil> taskList = new ArrayList<>();
                for (String child : list) {
                    if (!sitesMap.contains(child)) {
                        ParseUtil task = new ParseUtil(siteDto,  sitesMap, child);
                        task.fork();
                        taskList.add(task);
                        sitesMap.add(child);
                    }
                }
                for (ParseUtil task : taskList) {
                    task.join();
                }



        }
        System.out.println(sitesMap.size());
    }

        private Document connection (String url){
            Document document = null;
            try {
                document = Jsoup.connect(url).get();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            return document;

        }
        private static boolean isFile (String link){
            link.toLowerCase();
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

