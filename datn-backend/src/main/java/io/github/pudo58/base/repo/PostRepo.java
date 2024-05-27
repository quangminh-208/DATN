package io.github.pudo58.base.repo;

import io.github.pudo58.base.entity.Post;
import io.github.pudo58.dto.PostRequest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PostRepo extends JpaRepository<Post, UUID> {

    @Query("SELECT p FROM Post p WHERE p.title LIKE %:#{#model.keyword}% or p.content LIKE %:#{#model.keyword}%")
    Page<Post> findBySearch(PostRequest model, Pageable pageable);
}
