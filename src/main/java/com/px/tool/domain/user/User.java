package com.px.tool.domain.user;

import com.px.tool.infrastructure.model.request.EntityDefault;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User extends EntityDefault implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long userId;

    @Column
    private String email;

    @Column
    private String password;

    @Column
    @Type(type = "text")
    private String signImg;

    @Column
    private String fullName;

    @ManyToMany
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "phongBanId")
    private PhongBan phongBan;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return password;
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

    public String getFullName() {
        return StringUtils.isEmpty(fullName) ? email : fullName;
    }

    public boolean isTroLyKT() {
        return // TODO cap 4a
                this.phongBan != null && (
                        phongBan.getGroup().equals(8)
                                || phongBan.getGroup().equals(9)
                );
    }

    public boolean isToTruong() {
        return // TODO cap 4b
                this.phongBan != null && (
                        phongBan.getGroup().equals(17)
                                || phongBan.getGroup().equals(18)
                                || phongBan.getGroup().equals(19)
                                || phongBan.getGroup().equals(20)
                                || phongBan.getGroup().equals(21)
                                || phongBan.getGroup().equals(22)
                                || phongBan.getGroup().equals(23)
                                || phongBan.getGroup().equals(24)
                );
    }

    public boolean isQuanDocPhanXuong() {
        return // TODO cap 3
                this.phongBan != null && (
                        phongBan.getGroup().equals(17)
                                || phongBan.getGroup().equals(18)
                                || phongBan.getGroup().equals(19)
                                || phongBan.getGroup().equals(20)
                                || phongBan.getGroup().equals(21)
                                || phongBan.getGroup().equals(22)
                                || phongBan.getGroup().equals(23)
                                || phongBan.getGroup().equals(24)
                );
    }
}
