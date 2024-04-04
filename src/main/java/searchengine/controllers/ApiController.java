package searchengine.controllers;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import searchengine.dto.ResponseDto;
import searchengine.dto.statistics.StatisticsResponse;
import searchengine.services.indexing.IndexingService;
import searchengine.services.statistics.StatisticsService;

@RestController
@RequestMapping("/api")

public class ApiController {

    private final StatisticsService statisticsService;
    private final IndexingService indexingService;


    public ApiController(StatisticsService statisticsService, IndexingService indexingService) {
        this.statisticsService = statisticsService;
        this.indexingService = indexingService;
    }

    @GetMapping("/statistics")
    public ResponseEntity<StatisticsResponse> statistics() {
        return ResponseEntity.ok(statisticsService.getStatistics());
    }

    @GetMapping("/startIndexing")
    public ResponseEntity<ResponseDto> startIndexing() {
        return ResponseEntity.ok(indexingService.startIndexing());
    }
    @GetMapping("/stopIndexing")
    public ResponseEntity<ResponseDto> stopIndexing() {
        ResponseDto responseDto = indexingService.stopIndexing();
        return ResponseEntity.ok(responseDto);
    }


}
