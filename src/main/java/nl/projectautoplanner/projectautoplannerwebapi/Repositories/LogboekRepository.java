package nl.projectautoplanner.projectautoplannerwebapi.Repositories;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Logboek;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface LogboekRepository extends JpaRepository<Logboek, Long> {
    List<Logboek> findByProjectId(Long projectId);
}

