package nl.projectautoplanner.projectautoplannerwebapi.Repositories;


import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Documentatie;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface DocumentatieRepository extends JpaRepository<Documentatie, Long> {
    List<Documentatie> findByProject_Id(Long projectId);
}
