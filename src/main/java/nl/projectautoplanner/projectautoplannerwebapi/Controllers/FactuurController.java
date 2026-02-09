package nl.projectautoplanner.projectautoplannerwebapi.Controllers;

import nl.projectautoplanner.projectautoplannerwebapi.DTO.Request.FactuurRequestDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DTO.response.FactuurResponseDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Factuur;
import nl.projectautoplanner.projectautoplannerwebapi.Services.FactuurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/facturen")
public class FactuurController {

    private final FactuurService factuurService;

    public FactuurController(FactuurService factuurService) {
        this.factuurService = factuurService;
    }

    private FactuurResponseDTO convertToDTO(Factuur factuur) {
        FactuurResponseDTO dto = new FactuurResponseDTO();
        dto.id = factuur.getId();
        dto.factuurDatum = factuur.getFactuurDatum();
        dto.totaalBedrag = factuur.getTotaalBedrag();
        dto.isBetaald = factuur.isBetaald();
        if (factuur.getProject() != null) {
            dto.projectId = factuur.getProject().getId();
        }
        return dto;
    }

    @PostMapping("/project/{projectId}")
    public ResponseEntity<FactuurResponseDTO> createFactuur(@PathVariable Long projectId) {
        FactuurRequestDTO dto = new FactuurRequestDTO();
        dto.projectId = projectId;
        Factuur factuur = factuurService.genereerFactuur(dto);
        return ResponseEntity.ok(convertToDTO(factuur));
    }
    @GetMapping("/project/{projectId}")
    public ResponseEntity<FactuurResponseDTO> getFactuur(@PathVariable Long projectId) {
        Factuur factuur = factuurService.getFactuurByProject_Id(projectId);
        return ResponseEntity.ok(convertToDTO(factuur));
    }
    @GetMapping
    public ResponseEntity<List<FactuurResponseDTO>> getAllFacturen() {
        List<Factuur> facturen = factuurService.getAllFacturen();
        List<FactuurResponseDTO> dtos = facturen.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
        return ResponseEntity.ok(dtos);
    }
}
