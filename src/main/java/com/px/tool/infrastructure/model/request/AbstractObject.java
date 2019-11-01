package com.px.tool.infrastructure.model.request;

import com.px.tool.infrastructure.utils.JsonUtils;

public class AbstractObject {

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }
}
