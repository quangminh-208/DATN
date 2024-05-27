package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Post;
import io.github.pudo58.base.service.core.Service;
import io.github.pudo58.dto.PostRequest;
import org.springframework.data.domain.Page;

public interface PostService extends Service<Post> {
    Page<Post> findBySearch(PostRequest model);
}
