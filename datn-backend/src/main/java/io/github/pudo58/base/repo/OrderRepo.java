package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Order;
import io.github.pudo58.dto.OrderRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.sql.Date;
import java.util.List;
import java.util.UUID;

@Repository
public interface OrderRepo extends JpaRepository<Order, UUID>, JpaSpecificationExecutor<Order> {
    Order getByTourIdAndUserIdAndStatusIn(UUID tourId, UUID userId, List<Integer> statusList);

    @Query("""
            select o from Order o where o.user.id = :#{#model.userId} 
            and (:#{#model.startDate} is null or o.createDate >= :#{#model.startDate})
            and (:#{#model.endDate} is null or o.createDate <= :#{#model.endDate})
    """)
    Page<Order> findBySearchForUser(OrderRequest model, Pageable pageable);

    @Query("""
            select o from Order o where
             (:#{#model.startDate} is null or o.createDate >= :#{#model.startDate})
            and (:#{#model.endDate} is null or o.createDate <= :#{#model.endDate})
    """)
    Page<Order> findBySearch(OrderRequest model, Pageable pageable);

    int countByStatus(int status);
    @Query("""
            select coalesce(sum(o.totalPayment),0) from Order o where o.status = :status
    """)
    int sumTotalPaymentByStatus(int status);

    @Query("""
            select coalesce(sum(o.totalPayment),0) from Order o where o.status = :status and month(o.createDate) = month(:currentDate) and year(o.createDate) = year(:currentDate)
    """)
    int sumTotalPaymentByStatus(int status, Date currentDate);

    @Query("""
            select count(o.id) from Order o where o.status = :status and month(o.createDate) = month(:currentDate) and year(o.createDate) = year(:currentDate)
    """)
    int countSpecificMonthByStatus(int status,  Date currentDate);

}
