package io.github.pudo58.endpoint.v2;

import io.github.pudo58.base.entity.Role;
import io.github.pudo58.base.entity.User;
import io.github.pudo58.base.exception.UnauthorizedException;
import io.github.pudo58.base.service.JwtService;
import io.github.pudo58.base.service.TokenService;
import io.github.pudo58.base.service.UserService;
import io.github.pudo58.constant.UserConst;
import io.github.pudo58.dto.AuthRequest;
import io.github.pudo58.dto.UserRegisterRequest;
import io.github.pudo58.record.AlertResponseRecord;
import io.github.pudo58.record.TokenRecord;
import io.github.pudo58.record.UserRecord;
import io.github.pudo58.record.UserRegisterRecord;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.util.Assert;
import org.springframework.web.bind.annotation.*;

import java.security.NoSuchAlgorithmException;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v2")
@RequiredArgsConstructor
public class AuthV2Controller {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final UserService userService;
    private final TokenService tokenService;
    private final HttpServletRequest request;

    @PostMapping("/login")
    public TokenRecord authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
        try {
            Assert.notNull(authRequest.getUsername(), "Tài khoản không được để trống");
            Assert.notNull(authRequest.getPassword(), "Mật khẩu không được để trống");
            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword()));
            if (authentication.isAuthenticated()) {
                User user = (User) authentication.getPrincipal();
                if (UserConst.STATUS_INACTIVE.equals(user.getStatus())) {
                    throw new UnauthorizedException("Tài khoản của bạn đã bị khóa");
                }
                Set<String> roleList = user.getRoleList().stream().map(Role::getName).collect(Collectors.toSet());
                String username = authRequest.getUsername();
                boolean rememberMe = authRequest.isRememberMe();
                String token = rememberMe ? jwtService.generateRefreshToken(username) : jwtService.generateToken(username);
                tokenService.saveToken(username, token);
                return new TokenRecord(token, username, roleList, user.getAvatar(), rememberMe);
            } else {
                throw new IllegalArgumentException("Xác thực thất bại");
            }
        } catch (InternalAuthenticationServiceException | BadCredentialsException ex) {
            throw new IllegalArgumentException("Thông tin đăng nhập không chính xác");
        }
    }

    @PostMapping("/register")
    public UserRegisterRecord register(@NonNull @RequestBody UserRegisterRequest model) throws NoSuchAlgorithmException {
        return userService.register(model);
    }

    @PostMapping("/logout")
    public AlertResponseRecord logout() {
        Assert.notNull(User.getContext(), "Bạn chưa đăng nhập");
        String username = User.getContext().getUsername();
        tokenService.deleteTokens(username);
        return new AlertResponseRecord("Đăng xuất thành công, bạn sẽ được chuyển hướng sau 3 giây", HttpStatus.OK.value());
    }

    @PostMapping("/refresh")
    public TokenRecord refreshToken() {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ") && token.length() > 7) {
            token = token.substring(7);
            String username = jwtService.extractUsername(token);
            Assert.notNull(username, "Token không hợp lệ");
            if (jwtService.validateToken(token, userService.findByUsername(username))) {
                String newToken = jwtService.generateToken(username);
                tokenService.saveToken(username, newToken);
                return new TokenRecord(newToken, username, null, null, false);
            } else {
                throw new IllegalArgumentException("Token không hợp lệ");
            }
        } else {
            throw new IllegalArgumentException("Bạn chưa đăng nhập");
        }
    }

    @GetMapping("/profile")
    public User getProfile() {
        return userService.get();
    }
}
