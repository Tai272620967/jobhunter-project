package vn.hoidanit.jobhunter.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.hoidanit.jobhunter.domain.OtpVerification;

@Repository
public interface OtpVerifycationRepository extends JpaRepository<OtpVerification, Long>, JpaSpecificationExecutor<OtpVerification> {
    OtpVerification findByEmail(String email);
    void deleteByEmail(String email);
}
