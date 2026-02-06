package nl.projectautoplanner.projectautoplannerwebapi.Contollers;

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
    @PostMapping("/{projectId}")
    public ResponseEntity<DocumentatieResponseDTO> addDocumentatie(
            @PathVariable Long projectId,
            @RequestBody Documentatie docRequest) {

        Documentatie saved = documentatieService.saveDocumentatie(
                docRequest.getBestandsnaam(),
                docRequest.getBestandtype(),
                docRequest.getUrl(),
                docRequest.getTekstInhoud(),
                projectId);

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
