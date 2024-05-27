package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.entity.Post;
import io.github.pudo58.base.service.PostService;
import io.github.pudo58.dto.PostRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/v2/post")
public class PostV2Controller {
    private final PostService postService;

    @GetMapping("/findAll")
    public List<Post> findAll() {
        return this.postService.findAll();
    }

    @GetMapping("/get/{id}")
    public Post get(@PathVariable UUID id) {
        return this.postService.findById(id);
    }

    @PostMapping("/findBySearch")
    public Page<Post> findBySearch(@RequestBody PostRequest model) {
        return this.postService.findBySearch(model);
    }
}
