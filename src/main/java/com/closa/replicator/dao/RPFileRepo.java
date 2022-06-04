package com.closa.replicator.dao;

import com.closa.replicator.domain.RPFile;
import com.closa.replicator.domain.RPRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RPFileRepo extends JpaRepository<RPFile, Long> {
    @Query("select f from RPFile f where f.request = :request and lower(f.fileName) = :fileName ")
    Optional<RPFile> findByRequestAndFile(@Param("request") RPRequest rpRequest, @Param("fileName") String fileName);
}
