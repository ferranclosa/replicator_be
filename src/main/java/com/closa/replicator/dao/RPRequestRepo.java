package com.closa.replicator.dao;

import com.closa.replicator.domain.RPFile;
import com.closa.replicator.domain.RPRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RPRequestRepo extends JpaRepository<RPRequest, Long> {
    @Query("select r from RPRequest r inner join r.fileList  where r.requestCode = :code")
    Optional<RPRequest> getByRequestCode(@Param("code") String code);

    @Query("select r from RPRequest r where r.requestCode = :code")
    Optional<RPRequest> findByRequestCode(@Param("code") String code);

    @Query("select r from RPRequest r")
    List<RPRequest> listOfRequests();
}
