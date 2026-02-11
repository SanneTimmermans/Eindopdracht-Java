package nl.projectautoplanner.projectautoplannerwebapi.Controllers;

import jakarta.validation.Valid;
import nl.projectautoplanner.projectautoplannerwebapi.DTO.Request.ProjectRequestDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DTO.response.OnderdeelResponseDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DTO.response.ProjectResponseDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Gebruiker;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Project;
import nl.projectautoplanner.projectautoplannerwebapi.Services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/projecten")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> addProject(@Valid @RequestBody ProjectRequestDTO requestDTO) {
        Project savedProject = projectService.createProject(requestDTO);
        return ResponseEntity.ok(convertToDTO(savedProject));
    }

    @GetMapping
    public ResponseEntity<List<ProjectResponseDTO>> getProjects() {
        List<Project> projecten = projectService.getAllProjects();
        List<ProjectResponseDTO> dtos = projecten.stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @GetMapping("/{id}")
    public ResponseEntity<ProjectResponseDTO> getProject(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        Project project = projectService.getProjectGevalideerd(id, jwt);
        return ResponseEntity.ok(convertToDTO(project));
    }
    @GetMapping("/eigenaar/{Id}")
    public ResponseEntity<List<ProjectResponseDTO>> getProjectenByEigenaar(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        List<Project> projecten = projectService.getProjectenVanKlant(id, jwt);
        List<ProjectResponseDTO> response = new ArrayList<>();
        for (Project project : projecten) {
            response.add(convertToDTO(project));
        }
        return ResponseEntity.ok(response);
    }
    private ProjectResponseDTO convertToDTO(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.id = project.getId();
        dto.projectnaam = project.getProjectnaam();
        dto.merk = project.getMerk();
        dto.model = project.getModel();

        if (project.getEigenaar() != null) {
            dto.eigenaarNaam = project.getEigenaar().getGebruikersnaam();
        }

        if (project.getMonteurs() != null) {
            dto.monteurNamen = project.getMonteurs().stream()
                    .map(Gebruiker::getGebruikersnaam)
                    .toList();
        }

        if (project.getOnderdelen() != null) {
            dto.onderdelen = project.getOnderdelen().stream()
                    .map(onderdeel -> {
                        OnderdeelResponseDTO oDto = new OnderdeelResponseDTO();
                        oDto.onderdeelnaam = onderdeel.getOnderdeelnaam();
                        oDto.artikelnummer = onderdeel.getArtikelnummer();
                        oDto.prijs = onderdeel.getPrijs();
                        oDto.bestelstatus = onderdeel.getBestelstatus();
                        oDto.projectId = project.getId();
                        return oDto;
                    })
                    .toList();
        }
        return dto;
    }
}
