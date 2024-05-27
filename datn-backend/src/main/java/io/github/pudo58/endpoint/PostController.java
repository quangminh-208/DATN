package io.github.pudo58.endpoint;

import io.github.pudo58.base.controller.AbstractController;
import io.github.pudo58.base.entity.Post;
import io.github.pudo58.base.service.PostService;
import io.github.pudo58.dto.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/post")
public class PostController extends AbstractController<Post> {
    private final PostService postService;

    @PostMapping("/findBySearch")
    public Page<Post> findBySearch(@RequestBody PostRequest model) {
        return this.postService.findBySearch(model);
    }
}
