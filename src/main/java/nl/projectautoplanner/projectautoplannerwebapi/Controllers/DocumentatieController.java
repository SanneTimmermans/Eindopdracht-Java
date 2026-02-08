package nl.projectautoplanner.projectautoplannerwebapi.Controllers;

import nl.projectautoplanner.projectautoplannerwebapi.DTO.Request.DocumentatieRequestDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DTO.response.DocumentatieResponseDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Documentatie;
import nl.projectautoplanner.projectautoplannerwebapi.Services.DocumentatieService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/documentatie")
public class DocumentatieController {
    private final DocumentatieService documentatieService;

    public DocumentatieController(DocumentatieService documentatieService) {
        this.documentatieService = documentatieService;
    }
    @PostMapping
    public ResponseEntity<DocumentatieResponseDTO> addDocumentatie(@RequestBody DocumentatieRequestDTO request) {
        Documentatie saved = documentatieService.saveDocumentatie(
                request.bestandsnaam,
                request.bestandtype,
                request.url,
                request.tekstInhoud,
                request.projectId,
                request.onderdeelnaam);

        return ResponseEntity.ok(convertToDTO(saved));
    }
    private DocumentatieResponseDTO convertToDTO(Documentatie doc) {
        DocumentatieResponseDTO dto = new DocumentatieResponseDTO();
        dto.id = doc.getId();
        dto.bestandsnaam = doc.getBestandsnaam();
        dto.bestandtype = doc.getBestandtype();
        dto.url = doc.getUrl();
        dto.tekstInhoud = doc.getTekstInhoud();

        if (doc.getProject() != null) {
            dto.projectId = doc.getProject().getId();
            dto.projectnaam = doc.getProject().getProjectnaam();
        }
        if (doc.getOnderdeel() != null) {
            dto.onderdeelnaam = doc.getOnderdeel().getOnderdeelnaam();
        }
        return dto;
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<DocumentatieResponseDTO>> getByProject(@PathVariable Long projectId) {
        List<Documentatie> docs = documentatieService.getDocumentatieByProject(projectId);
        List<DocumentatieResponseDTO> dtos = docs.stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocumentatie(@PathVariable Long id) {
        documentatieService.deleteDocumentatie(id);
        return ResponseEntity.noContent().build();
    }
}
