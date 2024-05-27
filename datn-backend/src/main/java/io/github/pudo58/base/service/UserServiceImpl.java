package io.github.pudo58.base.service;

import io.github.pudo58.base.entity.Role;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.exception.UnauthorizedException;
import io.github.pudo58.base.repo.RoleRepo;
import io.github.pudo58.base.repo.UserRepo;
import io.github.pudo58.base.service.core.AbstractService;
import io.github.pudo58.constant.UserConst;
import io.github.pudo58.dto.UserRegisterRequest;
import io.github.pudo58.dto.UserSearchRequest;
import io.github.pudo58.record.UserRegisterRecord;
import lombok.AllArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.Base64;
import java.util.List;
import java.util.UUID;

@Service
@AllArgsConstructor
public class UserServiceImpl extends AbstractService<User> implements UserService {
    private final UserRepo userRepo;
    private final PasswordEncoder passwordEncoder;
    private final RoleRepo roleRepo;

    @Override
    @Transactional(rollbackFor = RuntimeException.class, readOnly = true)
    public User save(User user) {
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return super.save(user);
    }

    @Override
    public User update(UUID uuid, User user) {
        User oldUser = this.repo.findById(uuid).orElse(null);
        Assert.notNull(oldUser, () -> "Không tìm thấy người dùng");
        if (user.getPassword() != null) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        user.setUsername(oldUser.getUsername());
        user.setRoleList(oldUser.getRoleList());
        user.setAvatar(oldUser.getAvatar());
        return super.update(uuid, user);
    }

    @Override
    public User findByUsername(String username) {
        return this.userRepo.findByUsername(username);
    }

    @Transactional(rollbackFor = RuntimeException.class)
    @Override
    public UserRegisterRecord register(UserRegisterRequest model) {
        User user = new User();
        Assert.notNull(model.getUsername(), () -> "Username is required");
        Assert.notNull(model.getPassword(), () -> "Password is required");
        Assert.doesNotContain(model.getUsername(), "@", () -> "Username không được chứa ký tự @");
        Assert.doesNotContain(model.getUsername(), " ", () -> "Username không được chứa khoảng trắng");
        Assert.isTrue(model.getPassword().length() >= 6, () -> "Mật khẩu phải có ít nhất 6 ký tự");
        Assert.isTrue(model.getPassword().equals(model.getConfirmPassword()), () -> "Mật khẩu không khớp");
        if (this.userRepo.findByUsername(model.getUsername()) != null) {
            throw new IllegalArgumentException("Username đã tồn tại");
        }
        user.setUsername(model.getUsername());
        user.setPassword(this.passwordEncoder.encode(model.getPassword()));
        this.addRole(user);
        this.repo.save(user);
        return new UserRegisterRecord(HttpStatus.OK.value(), "Đăng ký thành công");

    }

    @Override
    public void deleteById(UUID uuid) {
        User user = this.repo.findById(uuid).orElse(null);
        Assert.notNull(user, () -> "Không tìm thấy người dùng");
        user.setStatus(UserConst.STATUS_INACTIVE);
        this.repo.save(user);
    }

    @Override
    public User get() {
        UUID id = User.getContextId();
        if (id == null) {
            throw new UnauthorizedException("Bạn chưa đăng nhập");
        }
        User user = this.repo.findById(id).orElse(null);
        if (user == null) {
            throw new UnauthorizedException("Bạn chưa đăng nhập");
        }
        return user;
    }

    @Override
    @Transactional(rollbackFor = RuntimeException.class)
    public User update(User user) {
        User oldUser = this.repo.findById(User.getContextId()).orElse(null);
        Assert.notNull(oldUser, "Bạn chưa đăng nhập");
        oldUser.setUsername(oldUser.getUsername());
        oldUser.setRoleList(oldUser.getRoleList());
        if (user.getAvatarData() != null) {
            user.setAvatarData(user.getAvatarData().substring(user.getAvatarData().indexOf(",") + 1));
            oldUser.setAvatar(Base64.getDecoder().decode(user.getAvatarData()));
        }
        oldUser.setAddress(user.getAddress());
        oldUser.setEmail(user.getEmail());
        return this.repo.save(oldUser);
    }

    @Override
    public Page<User> findBySearch(UserSearchRequest model) {
        Pageable pageable = PageRequest.of(model.getPage(), model.getSize());
        return this.userRepo.findBySearch(model, pageable);
    }

    @Override
    public List<User> findAdmin() {
        return this.userRepo.findAdmin();
    }

    private void addRole(User user) {
        try {
            Role role = roleRepo.findAll((root, query, builder) -> builder.equal(root.get("name"), UserConst.ROLE_USER)).stream().findFirst().orElse(null);
            user.getRoleList().add(role);
        } catch (Throwable e) {
            throw new IllegalArgumentException(e);
        }
    }
}
