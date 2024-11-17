package vn.hoidanit.jobhunter.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResVerifyOtp {
    private String message;
    private Object data;
    private boolean isVerifiedOtp;

    public ResVerifyOtp(String message, boolean isVerifiedOtp) {
        this.message = message;
        this.isVerifiedOtp = isVerifiedOtp;
    }
}
