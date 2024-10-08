package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.repository.CompanyRepositoty;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.dto.Meta;
import vn.hoidanit.jobhunter.domain.dto.ResultPaginationDTO;

@Service
public class CompanyService {
    
    private final CompanyRepositoty companyRepositoty;

    public CompanyService(CompanyRepositoty companyRepositoty) {
        this.companyRepositoty = companyRepositoty;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepositoty.save(company);
    }

    public ResultPaginationDTO handleGetAllCompany(Specification<Company> spec, Pageable pageable) {
        Page<Company> pCompany = this.companyRepositoty.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        Meta mt = new Meta();

        mt.setPage(pCompany.getNumber() + 1);
        mt.setPageSize(pCompany.getSize());

        mt.setPages(pCompany.getTotalPages());
        mt.setTotal(pCompany.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pCompany.getContent());
        
        return rs;
    }

    public Company handleUpdateCompany(Company reqCompany) {
        // Company currentCompany = this.handleGetCompanyById(reqCompany.getId());
        Optional<Company> companyOptional = this.companyRepositoty.findById(reqCompany.getId());
        if (companyOptional.isPresent()) {
            Company currentCompany = companyOptional.get();
            currentCompany.setName(reqCompany.getName());
            currentCompany.setAddress(reqCompany.getAddress());
            currentCompany.setDescription(reqCompany.getDescription());
            currentCompany.setLogo(reqCompany.getLogo());

            return this.companyRepositoty.save(currentCompany);
        }
        return null;
    }

    public void handleDeleteCompany(long id) {
        this.companyRepositoty.deleteById(id);
    }
}
