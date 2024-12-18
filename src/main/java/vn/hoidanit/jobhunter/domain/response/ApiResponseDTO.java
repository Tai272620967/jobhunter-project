package vn.hoidanit.jobhunter.domain.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ApiResponseDTO {
    private String message;

    public ApiResponseDTO(String message) {
        this.message = message;
    }
}
