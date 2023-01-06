package com.study.trainingboard.domain.article.dto.security;

import com.study.trainingboard.domain.article.dto.UserAccountDto;
import lombok.Getter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
public class BoardPrincipal implements UserDetails {
    private String email;
    private String password;
    private String nickname;
    private Collection<? extends GrantedAuthority> authorities;

    private BoardPrincipal(
            String email,
            String password,
            String nickname,
            Collection<? extends GrantedAuthority> authorities
    ) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.authorities = authorities;
    }

    public static BoardPrincipal of(
            String email,
            String password,
            String nickname
    ) {
        Set<RoleType> roleTypes = Set.of(RoleType.USER);

        return new BoardPrincipal(
                email,
                password,
                nickname,
                roleTypes.stream()
                        .map(RoleType::getName)
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toUnmodifiableSet())
        );
    }

    public static BoardPrincipal from(UserAccountDto dto) {
        return BoardPrincipal.of(
                dto.email(),
                dto.password(),
                dto.nickname()
        );
    }

    public UserAccountDto toDto() {
        return UserAccountDto.of(
                email,
                password,
                nickname
        );
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

    @Getter
    public enum RoleType {
        USER("ROLE_USER");
        private final String name;

        RoleType(String name) {
            this.name = name;
        }
    }
}
