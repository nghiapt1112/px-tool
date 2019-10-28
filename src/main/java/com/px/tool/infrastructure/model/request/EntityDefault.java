package com.px.tool.infrastructure.model.request;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.MappedSuperclass;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import java.util.Date;

import static com.px.tool.infrastructure.utils.DateTimeUtils.nowAsDate;

@Getter
@Setter
@MappedSuperclass
public abstract class EntityDefault extends AbstractObject {

    @Column
    private Date createdAt;

    @Column
    private Long createdBy;

    @Column
    private Date updatedAt;

    @Column
    private Long updatedBy;

    @Column
    private boolean deleted;

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = nowAsDate();
    }

    @PrePersist
    protected void onSave() {
        this.createdAt = nowAsDate();
        this.createdAt = nowAsDate();
    }
}
