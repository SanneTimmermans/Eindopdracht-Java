package nl.projectautoplanner.projectautoplannerwebapi.Services;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Factuur;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Onderdeel;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Project;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.FactuurRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.LogboekRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.OnderdeelRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.ProjectRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class FactuurService {
    private final FactuurRepository factuurRepository;
    private final ProjectRepository projectRepository;
    private final LogboekRepository logboekRepository;
    private final OnderdeelRepository onderdeelRepository;

    public FactuurService(FactuurRepository factuurRepository,
                          ProjectRepository projectRepository,
                          LogboekRepository logboekRepository,
                          OnderdeelRepository onderdeelRepository) {
        this.factuurRepository = factuurRepository;
        this.projectRepository = projectRepository;
        this.logboekRepository = logboekRepository;
        this.onderdeelRepository = onderdeelRepository;
    }
    public Factuur genereerFactuur(Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project niet gevonden"));
        double uurtarief = 75.0;
        double urenKosten = logboekRepository.findByProject_Id(projectId).stream()
                .mapToDouble(log -> log.getUren() * uurtarief)
                .sum();
        double onderdelenKosten = onderdeelRepository.findByProject_Id(projectId).stream()
                .mapToDouble(Onderdeel::getPrijs)
                .sum();
        Factuur factuur = new Factuur();
        factuur.setProject(project);
        factuur.setFactuurDatum(LocalDate.now());
        factuur.setTotaalBedrag(urenKosten + onderdelenKosten);
        factuur.setBetaald(false);

        return factuurRepository.save(factuur);
    }
}