package searchengine.controllers;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;


import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.http.HttpStatus;


import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;


import searchengine.config.StartAndStop;
import searchengine.dto.ResponseDto;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class ApiControllerTest {

    @Autowired
    private MockMvc mockMvc;


    @Test
    void startIndexingTrueTest() throws Exception {
        ResponseDto responseDto = new ResponseDto(true);
        JSONObject jsonObj = createResponseJsonObj(responseDto);
        StartAndStop.setStart(false);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/startIndexing"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().json(jsonObj.toString()));
    }

    @Test
    void startIndexingFalseTest() throws Exception {
        ResponseDto responseDto = new ResponseDto(false, "Indexing is already running");
        StartAndStop.setStart(true);
        JSONObject jsonObj = createResponseJsonObj(responseDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/startIndexing"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().json(jsonObj.toString()));
    }
    @Test
    void IndexingPageTest() throws Exception {
        ResponseDto responseDto = new ResponseDto(true);
        StartAndStop.setStart(false);
        JSONObject jsonObj = createResponseJsonObj(responseDto);
        mockMvc.perform(MockMvcRequestBuilders.post("/api/indexPage").content("URL=http://музей-ямщика.рф"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().json(jsonObj.toString()));
    }

    @Test
    void stopIndexingFalseTest() throws Exception {
        ResponseDto responseDto = new ResponseDto(false, "Indexing is not running");
        StartAndStop.setStart(false);
        JSONObject jsonObj = createResponseJsonObj(responseDto);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/stopIndexing"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().json(jsonObj.toString()));
    }

    @Test
    void stopIndexingTrueTest() throws Exception {
        ResponseDto responseDto = new ResponseDto(true);
        JSONObject jsonObj = createResponseJsonObj(responseDto);
        StartAndStop.setStart(true);
        mockMvc.perform(MockMvcRequestBuilders.get("/api/stopIndexing"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()))
                .andExpect(MockMvcResultMatchers.content().json(jsonObj.toString()));
    }

    @Test
    void statisticsTest() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/statistics"))
                .andExpect(MockMvcResultMatchers.status().is(HttpStatus.OK.value()));
    }


    private JSONObject createResponseJsonObj(ResponseDto responseDto) throws JSONException {
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("result", responseDto.getResult());
        jsonObj.put("error", responseDto.getError());
        return jsonObj;
    }


}
