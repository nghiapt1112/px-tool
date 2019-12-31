package com.px.tool.domain.file.repository;

import com.px.tool.domain.RequestType;
import com.px.tool.domain.file.FileStorage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.io.File;
import java.util.List;
import java.util.Optional;

@Repository
public interface FileStorageRepository extends JpaRepository<FileStorage, Long> {
    List<FileStorage> findByFileName(String fileName);

    @Query("SELECT f FROM FileStorage f WHERE f.requestId = :id AND f.requestType = :rtype")
    Optional<List<FileStorage>> findFiles(@Param("id") Long id, @Param("rtype") RequestType requestType);

    Optional<FileStorage> findByRequestId(Long requestId);
}
