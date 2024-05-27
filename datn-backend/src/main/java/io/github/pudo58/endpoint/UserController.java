package io.github.pudo58.endpoint;

import io.github.pudo58.base.controller.AbstractController;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.service.UserService;
import io.github.pudo58.dto.UserSearchRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController extends AbstractController<User> {
    private final UserService userService;

    @PostMapping("/findBySearch")
    public Page<User> findBySearch(@RequestBody UserSearchRequest model) {
        return this.userService.findBySearch(model);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteById(@PathVariable UUID id) {
        this.userService.deleteById(id);
    }
}
