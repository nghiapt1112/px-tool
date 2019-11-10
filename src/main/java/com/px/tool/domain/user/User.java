package com.px.tool.domain.user;

import com.px.tool.infrastructure.model.request.EntityDefault;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.Type;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
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

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_role",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "role_id"))
    private Set<Role> authorities = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "phongBanId")
    private PhongBan phongBan;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
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


    /**
     * (cấp 4b từ account 51 đến 81) là người lập phiếu) => Lưu và Chuyển phiếu cho Trợ lý KT (cấp 4a từ account 29 đến 40)
     * Hiện cấp 4a để lựa chọn và chuyển
     */
    public boolean isToTruong() {
        return this.getLevel() == 4 &&
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

    /**
     * (cấp 4a từ account 29 đến 40) => Lưu và Chuyển phiếu cho Quản đốc phân xưởng (cấp 3 từ account 18 đến 25) (cấp 3 từ account 17 đến 25)
     * Hiện cấp 3 (từ account 17 đến 25) để lựa chọn và chuyển
     */
    public boolean isTroLyKT() {
        return this.getLevel() == 4 &&
                this.phongBan != null && (
                        phongBan.getGroup().equals(8)
                                || phongBan.getGroup().equals(9)
                );
    }

    /**
     * (cấp 3 từ account 18 đến 25) => Lưu và Chuyển phiếu cho Nhân viên vật tư (50a, 50b, 50c)
     * Hiện 50a, 50b, 50c để chọn và chuyển
     * Hiện cấp 4a từ account 29 đến 40 nếu không đồng ý (phải có nguyên nhân ) (lặp đến khi nào Quản đốc đồng ý)
     */
    public boolean isQuanDocPhanXuong() {
        return this.getLevel() == 3 &&
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

    public boolean isAdmin() {
        if (CollectionUtils.isEmpty(this.authorities)) {
            return false;
        } else {
            for (Role authority : authorities) {
                return authority.getAuthority().equalsIgnoreCase("ADMIN");
            }
        }
        return false;
    }

    public int getLevel() {
        if (CollectionUtils.isEmpty(this.authorities)) {
            return -1;
        } else {
            for (Role authority : authorities) {
                switch (authority.getAuthority()) {
                    case "ADMIN":
                        return 1;
                    case "LEVEL2":
                        return 2;
                    case "LEVEL3":
                        return 3;
                    case "LEVEL4":
                        return 4;
                    case "LEVEL5":
                        return 5;
                }
            }
        }
        return -1;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", email='" + email + '\'' +
                ", authorities=" + authorities +
                '}';
    }
}
