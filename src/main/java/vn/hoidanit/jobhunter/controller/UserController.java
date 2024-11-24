package vn.hoidanit.jobhunter.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import jakarta.mail.MessagingException;
import jakarta.validation.Valid;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import vn.hoidanit.jobhunter.domain.OtpVerification;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.request.SendMailDTO;
import vn.hoidanit.jobhunter.domain.request.VerifyCodeDTO;
import vn.hoidanit.jobhunter.domain.response.ResCreateUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUpdateUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResUserDTO;
import vn.hoidanit.jobhunter.domain.response.ResVerifyEmail;
import vn.hoidanit.jobhunter.domain.response.ResVerifyOtp;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.UserService;
import vn.hoidanit.jobhunter.util.anotation.ApiMessage;
import vn.hoidanit.jobhunter.util.error.IdInvalidException;
import vn.hoidanit.jobhunter.service.EmailService;
import vn.hoidanit.jobhunter.service.OtpVerifycationService;

@RestController
@RequestMapping("/api/v1")
public class UserController {

    private final UserService userService;

    private final PasswordEncoder passwordEncoder;

    private final EmailService emailService;

    private final OtpVerifycationService otpVerifycationService;

    public UserController(UserService userService, PasswordEncoder passwordEncoder, EmailService emailService, OtpVerifycationService otpVerifycationService) {
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.emailService = emailService;
        this.otpVerifycationService = otpVerifycationService;
    }

    @PostMapping("/users/register")
    @ApiMessage("Create a new user")
    public ResponseEntity<ResCreateUserDTO> createNewUser(@Valid @RequestBody User postManUser) throws IdInvalidException {
        boolean isEmailExist = this.userService.isEmailExist(postManUser.getEmail());
        if (isEmailExist) {
            throw new IdInvalidException(
                "Email " + postManUser.getEmail() + " đã tồn tại, vui lòng sử dụng email khác.");
        }
        String hashPassword = this.passwordEncoder.encode(postManUser.getPassword());
        postManUser.setPassword(hashPassword);
        User newUser = this.userService.handleCreateUser(postManUser);
        return ResponseEntity.status(HttpStatus.CREATED).body(this.userService.convertToResCreateUserDTO(newUser));
    }

    @DeleteMapping("/users/{id}")
    @ApiMessage("Delete a user")
    public ResponseEntity<Void> deleteUser(@PathVariable("id") long id) throws IdInvalidException {
        User currentUser =  this.userService.handleGetUserById(id);
        this.userService.handleDeleteUser(id);
        if (currentUser == null) {
            throw new IdInvalidException("User với id = " + id + " không tồn tại.");
        }
        this.userService.handleDeleteUser(id);
        return ResponseEntity.ok(null);
    }

    @GetMapping("/users")
    @ApiMessage("fetch all users")
    public ResponseEntity<ResultPaginationDTO> getAllUser(
        @Filter Specification<User> spec,
        Pageable pageable
    ) {

        return ResponseEntity.status(HttpStatus.OK).body(this.userService.handleGetAllUser(spec, pageable));
    }

    @GetMapping("/users/{id}")
    @ApiMessage("fetch user by id")
    public ResponseEntity<ResUserDTO> getUserById(@PathVariable("id") long id) throws IdInvalidException {
        User fetchUser = this.userService.handleGetUserById(id);
        if (fetchUser == null) {
            throw new IdInvalidException("User với id = " + id + " không tồn tại");
        }
        return ResponseEntity.status(HttpStatus.OK).body(this.userService.convertToResUserDTO(fetchUser));
    }

    @PutMapping("/users")
    @ApiMessage("Update a user")
    public ResponseEntity<ResUpdateUserDTO> updateUser(@RequestBody User user) throws IdInvalidException {
        User updateUser = this.userService.handleUpdateUser(user);
        if (updateUser == null) {
            throw new IdInvalidException("User với id = " + user.getId() + " không tồn tại");
        }
        return ResponseEntity.ok(this.userService.convertToResUpdateUserDTO(updateUser));
    }

    @PostMapping("/users/checkRegistMailAddress")
    @ApiMessage("Check mail address")
    public ResponseEntity<ResVerifyEmail> checkRegistMailAddress(@Valid @RequestBody SendMailDTO emailRequest) throws IdInvalidException {
        boolean isEmailExist = this.userService.isEmailExist(emailRequest.getTo());
        if (isEmailExist) {
            throw new IdInvalidException(
                "入力されたメールアドレスは既に登録済みです。");
        } else {
            try {
                Map<String, Object> model = new HashMap<>();
                model.put("name", "Người dùng");
                emailService.sendHtmlEmail(emailRequest.getTo(), emailRequest.getSubject());
                // return ResponseEntity.ok("Email đã được gửi thành công!");
                return ResponseEntity.status(HttpStatus.OK).body(new ResVerifyEmail("メールアドレスが確認されました。コードが送信されましたので、ご確認ください。", true));
            } catch (MessagingException e) {
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                    .body(new ResVerifyEmail("Lỗi khi gửi email: " + e.getMessage(), false));
            }
        }
    }

    @PostMapping("/users/checkVerifyCode")
    @ApiMessage("Check verify code")
    public ResponseEntity<ResVerifyOtp> checkVerifyCode(@Valid @RequestBody VerifyCodeDTO verifyCodeRequest) throws IdInvalidException {
        String email = verifyCodeRequest.getEmail();
        System.out.println("email for verify code" + email);
        String inputCode = verifyCodeRequest.getVerifyCode();
        System.out.println("inputCode" + inputCode);

        // Kiểm tra người dùng có tồn tại hay không
        OtpVerification otpVerification = otpVerifycationService.handleFindByEmail(email);
        System.out.println("otpVerification: " + otpVerification.getOtpCode());
        if (otpVerification == null) {
            throw new IdInvalidException("Email không tồn tại.");
        }

        // Kiểm tra mã xác minh và thời gian hết hạn
        if (otpVerification.getOtpCode() == null || !otpVerification.getOtpCode().equals(inputCode)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResVerifyOtp("確認コードが正しくありません！", false));
        }

        if (otpVerification.getExpiryTime() != null && otpVerification.getExpiryTime().isBefore(LocalDateTime.now())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResVerifyOtp("確認コードの有効期限が切れました。", false));
        }
        this.otpVerifycationService.handleUpdateOtpVerification(otpVerification);
        
        return ResponseEntity.status(HttpStatus.OK).body(new ResVerifyOtp("コードの確認に成功しました！", true));
    }
}
