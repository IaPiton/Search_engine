package searchengine.controllers;

import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchengine.dto.ResponseDto;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.services.indexing.IndexingService;
import searchengine.services.indexing.IndexingServiceImpl;
import searchengine.services.statistics.StatisticsService;
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
        ResponseDto responseDto = indexingService.stopIndexing();
        return ResponseEntity.ok(responseDto);
    }


}
