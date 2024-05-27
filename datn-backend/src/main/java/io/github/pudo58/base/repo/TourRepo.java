package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Tour;
import io.github.pudo58.dto.TourSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TourRepo extends JpaRepository<Tour, UUID>, JpaSpecificationExecutor<Tour> {

    @Query("""
            select t from Tour t
            where (:#{#model.keyword} is null or lower(t.name) like lower(concat('%', :#{#model.keyword}, '%')))
            and  (:#{#model.categoryId} is null or t.category.id = :#{#model.categoryId})
            and (t.status = io.github.pudo58.constant.TourConst.STATUS_ACTIVE)
            """)
    Page<Tour> findBySearch(TourSearchRequest model, Pageable pageable);

    @Query("""
            select t from Tour t
            order by t.createDate desc
            """)
    List<Tour> findByNewest();

    List<Tour> findByCategoryId(UUID categoryId);
}
