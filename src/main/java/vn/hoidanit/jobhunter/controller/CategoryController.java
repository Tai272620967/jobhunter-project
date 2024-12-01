package vn.hoidanit.jobhunter.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import vn.hoidanit.jobhunter.domain.Category;
import vn.hoidanit.jobhunter.domain.request.CategoryDTO;
import vn.hoidanit.jobhunter.service.CategoryService;
import vn.hoidanit.jobhunter.util.anotation.ApiMessage;

@RestController
@RequestMapping("/api/v1")
public class CategoryController {
    
    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @PostMapping("/categories")
    public ResponseEntity<Category> createCategory(@RequestBody CategoryDTO categoryRequest) {
        return ResponseEntity.status(HttpStatus.CREATED).body(this.categoryService.handleCreateCategory(categoryRequest));
    }

    @GetMapping("/categories/{id}")
    @ApiMessage("fetch category by id")
    public ResponseEntity<Category> getCategoryById(@PathVariable("id") long id) {
        Optional<Category> optionalCategory = this.categoryService.handleGetCategoryById(id);
        return ResponseEntity.ok().body(optionalCategory.get());
    }

    @GetMapping("/categories/sub-category/{id}")
    @ApiMessage("fetch category by sub-category id")
    public List<Category> getCategoriesBySubCategoryId(@PathVariable("id") long id) {
        return this.categoryService.handleGetCategoriesBySubCategoryId(id);
    }

    // @GetMapping("/main-categories")
    // public ResponseEntity<ResultPaginationDTO> getAllMainCategory(
    //     @Filter Specification<Category> spec, Pageable pageable
    // ) {
    //     return ResponseEntity.ok(this.categoryService.handleGetAllMainCategory(spec, pageable));
    // }

    // @GetMapping("/sub-categories")
    // public ResponseEntity<ResultPaginationDTO> getAllSubCategory(
    //     @Filter Specification<Category> spec, Pageable pageable
    // ) {
    //     return ResponseEntity.ok(this.categoryService.handleGetAllSubCategory(spec, pageable));
    // }
}
