package com.px.tool.domain.request;

import com.px.tool.infrastructure.model.request.AbstractObject;
import com.px.tool.domain.RequestStatus;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DashBoard extends AbstractObject {
    protected String key;
    protected String noiDung;
    protected RequestStatus trangThaiRequest;


}
