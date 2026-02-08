package nl.projectautoplanner.projectautoplannerwebapi.Repositories;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Project;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProjectRepository extends JpaRepository<Project, Long> {
}
