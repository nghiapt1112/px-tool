package com.px.tool.infrastructure.model.payload;

import com.px.tool.infrastructure.utils.JsonUtils;

import java.util.Objects;

public abstract class AbstractObject {

    @Override
    public String toString() {
        return JsonUtils.toJson(this);
    }

    protected boolean getBol(Boolean val) {
        return (Objects.isNull(val)) ? false : val;
    }
}
