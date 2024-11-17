package vn.hoidanit.jobhunter.domain.response;

import java.sql.Date;
import java.time.Instant;

import lombok.Getter;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.GenderEnum;

@Getter
@Setter
public class ResCreateUserDTO {
    private long id;
    private String firstName;
    private String lastName;
    private String kataFirstName;
    private String kataLastName;
    private String address1;
    private String address2;
    private String address3;
    private String address4;
    private String phone;
    private Date birthday;
    private String email;
    private GenderEnum gender;
    private Instant createdAt;
    private Instant updatedAt;
}
