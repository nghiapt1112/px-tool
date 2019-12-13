package com.px.tool.domain.user.repository;

import com.px.tool.domain.user.Folder;
import com.px.tool.infrastructure.model.payload.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FolderRepository extends JpaRepository<Folder, Long> {
//    List<Folder> findAll(PageRequest pageRequest);
}
