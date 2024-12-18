package vn.hoidanit.jobhunter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.domain.SubCategory;
import vn.hoidanit.jobhunter.domain.request.SubCategoryDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.SubCategoryService;
import vn.hoidanit.jobhunter.util.anotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class SubCategoryController {
    private final SubCategoryService subCategoryService;

    public SubCategoryController(SubCategoryService subCategoryService) {
        this.subCategoryService = subCategoryService;
    }

    @PostMapping("sub-categories")
    public ResponseEntity<SubCategory> createSubCategory(@RequestBody SubCategoryDTO subCategoryRequest) {
        // return ResponseEntity.ok(this.subCategoryService.handleCreateSubCategory(subCategory));
        return ResponseEntity.status(HttpStatus.CREATED).body(this.subCategoryService.handleCreateSubCategory(subCategoryRequest));
    }

    @GetMapping("sub-categories")
    public ResponseEntity<ResultPaginationDTO> getAllSubCategory(
        @Filter Specification<SubCategory> spec, Pageable pageable
    ) {
        return ResponseEntity.ok(this.subCategoryService.handleGetAllSubCategory(spec, pageable));
    }

    @GetMapping("sub-categories/main-category/{id}")
    public List<SubCategory> getCategoriesByMainCategoryId(@PathVariable("id") long id) {
        return this.subCategoryService.handleFindByMainCategoryId(id);
    }

    @GetMapping("sub-categories/{id}")
    @ApiMessage("fetch sub-category by id")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable("id") long id) {
        Optional<SubCategory> optionalSubCategory = this.subCategoryService.handleGetSubCategoryById(id);
        return ResponseEntity.ok().body(optionalSubCategory.get());
    }
}
