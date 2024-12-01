package vn.hoidanit.jobhunter.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoidanit.jobhunter.domain.Category;
import vn.hoidanit.jobhunter.domain.Product;
import vn.hoidanit.jobhunter.domain.request.ProductDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CategoryRepository;
import vn.hoidanit.jobhunter.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
    }

    public Product handleCreateProduct(ProductDTO productRequest) {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + productRequest.getCategoryId()));

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setMinPrice(productRequest.getMinPrice());
        product.setMaxPrice(productRequest.getMaxPrice());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(productRequest.getImageUrl());
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(category);

        return productRepository.save(product);
    }

    public ResultPaginationDTO handleGetAllProduct(Specification<Product> spec, Pageable pageable) {
        Page<Product> pageProduct = this.productRepository.findAll(spec, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageProduct.getNumber() + 1);
        mt.setPageSize(pageProduct.getSize());

        mt.setPages(pageProduct.getTotalPages());
        mt.setTotal(pageProduct.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageProduct.getContent());

        return rs;
    }

    public ResultPaginationDTO handleGetProductByCategoryId(Long subCategoryId, Pageable pageable) {
        // Page<Product> pageProduct = this.productRepository.findByCategoryId(categoryId, pageable);
        Page<Product> pageProduct = this.productRepository.findByCategory_SubCategory_Id(subCategoryId, pageable);
        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageProduct.getNumber() + 1);
        mt.setPageSize(pageProduct.getSize());

        mt.setPages(pageProduct.getTotalPages());
        mt.setTotal(pageProduct.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pageProduct.getContent());

        return rs;
    }
}
