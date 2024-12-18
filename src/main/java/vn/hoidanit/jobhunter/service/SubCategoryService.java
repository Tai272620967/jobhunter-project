package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.MainCategory;
import vn.hoidanit.jobhunter.domain.SubCategory;
import vn.hoidanit.jobhunter.domain.request.SubCategoryDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.MainCategoryRepository;
import vn.hoidanit.jobhunter.repository.SubCategoryRepository;

@Service
public class SubCategoryService {
    private final SubCategoryRepository subCategoryRepository;
    private final MainCategoryRepository mainCategoryRepository;

    public SubCategoryService(SubCategoryRepository subCategoryRepository, MainCategoryRepository mainCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
        this.mainCategoryRepository = mainCategoryRepository;
    }

    public SubCategory handleCreateSubCategory(SubCategoryDTO subCategoryRequest) {
        MainCategory mainCategory = this.mainCategoryRepository.findById(subCategoryRequest.getMainCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + subCategoryRequest.getMainCategoryId()));

        SubCategory subCategory = new SubCategory();
        subCategory.setName(subCategoryRequest.getName());
        subCategory.setImageUrl(subCategory.getImageUrl());
        subCategory.setMainCategory(mainCategory);
        return this.subCategoryRepository.save(subCategory);
    }

    public ResultPaginationDTO handleGetAllSubCategory(Specification<SubCategory> spec, Pageable pageable) {
        Page<SubCategory> pCategory = this.subCategoryRepository.findAll(spec, pageable);
        
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

    public List<SubCategory> handleFindByMainCategoryId(long mainCategoryId) {
        return this.subCategoryRepository.findByMainCategory_Id(mainCategoryId);
    }

    public Optional<SubCategory> handleGetSubCategoryById(long subCategoryId) {
        return this.subCategoryRepository.findById(subCategoryId);
    }
}
