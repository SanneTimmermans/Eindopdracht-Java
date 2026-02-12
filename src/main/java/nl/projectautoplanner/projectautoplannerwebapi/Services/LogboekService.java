package nl.projectautoplanner.projectautoplannerwebapi.Services;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Gebruiker;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Logboek;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Project;
import nl.projectautoplanner.projectautoplannerwebapi.Exceptions.RecordNotFoundException;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.GebruikerRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.LogboekRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.ProjectRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class LogboekService {
    private static final Logger log = LoggerFactory.getLogger(LogboekService.class);
    private final LogboekRepository logboekRepository;
    private final GebruikerRepository gebruikerRepository;
    private final ProjectRepository projectRepository;

    public LogboekService(LogboekRepository logboekRepository,
                          GebruikerRepository gebruikerRepository,
                          ProjectRepository projectRepository) {
        this.logboekRepository = logboekRepository;
        this.gebruikerRepository = gebruikerRepository;
        this.projectRepository = projectRepository;
    }
    public Logboek saveLogboek(String beschrijving, double uren, Long monteurId, Long projectId) {
        Logboek logboek = new Logboek();
        logboek.setBeschrijving(beschrijving);
        logboek.setUren(uren);
        logboek.setDatumTijd(LocalDateTime.now());
        Gebruiker monteur = gebruikerRepository.findById(monteurId)
                .orElseThrow(() -> new RecordNotFoundException("Monteur niet gevonden met id: " + monteurId));
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RecordNotFoundException("Project niet gevonden met id: " + projectId));
        logboek.setMonteur(monteur);
        logboek.setProject(project);
        project.getLogboeken().add(logboek);
        return logboekRepository.save(logboek);
    }

    public List<Logboek> getRegelsByProjectId(Long projectId) {
        return logboekRepository.findByProject_Id(projectId);
    }
}
