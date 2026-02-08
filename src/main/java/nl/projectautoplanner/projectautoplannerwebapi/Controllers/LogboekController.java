package nl.projectautoplanner.projectautoplannerwebapi.Controllers;

import nl.projectautoplanner.projectautoplannerwebapi.DTO.Request.LogboekRequestDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DTO.response.LogboekResponseDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Logboek;
import nl.projectautoplanner.projectautoplannerwebapi.Services.LogboekService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/logboeken")
public class LogboekController {

    private final LogboekService logboekService;

    public LogboekController(LogboekService logboekService) {
        this.logboekService = logboekService;
    }
    @PostMapping
    public ResponseEntity<LogboekResponseDTO> createLogboek(@RequestBody LogboekRequestDTO requestDTO) {
        Logboek savedLogboek = logboekService.saveLogboek(
                requestDTO.beschrijving,
                requestDTO.uren,
                requestDTO.monteurId,
                requestDTO.projectId);
        return ResponseEntity.ok(convertToDTO(savedLogboek));
    }
    private LogboekResponseDTO convertToDTO(Logboek logboek) {
        LogboekResponseDTO dto = new LogboekResponseDTO();
        dto.id = logboek.getId();
        dto.beschrijving = logboek.getBeschrijving();
        dto.uren = logboek.getUren();
        dto.datumTijd = logboek.getDatumTijd();
        if (logboek.getMonteur() != null) {
            dto.monteurNaam = logboek.getMonteur().getVoornaam() + " " + logboek.getMonteur().getAchternaam();
        }
        if (logboek.getProject() != null) {
            dto.projectNaam = logboek.getProject().getProjectnaam();
        }
        return dto;
    }
    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<LogboekResponseDTO>> getLogboekByProject(@PathVariable Long projectId) {
        List<Logboek> regels = logboekService.getRegelsByProjectId(projectId);
        List<LogboekResponseDTO> dtos = regels.stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }
}
