package nl.projectautoplanner.projectautoplannerwebapi.Controllers;

import nl.projectautoplanner.projectautoplannerwebapi.DTO.Request.ProjectRequestDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DTO.response.OnderdeelResponseDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DTO.response.ProjectResponseDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Project;
import nl.projectautoplanner.projectautoplannerwebapi.Services.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/projecten")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    @PostMapping
    public ResponseEntity<ProjectResponseDTO> addProject(@RequestBody ProjectRequestDTO requestDTO) {
        Project savedProject = projectService.createProject(
                requestDTO.projectnaam,
                requestDTO.merk,
                requestDTO.model);
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
    private ProjectResponseDTO convertToDTO(Project project) {
        ProjectResponseDTO dto = new ProjectResponseDTO();
        dto.id = project.getId();
        dto.projectnaam = project.getProjectnaam();
        dto.merk = project.getMerk();
        dto.model = project.getModel();
        if (project.getOnderdelen() != null) {
            dto.onderdelen = project.getOnderdelen().stream()
                    .map(onderdeel -> {
                        OnderdeelResponseDTO onderdeelResponseDTO = new OnderdeelResponseDTO();
                        onderdeelResponseDTO.onderdeelnaam = onderdeel.getOnderdeelnaam();
                        onderdeelResponseDTO.artikelnummer = onderdeel.getArtikelnummer();
                        onderdeelResponseDTO.prijs = onderdeel.getPrijs();
                        onderdeelResponseDTO.bestelstatus = onderdeel.getBestelstatus();
                        onderdeelResponseDTO.projectId = project.getId();
                        return onderdeelResponseDTO;
                    })
                            .toList();
        }
        return dto;
    }
}
