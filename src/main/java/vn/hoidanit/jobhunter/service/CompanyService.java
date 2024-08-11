package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.repository.CompanyRepositoty;
import vn.hoidanit.jobhunter.domain.Company;

@Service
public class CompanyService {
    
    private final CompanyRepositoty companyRepositoty;

    public CompanyService(CompanyRepositoty companyRepositoty) {
        this.companyRepositoty = companyRepositoty;
    }

    public Company handleCreateCompany(Company company) {
        return this.companyRepositoty.save(company);
    }

    public List<Company> handleGetAllCompany() {
        return this.companyRepositoty.findAll();
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
