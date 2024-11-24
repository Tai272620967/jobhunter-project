package vn.hoidanit.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.Category;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.CategoryService;
import com.turkraft.springfilter.boot.Filter;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.categoryService.handleCreateCategory(category));
    }

    @GetMapping("/main-categories")
    public ResponseEntity<ResultPaginationDTO> getAllMainCategory(
        @Filter Specification<Category> spec, Pageable pageable
    ) {
        return ResponseEntity.ok(this.categoryService.handleGetAllMainCategory(spec, pageable));
    }

    @GetMapping("/sub-categories")
    public ResponseEntity<ResultPaginationDTO> getAllSubCategory(
        @Filter Specification<Category> spec, Pageable pageable
    ) {
        return ResponseEntity.ok(this.categoryService.handleGetAllSubCategory(spec, pageable));
    }
    
}
