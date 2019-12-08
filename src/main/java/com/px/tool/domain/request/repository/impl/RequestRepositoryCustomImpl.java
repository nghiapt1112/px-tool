package com.px.tool.domain.request.repository.impl;

import com.px.tool.domain.request.Request;
import com.px.tool.domain.request.payload.ThongKePageRequest;
import com.px.tool.domain.request.repository.RequestRepositoryCustom;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.math.BigInteger;
import java.util.List;

public class RequestRepositoryCustomImpl implements RequestRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public List<Request> findPaging(ThongKePageRequest thongKeRequest) {
        StringBuilder query = new StringBuilder();
        query.append("SELECT rq FROM Request rq FETCH ALL PROPERTIES ");
        return this.entityManager.createQuery(query.toString()).getResultList();
    }

    @Override
    public Long findPhuongAnId(Long requsetId) {
        try {
            return ((BigInteger) entityManager
                    .createNativeQuery("SELECT r.pa_id FROM request r WHERE r.request_id = ?1 LIMIT 1")
                    .setParameter(1, requsetId)
                    .getSingleResult())
                    .longValue();
        } catch (Exception e) {
            return -1L;
        }
    }
}