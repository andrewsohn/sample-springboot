package kr.sample.spring.common;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import org.springframework.validation.FieldError;

import java.util.List;

/**
 * Created by andrew on 2017. 1. 6..
 */
@Data
public class ErrorResponse {
    private String message;
    private String code;
    private List<FieldError> errors;

    @Data
    public static class FieldError {
        private String field;
        private String code;
        private String message;

    }
}
