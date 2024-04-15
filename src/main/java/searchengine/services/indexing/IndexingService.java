package searchengine.services.indexing;

import searchengine.dto.ResponseDto;

public interface IndexingService {

    ResponseDto startIndexing();
    ResponseDto stopIndexing();
}
