package searchengine.services.indexing;

import searchengine.dto.ResponseDto;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

public interface IndexingService {

    ResponseDto startIndexing();
    ResponseDto stopIndexing();

    ResponseDto indexPage(String url) throws URISyntaxException, MalformedURLException;
}
