package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Post;
import io.github.pudo58.base.repo.PostRepo;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.dto.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class PostServiceImpl extends AbstractService<Post> implements PostService {
    private final PostRepo postRepo;

    @Override
    public Page<Post> findBySearch(PostRequest model) {
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        return this.postRepo.findBySearch(model, pageable);
    }
}
