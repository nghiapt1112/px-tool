package com.px.tool.domain.user;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.px.tool.domain.request.Request;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Getter
@Setter
@Entity
@Table(name = "folder")
public class Folder {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long folderId;

    @Column
    private String folder1;
    @Column
    private String folder2;
    @Column
    private String folder3;
    @Column
    private String folder4;
    @Column
    private String folder5;
    @Column
    private String folder6;
    @Column
    private String folder7;
    @Column
    private String folder8;
    @Column
    private String folder9;
    @Column
    private String folder10;
    @Column
    private String folder11;
    @Column
    private String folder12;
    @Column
    private String folder13;
    @Column
    private String folder14;
    @Column
    private String folder15;
    @Column
    private String folder16;
    @Column
    private String folder17;
    @Column
    private String folder18;
    @Column
    private String folder19;
    @Column
    private String folder20;

    @JsonBackReference
    @OneToOne(mappedBy = "folder")
    private User user;
}
