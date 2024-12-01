package vn.hoidanit.jobhunter.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Category;
import vn.hoidanit.jobhunter.domain.MainCategory;
import vn.hoidanit.jobhunter.domain.SubCategory;
import vn.hoidanit.jobhunter.domain.request.CategoryDTO;
import vn.hoidanit.jobhunter.domain.response.ResCategoryDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CategoryRepository;
import vn.hoidanit.jobhunter.repository.SubCategoryRepository;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;
    private final SubCategoryRepository subCategoryRepository;

    public CategoryService(CategoryRepository categoryRepository, SubCategoryRepository subCategoryRepository) {
        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }

    public Category handleCreateCategory(CategoryDTO categoryRequest) {
        SubCategory subCategory = this.subCategoryRepository.findById(categoryRequest.getSubCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + categoryRequest.getSubCategoryId()));

        Category category = new Category();
        category.setName(categoryRequest.getName());
        category.setImageUrl(categoryRequest.getImageUrl());
        category.setSubCategory(subCategory);
        return this.categoryRepository.save(category);
    }

    public ResultPaginationDTO handleGetAllSubCategory(Specification<Category> spec, Pageable pageable) {
        // Page<Category> pCategory = this.categoryRepository.findAll(spec, pageable);
        // Sử dụng Specification để kiểm tra parentId khác null
        Specification<Category> parentIdNotNullSpec = (root, query, criteriaBuilder) -> 
            criteriaBuilder.isNotNull(root.get("parentId"));
        
        // Kết hợp các Specification
        Specification<Category> combinedSpec = spec.and(parentIdNotNullSpec);

        // Truy vấn với Specification kết hợp và phân trang
        Page<Category> pCategory = this.categoryRepository.findAll(combinedSpec, pageable);
        
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

    public ResultPaginationDTO handleGetAllMainCategory(Specification<Category> spec, Pageable pageable) {
        // Page<Category> pCategory = this.categoryRepository.findAll(spec, pageable);
        Specification<Category> parentIdNotNullSpec = (root, query, criteriaBuilder) -> 
            criteriaBuilder.isNull(root.get("parentId"));
        
        // Kết hợp các Specification
        Specification<Category> combinedSpec = spec.and(parentIdNotNullSpec);

        // Truy vấn với Specification kết hợp và phân trang
        Page<Category> pCategory = this.categoryRepository.findAll(combinedSpec, pageable);
        
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

    public Optional<Category> handleGetCategoryById(long categoryId) {
        return this.categoryRepository.findById(categoryId);
    }

    public List<Category> handleGetCategoriesBySubCategoryId(long subCategoryId) {
        return this.categoryRepository.findBySubCategory_Id(subCategoryId);
    }
}
