package vn.hoidanit.jobhunter.domain.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import vn.hoidanit.jobhunter.util.constant.GenderEnum;

import java.sql.Date;
import java.time.Instant;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResUserDTO {
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

    // @Getter
    // @Setter
    // @AllArgsConstructor
    // @NoArgsConstructor
    // public static class CompanyUser {
    //     private long id;
    //     private String name; 
    // }
}
