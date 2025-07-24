package com.tratra.tratra_app.security;

import com.tratra.tratra_app.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;

public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    // Aquí puedes agregar más getters si quieres exponer otros datos de User

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        // Por ahora sin roles, devolvemos una lista vacía
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return user.getPasswordHash();  // Asegúrate que este getter existe en User
    }

    @Override
    public String getUsername() {
        return user.getUsername();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true; // Cambia según tu lógica de negocio
    }

    @Override
    public boolean isAccountNonLocked() {
        return true; // Cambia según tu lógica de negocio
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true; // Cambia según tu lógica de negocio
    }

    @Override
    public boolean isEnabled() {
        return true; // Cambia según tu lógica de negocio
    }
}
