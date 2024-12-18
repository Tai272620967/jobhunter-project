package vn.hoidanit.jobhunter.service;

import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import vn.hoidanit.jobhunter.domain.Category;
import vn.hoidanit.jobhunter.domain.Product;
import vn.hoidanit.jobhunter.domain.request.ProductDTO;
import vn.hoidanit.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoidanit.jobhunter.repository.CategoryRepository;
import vn.hoidanit.jobhunter.repository.ProductRepository;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.UUID;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CategoryRepository categoryRepository;
    private final FileService fileService;
    @Value("${base-uri}")
    private String baseURI;

    public ProductService(ProductRepository productRepository, CategoryRepository categoryRepository, FileService fileService) {
        this.productRepository = productRepository;
        this.categoryRepository = categoryRepository;
        this.fileService = fileService;
    }

    public Product handleCreateProduct(ProductDTO productRequest, MultipartFile imageFile) throws IOException, URISyntaxException {
        Category category = categoryRepository.findById(productRequest.getCategoryId())
            .orElseThrow(() -> new IllegalArgumentException("Category not found with ID: " + productRequest.getCategoryId()));

        // Upload hình ảnh
        String imageUrl = uploadImage(imageFile);

        Product product = new Product();
        product.setName(productRequest.getName());
        product.setMinPrice(productRequest.getMinPrice());
        product.setMaxPrice(productRequest.getMaxPrice());
        product.setDescription(productRequest.getDescription());
        product.setImageUrl(imageUrl);
        product.setStockQuantity(productRequest.getStockQuantity());
        product.setCategory(category);

        return productRepository.save(product);
    }

    private String uploadImage(MultipartFile imageFile) throws IOException, URISyntaxException {
        if (imageFile == null || imageFile.isEmpty()) {
            throw new IllegalArgumentException("Image file is required.");
        }

        // create a directory if not exist
        this.fileService.createDirectory(baseURI + "images");

        // store file
        String finalName = this.fileService.store(imageFile, "images");

        // Path uploadPath = Paths.get(basePath, "images");

        // // Tạo thư mục nếu chưa tồn tại
        // if (!Files.exists(uploadPath)) {
        //     Files.createDirectories(uploadPath);
        // }

        // // Tạo tên file ngẫu nhiên để tránh trùng lặp
        // String fileName = UUID.randomUUID().toString() + "_" + imageFile.getOriginalFilename();
        // Path filePath = uploadPath.resolve(fileName);

        // // Lưu file lên server
        // Files.copy(imageFile.getInputStream(), filePath);

        // Trả về đường dẫn URL (không bao gồm `base-path` để phục vụ ảnh qua HTTP)
        // return "/uploads/images/" + fileName;
        return "/images/" + finalName;
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

    public ResultPaginationDTO handleGetProductBySubCategoryId(Long subCategoryId, Pageable pageable) {
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

    public ResultPaginationDTO handleGetProductByCategoryId(Long categoryId, Pageable pageable) {
        Page<Product> pageProduct = this.productRepository.findByCategoryId(categoryId, pageable);
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

    public Optional<Product> handleGetProductById(long productId) {
        return this.productRepository.findById(productId);
    }
}
