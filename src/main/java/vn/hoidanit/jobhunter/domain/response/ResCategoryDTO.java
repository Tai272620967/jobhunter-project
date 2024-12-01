package vn.hoidanit.jobhunter.domain.response;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResCategoryDTO {
    private Long id;
    private String name;
    private String imageUrl;

    // Định nghĩa parentCategory
    private ParentCategory parentCategory;

    // Định nghĩa danh sách subcategories
    private List<ResCategoryDTO> subCategories;

    @Getter
    @Setter
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ParentCategory {
        private long id;
        private String name;
        private String imageUrl;
    }    
}
