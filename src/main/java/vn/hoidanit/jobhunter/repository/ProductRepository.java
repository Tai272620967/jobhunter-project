package vn.hoidanit.jobhunter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import vn.hoidanit.jobhunter.domain.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findAll(Specification<Product> spec, Pageable pageable);
    Page<Product> findByCategoryId(Long categoryId, Pageable pageable);

    // Lấy danh sách sản phẩm theo SubCategory ID
    Page<Product> findByCategory_SubCategory_Id(Long subCategoryId, Pageable pageable);
}
