package vn.hoidanit.jobhunter.domain.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class ProductDTO {
    @NotBlank(message = "Product name is required")
    private String name;

    @NotNull(message = "minPrice is required")
    private BigDecimal minPrice;

    private BigDecimal maxPrice;

    private String description;
    private String imageUrl;

    @NotNull(message = "Stock quantity is required")
    private int stockQuantity;

    @NotNull(message = "Category ID is required")
    private Long categoryId;
    
    // Constructor mới với các tham số
    public ProductDTO(String name, BigDecimal minPrice, BigDecimal maxPrice, String description, Integer stockQuantity, Long categoryId) {
        this.name = name;
        this.minPrice = minPrice;
        this.maxPrice = maxPrice;
        this.description = description;
        this.stockQuantity = stockQuantity;
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getMinPrice() {
        return minPrice;
    }

    public void setMinPrice(BigDecimal minPrice) {
        this.minPrice = minPrice;
    }

    public BigDecimal getMaxPrice() {
        return maxPrice;
    }

    public void setMaxPrice(BigDecimal maxPrice) {
        this.maxPrice = maxPrice;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public int getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(int stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }
}
