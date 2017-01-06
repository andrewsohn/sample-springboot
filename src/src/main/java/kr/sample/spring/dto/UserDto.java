package kr.sample.spring.dto;

import lombok.*;
import org.hibernate.validator.constraints.NotBlank;

import javax.validation.constraints.Size;

/**
 * Created by andrew on 2017. 1. 6..
 */
public class UserDto {
    @Data
    public static class Create {
        @NotBlank
        private String userid;

        @NotBlank
        private String name;

        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }

    @Data
    public static class Read {
        @NotBlank
        private String userid;

        @NotBlank
        private String password;
    }

    @Data
    public static class Response {
        @NotBlank
        private String userid;

        @NotBlank
        private String name;

        @NotBlank
        private String email;

        @NotBlank
        private String password;
    }

    @Data
    public static class Update {
        @NotBlank
        @Size(min=2)
        private String passwd;
        @NotBlank
        @Size(min = 3)
        private String title;
        @NotBlank
        @Size(min = 20)
        private String body;
    }

    @Data
    public static class Delete{
        @NotBlank
        @Size(min=2)
        private String passwd;
    }
}
