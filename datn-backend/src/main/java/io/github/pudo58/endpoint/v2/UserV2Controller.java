package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.service.UserService;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v2/user")
@RequiredArgsConstructor
public class UserV2Controller {
    private final UserService userService;

    @GetMapping("/get")
    public User get() {
        return this.userService.get();
    }

    @PostMapping("/update")
    public User update(@RequestBody User user) {
        return this.userService.update(user);
    }

    @GetMapping("/findAdmin")
    public List<User> findAdmin() {
        return this.userService.findAdmin();
    }
}
