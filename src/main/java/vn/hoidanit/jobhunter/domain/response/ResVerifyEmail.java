package vn.hoidanit.jobhunter.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResVerifyEmail {
    private String message;
    private Object data;
    private boolean isVerifyEmail;

    public ResVerifyEmail(String message, boolean isVerifyEmail) {
        this.message = message;
        this.isVerifyEmail = isVerifyEmail;
    }
}
