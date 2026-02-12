package nl.projectautoplanner.projectautoplannerwebapi.Controllers;

import org.springframework.core.io.Resource;
import nl.projectautoplanner.projectautoplannerwebapi.DTO.response.DocumentatieResponseDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Documentatie;
import nl.projectautoplanner.projectautoplannerwebapi.Services.DocumentatieService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/documentatie")
public class DocumentatieController {
    private final DocumentatieService documentatieService;

    public DocumentatieController(DocumentatieService documentatieService) {
        this.documentatieService = documentatieService;
    }
    @PostMapping("/upload")
    public ResponseEntity<DocumentatieResponseDTO> upload(
            @RequestParam("file") MultipartFile file,
            @RequestParam(value = "projectId", required = false) Long projectId,
            @RequestParam(value = "onderdeelId", required = false) Long onderdeelId,
            @RequestParam(value = "tekst", required = false) String tekst) {

        Documentatie saved = documentatieService.storeDocument(file, tekst, projectId, onderdeelId);
        return ResponseEntity.ok(convertToDTO(saved));
    }

    @GetMapping("/download/{bestandsnaam}")
    public ResponseEntity<Resource> download(@PathVariable String bestandsnaam) {
        Resource resource = documentatieService.loadFile(bestandsnaam);
        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; bestandsnaam=\"" + resource.getFilename() + "\"")
                .body(resource);
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
