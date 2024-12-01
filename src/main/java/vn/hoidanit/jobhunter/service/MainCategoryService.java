package vn.hoidanit.jobhunter.service;

import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.MainCategory;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.MainCategoryRepository;

@Service
public class MainCategoryService {
    private final MainCategoryRepository mainCategoryRepository;

    public MainCategoryService(MainCategoryRepository mainCategoryRepository) {
        this.mainCategoryRepository = mainCategoryRepository;
    }

    public MainCategory handleCreateMainCategory(MainCategory mainCategory) {
        return this.mainCategoryRepository.save(mainCategory);
    }

    public Optional<MainCategory> handleGetMainCategoryById(long mainCategoryId) {
        return this.mainCategoryRepository.findById(mainCategoryId);
    }

    public ResultPaginationDTO handleGetAllMainCategory(Specification<MainCategory> spec, Pageable pageable) {
        Page<MainCategory> pCategory = this.mainCategoryRepository.findAll(spec, pageable);
        
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pCategory.getNumber() + 1);
        mt.setPageSize(pCategory.getSize());

        mt.setPages(pCategory.getTotalPages());
        mt.setTotal(pCategory.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pCategory.getContent());
        
        return rs;
    }
}
