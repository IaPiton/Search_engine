package searchengine.dto;

import org.junit.jupiter.api.Test;
import searchengine.dto.ResponseDto;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ResponseDtoTest {

    @Test
    public void getResponseDtoTestErrorTest() {
        ResponseDto responseDto = new ResponseDto("ok");
        responseDto.setError("error");
        assertEquals("error", responseDto.getError());
    }
    @Test
    public void getResponseDtoTestResultTest() {
        ResponseDto responseDto = new ResponseDto(true);
        responseDto.setResult(false);
        assertEquals(false, responseDto.getResult());
    }
    @Test
    public void getResponseDtoTestErrorAndResultTest() {
        ResponseDto responseDto = new ResponseDto(true, "ok");
        assertEquals(true, responseDto.getResult());
        assertEquals("ok", responseDto.getError());
    }



}
