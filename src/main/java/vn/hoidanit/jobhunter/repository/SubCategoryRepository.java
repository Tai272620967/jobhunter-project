package vn.hoidanit.jobhunter.repository;

import org.springframework.stereotype.Repository;

import vn.hoidanit.jobhunter.domain.SubCategory;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface SubCategoryRepository extends JpaRepository<SubCategory, Long>, JpaSpecificationExecutor<SubCategory> {
    Page<SubCategory> findAll(Specification<SubCategory> spec, Pageable pageable);

    List<SubCategory> findByMainCategory_Id(Long mainCategoryId);
} 
