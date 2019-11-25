package com.px.tool.domain.mucdich.sudung.repository;

import com.px.tool.domain.mucdich.sudung.MucDichSuDung;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MucDichSuDungRepository extends JpaRepository<MucDichSuDung, Long> {
}
