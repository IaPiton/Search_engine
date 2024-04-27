package searchengine.dto;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
public class ResponseDto {
    private Boolean result;
    private String error;

    public ResponseDto(Boolean result) {
        this.result = result;
    }

    public ResponseDto(String error) {
        this.error = error;
    }

    public ResponseDto(Boolean result, String error) {
        this.result = result;
        this.error = error;
    }





}
