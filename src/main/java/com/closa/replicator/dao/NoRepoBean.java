package com.closa.replicator.dao;
import com.closa.replicator.domain.RPExtraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.transaction.annotation.Transactional;
@NoRepositoryBean
public interface NoRepoBean<T extends RPExtraction> extends JpaRepository<T, String> {
@Transactional
public interface ExtractionRepo extends NoRepoBean<RPExtraction>{
}
}
