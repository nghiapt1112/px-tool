package com.px.tool.model.response;

import com.px.tool.model.AbstractObject;
import com.px.tool.model.TrangThaiRequest;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
public class DashBoard extends AbstractObject {
    protected String key;
    protected String noiDung;
    protected TrangThaiRequest trangThaiRequest;
}
