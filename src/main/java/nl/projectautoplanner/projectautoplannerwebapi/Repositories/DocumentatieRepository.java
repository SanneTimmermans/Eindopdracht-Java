package nl.projectautoplanner.projectautoplannerwebapi.Repositories;


import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Documentatie;
import org.springframework.data.domain.Limit;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DocumentatieRepository extends JpaRepository<Documentatie, Long> {
    List<Documentatie> findByProjectId(Long projectId);
}
