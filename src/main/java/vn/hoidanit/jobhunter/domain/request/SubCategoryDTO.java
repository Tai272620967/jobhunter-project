package vn.hoidanit.jobhunter.domain.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class SubCategoryDTO {
    @NotBlank(message = "SubCategory name is required")
    private String name;

    private String imageUrl;

    @NotNull(message = "MainCategory ID is required")
    private Long mainCategoryId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Long getMainCategoryId() {
        return mainCategoryId;
    }

    public void setMainCategoryId(Long mainCategoryId) {
        this.mainCategoryId = mainCategoryId;
    }
}
