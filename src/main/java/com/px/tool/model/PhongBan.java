package com.px.tool.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "phong_ban")
public class PhongBan extends AbstractObject {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long phongBanId;

    @Column
    private String name;

    @Column
    private Integer level;

    @ManyToMany(mappedBy = "phongBans")
    private Set<User> users;
}
