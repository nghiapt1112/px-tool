package com.px.tool.infrastructure.model.payload;

import com.px.tool.domain.user.User;

import java.util.Collection;
import java.util.Map;

public abstract class AbstractPayLoad extends AbstractObject {
    public abstract void processSignImgAndFullName(Map<Long, User> userById);

    public abstract Collection<Long> getDeletedIds(Object o);
}
