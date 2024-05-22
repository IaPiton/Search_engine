package searchengine.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import searchengine.dto.ResponseDto;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.services.indexing.IndexingService;
import searchengine.services.statistics.StatisticsService;

import java.net.MalformedURLException;
import java.net.URISyntaxException;

@Log4j2
@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class ApiController {

    private final StatisticsService statisticsService;
    private final IndexingService indexingService;


    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<ResponseDto> startIndexing() {
        log.info("ApiController.startIndexing() - start");
        return ResponseEntity.ok(indexingService.startIndexing());
    }
    @GetMapping("/stopIndexing")
    public ResponseEntity<ResponseDto> stopIndexing() {
        log.info("ApiController.stopIndexing() - start");
        return ResponseEntity.ok(indexingService.stopIndexing());
    }
    @PostMapping("/indexPage")
    public ResponseEntity<ResponseDto> indexPage(@RequestBody String url) throws URISyntaxException, MalformedURLException {
        log.info("ApiController.indexPage() - start");
        ResponseDto responseDto = indexingService.indexPage(url);
        return ResponseEntity.ok(responseDto);
    }


}
