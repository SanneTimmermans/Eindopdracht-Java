package nl.projectautoplanner.projectautoplannerwebapi.Repositories;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Gebruiker;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GebruikerRepository extends JpaRepository<Gebruiker, Long> {
}
