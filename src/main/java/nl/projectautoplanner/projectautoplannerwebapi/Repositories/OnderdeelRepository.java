package nl.projectautoplanner.projectautoplannerwebapi.Repositories;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Onderdeel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OnderdeelRepository extends JpaRepository<Onderdeel, Long> {
}

