package com.closa.replicator.dao;
import com.closa.replicator.domain.RPMethWHERE;
import com.closa.replicator.domain.RPScript;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public interface RPScriptRepo extends JpaRepository<RPScript, Long> {
}
