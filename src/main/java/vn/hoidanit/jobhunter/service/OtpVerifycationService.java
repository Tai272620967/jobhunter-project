package vn.hoidanit.jobhunter.service;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.OtpVerification;
import vn.hoidanit.jobhunter.repository.OtpVerifycationRepository;

@Service
public class OtpVerifycationService {
    private final OtpVerifycationRepository otpVerifycationRepository;

    public OtpVerifycationService(OtpVerifycationRepository otpVerifycationRepository) {
        this.otpVerifycationRepository = otpVerifycationRepository;
    }

    public OtpVerification handleFindByEmail(String to) {
        return this.otpVerifycationRepository.findByEmail(to);
    }

    public  OtpVerification handleCreateOtpVerification(OtpVerification otpVerification) {
        return this.otpVerifycationRepository.save(otpVerification);
    }

    public OtpVerification handleUpdateOtpVerification(OtpVerification reqOtpVerification) {
        OtpVerification otpVerification = this.handleFindByEmail(reqOtpVerification.getEmail());
        if (otpVerification != null) {
            otpVerification.setVerifiedOtp(true);
        }
        otpVerification = this.otpVerifycationRepository.save(otpVerification);

        return otpVerification;
    }
}
