package io.github.pudo58.base.override;

import io.github.pudo58.util.Hashable;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AESPasswordEncoder implements PasswordEncoder {
    private final Hashable hashable;

    @Override
    public String encode(CharSequence rawPassword) {
        return hashable.encodeAES(rawPassword);
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        return hashable.matchesAES(rawPassword, encodedPassword);
    }
}
