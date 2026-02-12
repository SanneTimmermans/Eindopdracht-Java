package nl.projectautoplanner.projectautoplannerwebapi.Repositories;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Factuur;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface FactuurRepository extends JpaRepository<Factuur, Long> {
    Optional<Factuur> findByProjectId(Long projectId);

}
