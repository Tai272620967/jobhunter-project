package vn.hoidanit.jobhunter.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import vn.hoidanit.jobhunter.domain.OtpVerification;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.util.OTPGenerator;

import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.Map;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private OtpVerifycationService otpVerifycationService;

    public void sendHtmlEmail(String to, String subject) throws MessagingException {
        // Tạo mã OTP
        String otpCode = OTPGenerator.generateOTP();
        System.out.println("Generated OTP Code: " + otpCode);

        // Tạo nội dung email từ HTML Template
        Context context = new Context();
        context.setVariable("otpCode", otpCode); // Truyền mã OTP vào model

        String htmlContent = templateEngine.process("email-template", context);
        // System.out.println("HTML Content: " + htmlContent);

        // Tạo email message
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED, StandardCharsets.UTF_8.name());

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(htmlContent, true);
        helper.setFrom("tai272620967@gmail.com");

        // Gửi email
        mailSender.send(message);

        // Lưu mã OTP vào bảng OtpVerification
        OtpVerification otpEntry = otpVerifycationService.handleFindByEmail(to);
        if (otpEntry == null) {
            otpEntry = new OtpVerification();
            otpEntry.setEmail(to);
        }
        otpEntry.setOtpCode(otpCode);
        otpEntry.setExpiryTime(LocalDateTime.now().plusMinutes(30)); // OTP hết hạn sau 30 phút
        otpVerifycationService.handleCreateOtpVerification(otpEntry);
    }
}

