package nl.projectautoplanner.projectautoplannerwebapi.Services;

import nl.projectautoplanner.projectautoplannerwebapi.DTO.Request.ProjectRequestDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Gebruiker;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Project;
import nl.projectautoplanner.projectautoplannerwebapi.Exceptions.BadRequestException;
import nl.projectautoplanner.projectautoplannerwebapi.Exceptions.RecordNotFoundException;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.GebruikerRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.ProjectRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ProjectService {

    private final ProjectRepository projectRepository;
    private final GebruikerRepository gebruikerRepository;

    public ProjectService(ProjectRepository projectRepository, GebruikerRepository gebruikerRepository) {
        this.projectRepository = projectRepository;
        this.gebruikerRepository = gebruikerRepository;
    }

    public Project createProject(ProjectRequestDTO dto) {
        Project project = new Project();
        project.setProjectnaam(dto.projectnaam);
        project.setMerk(dto.merk);
        project.setModel(dto.model);

        Gebruiker eigenaar = gebruikerRepository.findById(dto.eigenaarId)
                .orElseThrow(() -> new RecordNotFoundException("Eigenaar niet gevonden"));
        project.setEigenaar(eigenaar);

        if (dto.monteurIds == null || dto.monteurIds.isEmpty()) {
            throw new BadRequestException("Een project moet minimaal één monteur hebben.");
        }

        List<Gebruiker> monteurs = gebruikerRepository.findAllById(dto.monteurIds);
        project.setMonteurs(monteurs);

        return projectRepository.save(project);
    }

    public List<Project> getAllProjects() {
        List<Project> projecten = projectRepository.findAll();
        if (projecten.isEmpty()) {
            throw new RecordNotFoundException("Er zijn momenteel geen projecten geregistreerd.");
        }
        return projecten;
    }

    public Project getProjectGevalideerd(Long projectId, Jwt jwt) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RecordNotFoundException("Project niet gevonden"));

        String username = jwt.getClaimAsString("preferred_username");
        String roles = jwt.getClaimAsMap("resource_access").get("ProjectautoPlanner").toString();

        boolean isAdmin = roles.contains("ADMIN");
        boolean isToegewezenMonteur = project.getMonteurs().stream()
                .anyMatch(m -> m.getGebruikersnaam().equalsIgnoreCase(username));
        boolean isEigenaar = project.getEigenaar().getGebruikersnaam().equalsIgnoreCase(username);

        if (isAdmin || isToegewezenMonteur || isEigenaar) {
            return project;
        } else {
            throw new BadRequestException("Toegang geweigerd: Je bent niet gemachtigd voor dit project.");
        }
    }
    public List<Project> getProjectenVanKlant(Long klantId) {
        return projectRepository.findByEigenaar_Id(klantId);
    }

}
