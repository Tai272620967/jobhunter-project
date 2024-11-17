package vn.hoidanit.jobhunter.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.mail.MessagingException;
import vn.hoidanit.jobhunter.domain.request.SendMailDTO;
import vn.hoidanit.jobhunter.service.EmailService;

@RestController
@RequestMapping("/api/v1")
public class EmailController {

    @Autowired
    private EmailService emailService;

    @PostMapping("/send-mail")
    public ResponseEntity<String> sendHtmlEmail(@RequestBody SendMailDTO emailRequest) {
        try {
            // Dữ liệu để truyền vào template
            Map<String, Object> model = new HashMap<>();
            model.put("name", "Người dùng");

            emailService.sendHtmlEmail(emailRequest.getTo(), emailRequest.getSubject());
            return ResponseEntity.ok("Email đã được gửi thành công!");
        } catch (MessagingException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body("Lỗi khi gửi email: " + e.getMessage());
        }
    }
}

