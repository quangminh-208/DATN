package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Category;
import io.github.pudo58.dto.CategorySearchRequest;
import io.github.pudo58.record.CategoryRecord;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface CategoryRepo extends JpaRepository<Category, UUID>, JpaSpecificationExecutor<Category> {
    @Query("""
            select c from Category c where c.parent is null
            """)
    List<Category> findRootCategory();

    @Query("""
            select c from Category c where c.parent.id = :parentId
            """)
    List<Category> findByParentId(UUID parentId);

    @Query("""
            select new io.github.pudo58.record.CategoryRecord(c.id, c.name, c.parent) from Category c left join
            c.parent p
             where
            (:#{#model.keyword} is null or c.name like %:#{#model.keyword}%)
            and c.status = io.github.pudo58.constant.CategoryConst.STATUS_ACTIVE
            """)
    Page<CategoryRecord> findBySearch(CategorySearchRequest model, Pageable pageable);
}
