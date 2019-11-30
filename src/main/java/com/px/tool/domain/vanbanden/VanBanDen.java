package com.px.tool.domain.vanbanden;

import com.px.tool.domain.RequestType;
import com.px.tool.infrastructure.model.request.EntityDefault;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.Type;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Getter
@Setter
@ToString
@Entity
@Table(name = "van_ban_den")
public class VanBanDen extends EntityDefault {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public Long vbdId;

    @Column
    public Long noiNhan;

    @Column
    @Type(type="text")
    public String noiDung;

    @Column
    public String type;

    @Column
    private Boolean read;

    @Column
    @Enumerated
    public RequestType requestType;

}
