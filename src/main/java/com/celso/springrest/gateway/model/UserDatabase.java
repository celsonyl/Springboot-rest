package com.celso.springrest.gateway.model;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@Entity(name = "users")
public class UserDatabase implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;
    private String full_name;
    private String password;
    private boolean account_non_expired;
    private boolean account_non_locked;
    private boolean credentials_non_expired;
    private boolean enabled;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_permission",
            joinColumns = {@JoinColumn(name = "id_user")},
            inverseJoinColumns = {@JoinColumn(name = "id_permission")})
    private List<PermissionDatabase> permissions;

    public List<String> getRoles() {
        List<String> roles = new ArrayList<>();

        for (PermissionDatabase permission : this.permissions) {
            roles.add(permission.getDescription());
        }

        return roles;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.permissions;
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.account_non_expired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.account_non_locked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentials_non_expired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFull_name() {
        return full_name;
    }

    public void setFull_name(String full_name) {
        this.full_name = full_name;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setAccount_non_expired(boolean account_non_expired) {
        this.account_non_expired = account_non_expired;
    }

    public void setAccount_non_locked(boolean account_non_locked) {
        this.account_non_locked = account_non_locked;
    }

    public void setCredentials_non_expired(boolean credentials_non_expired) {
        this.credentials_non_expired = credentials_non_expired;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public List<PermissionDatabase> getPermissions() {
        return permissions;
    }

    public void setPermissions(List<PermissionDatabase> permissions) {
        this.permissions = permissions;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDatabase that = (UserDatabase) o;
        return account_non_expired == that.account_non_expired && account_non_locked == that.account_non_locked && credentials_non_expired == that.credentials_non_expired && enabled == that.enabled && Objects.equals(id, that.id) && Objects.equals(username, that.username) && Objects.equals(full_name, that.full_name) && Objects.equals(password, that.password) && Objects.equals(permissions, that.permissions);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, username, password, account_non_expired, account_non_locked, credentials_non_expired, enabled, permissions);
    }
}