package vn.hoidanit.jobhunter.domain.request;

public class VerifyCodeDTO {
    private String email;
    private String verifyCode;
    public String getEmail() {
        return email;
    }
    public String getVerifyCode() {
        return verifyCode;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public void setVerifyCode(String verifyCode) {
        this.verifyCode = verifyCode;
    }
}
