package nl.projectautoplanner.projectautoplannerwebapi.Repositories;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Logboek;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LogboekRepository extends JpaRepository<Logboek, Long> {
    List<Logboek> findByProjectId(Long projectId);
}

