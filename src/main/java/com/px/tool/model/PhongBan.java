package com.px.tool.model;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "phong_ban")
public class PhongBan extends AbstractObject {
}
