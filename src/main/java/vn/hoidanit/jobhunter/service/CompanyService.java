package vn.hoidanit.jobhunter.service;

import java.util.Optional;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.repository.CompanyRepositoty;
import vn.hoidanit.jobhunter.repository.UserRepositoty;
import vn.hoidanit.jobhunter.domain.Company;
import vn.hoidanit.jobhunter.domain.User;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;

@Service
public class CompanyService {
    
    private final CompanyRepositoty companyRepositoty;
    private final UserRepositoty userRepositoty;

    public CompanyService(CompanyRepositoty companyRepositoty, UserRepositoty userRepositoty) {
        this.companyRepositoty = companyRepositoty;
        this.userRepositoty = userRepositoty;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepositoty.save(company);
    }

    public ResultPaginationDTO handleGetAllCompany(Specification<Company> spec, Pageable pageable) {
        Page<Company> pCompany = this.companyRepositoty.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

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
        Optional<Company> comOptional = this.companyRepositoty.findById(id);
        if (comOptional.isPresent()) {
            Company com = comOptional.get();
            // fetch all user belong to this company
            List<User> users = this.userRepositoty.findByCompany(com);
            this.userRepositoty.deleteAll(users);
        }
        
        this.companyRepositoty.deleteById(id);
    }

    public Optional<Company> findById(long id) {
       return this.companyRepositoty.findById(id);
    }
}
