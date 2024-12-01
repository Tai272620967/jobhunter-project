package vn.hoidanit.jobhunter.controller;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.turkraft.springfilter.boot.Filter;

import vn.hoidanit.jobhunter.domain.Product;
import vn.hoidanit.jobhunter.domain.request.ProductDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.service.ProductService;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
@RequestMapping("/api/v1")
public class ProductController {
    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/products")
    public ResponseEntity<ResultPaginationDTO> getAllProduct(
        @Filter Specification<Product> spec, Pageable pageable
    ) {
        return ResponseEntity.ok(this.productService.handleGetAllProduct(spec, pageable));
    }

    @PostMapping("/products")
    public ResponseEntity<Product> createProduct(@RequestBody ProductDTO productRequest) {
        // return ResponseEntity.status(HttpStatus.CREATED).body(this.productService.handleCreateProduct(productRequest));
        return ResponseEntity.ok(this.productService.handleCreateProduct(productRequest));
    }

    @GetMapping("/products/sub-category/{subCategoryId}")
    public ResponseEntity<ResultPaginationDTO> getProductByCategoryId(
        @PathVariable("subCategoryId") long subCategoryId, Pageable pageable
    ) {
        return ResponseEntity.ok(this.productService.handleGetProductByCategoryId(subCategoryId, pageable));
    }
}
