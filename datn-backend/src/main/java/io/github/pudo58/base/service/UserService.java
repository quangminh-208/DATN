package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.service.core.Service;
import io.github.pudo58.dto.UserRegisterRequest;
import io.github.pudo58.dto.UserSearchRequest;
import io.github.pudo58.record.UserRegisterRecord;
import org.springframework.data.domain.Page;

import java.security.NoSuchAlgorithmException;
import java.util.List;

public interface UserService extends Service<User> {
    User findByUsername(String username);

    UserRegisterRecord register(UserRegisterRequest model) throws NoSuchAlgorithmException;

    User get();

    User update(User user);

    Page<User> findBySearch(UserSearchRequest model);

    List<User> findAdmin();
}
