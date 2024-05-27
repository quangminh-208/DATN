package io.github.pudo58.base.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.github.pudo58.constant.UserConst;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Entity
@Setter
@Getter
public class User extends BaseEntity implements UserDetails {
    @Column(unique = true)
    private String username;
    private String password;
    @Column(unique = true)
    private String email;
    private Integer status;
    @Lob
    @Column(columnDefinition = "LONGBLOB")
    private byte[] avatar;
    private String address;

    @Transient
    private String avatarData;

    @ManyToMany(targetEntity = Role.class, fetch = FetchType.EAGER)
    @JoinTable(
            name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id")
    )
    private Set<Role> roleList = new HashSet<>();

    public User() {
    }

    @Override
    public void preUpdate() {
        this.nothingToDo();
    }

    @Override
    public void prePersist() {
        this.status = UserConst.STATUS_ACTIVE;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Collection<GrantedAuthority> grantedAuthorities = new ArrayList<>();
        for (Role role : roleList) {
            grantedAuthorities.add(new SimpleGrantedAuthority(role.getName()));
        }
        return grantedAuthorities;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @JsonIgnore
    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }

    public static User getContext() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication.isAuthenticated()) {
            if (authentication.getPrincipal() instanceof User) {
                return (User) authentication.getPrincipal();
            }
        }
        return null;
    }

    public static UUID getContextId() {
        User user = getContext();
        assert user != null;
        return Optional.of(user.getId()).orElse(null);
    }

}
