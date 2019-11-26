package com.px.tool.domain.request.repository.impl;

import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.payload.ThongKeRequest;
import com.px.tool.domain.request.repository.RequestRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

public class RequestRepositoryCustomImpl  implements RequestRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Request> findPaging(ThongKeRequest thongKeRequest) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT rq FROM Request rq FETCH ALL PROPERTIES ");
        return this.entityManager.createQuery(query.toString()).getResultList();
    }
}