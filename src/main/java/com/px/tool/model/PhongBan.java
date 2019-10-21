package com.px.tool.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "phong_ban")
public class PhongBan extends EntityDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long phongBanId;

    @Column
    private String name;

    @Column
    private Integer level;


    @OneToMany(mappedBy = "phongBan", cascade = CascadeType.ALL)
    private Set<User> users;
}
