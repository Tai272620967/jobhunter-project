package vn.hoidanit.jobhunter.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.hoidanit.jobhunter.domain.MainCategory;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

@Repository
public interface MainCategoryRepository extends JpaRepository<MainCategory, Long>, JpaSpecificationExecutor<MainCategory> {
    Page<MainCategory> findAll(Specification<MainCategory> spec, Pageable pageable);
} 

