package nl.projectautoplanner.projectautoplannerwebapi.Controllers;

import nl.projectautoplanner.projectautoplannerwebapi.DTO.Request.OnderdeelRequestDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DTO.response.OnderdeelResponseDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Onderdeel;
import nl.projectautoplanner.projectautoplannerwebapi.Services.OnderdeelService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/onderdelen")
public class OnderdeelController {

    private final OnderdeelService onderdeelService;

    public OnderdeelController(OnderdeelService onderdeelService) {
        this.onderdeelService = onderdeelService;
    }

    @GetMapping("/project/{projectId}")
    public ResponseEntity<List<OnderdeelResponseDTO>> getAllOnderdelen(@PathVariable Long projectId) {
        List<Onderdeel> onderdelen = onderdeelService.getAllOnderdelen(projectId);
        List<OnderdeelResponseDTO> dtos = onderdelen.stream()
                .map(this::convertToDTO)
                .toList();
        return ResponseEntity.ok(dtos);
    }

    @PostMapping("/project/{projectId}")
    public ResponseEntity<OnderdeelResponseDTO> createOnderdeel(@PathVariable Long projectId, @RequestBody OnderdeelRequestDTO requestDTO) {
        Onderdeel savedOnderdeel = onderdeelService.saveOnderdeel(
                requestDTO.onderdeelnaam,
                requestDTO.artikelnummer,
                requestDTO.prijs,
                requestDTO.bestelstatus,
                projectId);
        return ResponseEntity.ok(convertToDTO(savedOnderdeel));
    }
    private OnderdeelResponseDTO convertToDTO(Onderdeel onderdeel) {
        OnderdeelResponseDTO dto = new OnderdeelResponseDTO();
        dto.onderdeelnaam = onderdeel.getOnderdeelnaam();
        dto.artikelnummer = onderdeel.getArtikelnummer();
        dto.prijs = onderdeel.getPrijs();
        dto.bestelstatus = onderdeel.getBestelstatus();

        if (onderdeel.getProject() != null) {
            dto.projectId = onderdeel.getProject().getId();
        }
        return dto;
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOnderdeel(@PathVariable Long id) {
        onderdeelService.deleteOnderdeel(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<OnderdeelResponseDTO> updateOnderdeel(@PathVariable Long id, @RequestBody Onderdeel onderdeel) {
        Onderdeel updated = onderdeelService.updateOnderdeel(id, onderdeel);
        OnderdeelResponseDTO dto = new OnderdeelResponseDTO();
        dto.onderdeelnaam = updated.getOnderdeelnaam();
        dto.artikelnummer = updated.getArtikelnummer();
        dto.prijs = updated.getPrijs();
        dto.bestelstatus = updated.getBestelstatus();

        if (updated.getProject() != null) {
            dto.projectId = updated.getProject().getId();
        }
        return ResponseEntity.ok(dto);
    }
}
