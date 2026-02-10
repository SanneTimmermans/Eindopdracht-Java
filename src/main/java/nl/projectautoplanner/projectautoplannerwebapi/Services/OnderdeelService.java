package nl.projectautoplanner.projectautoplannerwebapi.Services;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Onderdeel;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Project;
import nl.projectautoplanner.projectautoplannerwebapi.Exceptions.RecordNotFoundException;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.OnderdeelRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.ProjectRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OnderdeelService {

    private final OnderdeelRepository onderdeelRepository;
    private final ProjectRepository projectRepository;

    public OnderdeelService(OnderdeelRepository onderdeelRepository, ProjectRepository projectRepository) {
        this.onderdeelRepository = onderdeelRepository;
        this.projectRepository = projectRepository;
    }

    public List<Onderdeel> getAllOnderdelen(Long projectId) {
        return onderdeelRepository.findByProject_Id(projectId);
    }

    public Onderdeel saveOnderdeel(String onderdeelnaam, String artikelnummer, double prijs, Onderdeel.Bestelstatus bestelstatus, long projectId) {
        Onderdeel onderdeel = new Onderdeel();
        onderdeel.setOnderdeelnaam(onderdeelnaam);
        onderdeel.setArtikelnummer(artikelnummer);
        onderdeel.setPrijs(prijs);
        onderdeel.setBestelstatus(bestelstatus);
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RecordNotFoundException("Project niet gevonden met id: " + projectId));
        onderdeel.setProject(project);
        project.getOnderdelen().add(onderdeel);
        return onderdeelRepository.save(onderdeel);
    }

    public void deleteOnderdeel(long id) {
        onderdeelRepository.deleteById(id);
    }

    public Onderdeel updateOnderdeel(Long id, Onderdeel nieuwOnderdeel) {
        return onderdeelRepository.findById(id).map(bestaandOnderdeel -> {
            bestaandOnderdeel.setOnderdeelnaam(nieuwOnderdeel.getOnderdeelnaam());
            bestaandOnderdeel.setArtikelnummer(nieuwOnderdeel.getArtikelnummer());
            bestaandOnderdeel.setPrijs(nieuwOnderdeel.getPrijs());
            bestaandOnderdeel.setBestelstatus(nieuwOnderdeel.getBestelstatus());
            return onderdeelRepository.save(bestaandOnderdeel);
        }).orElseThrow(() -> new RecordNotFoundException("Onderdeel niet gevonden."));
    }
}
