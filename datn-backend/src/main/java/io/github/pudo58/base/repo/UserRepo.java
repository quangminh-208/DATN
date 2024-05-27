package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.User;
import io.github.pudo58.dto.UserSearchRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface UserRepo extends JpaRepository<User, UUID> {
    User findByUsername(String username);

    @Query("""
            select u from User u
            left join u.roleList r
            where u.username = :username
    """)
    User getByUsername(String username);

    @Query("""
            select u from User u
            where (:#{#model.keyword} is null or u.username like %:#{#model.keyword}% or u.email like %:#{#model.keyword}%)
            and u.status = io.github.pudo58.constant.UserConst.STATUS_ACTIVE
    """)
    Page<User> findBySearch(UserSearchRequest model, Pageable pageable);

    @Query("""
            select u from User u
            left join u.roleList r
            where r.name = 'ROLE_ADMIN'
    """)
    List<User> findAdmin();
}
