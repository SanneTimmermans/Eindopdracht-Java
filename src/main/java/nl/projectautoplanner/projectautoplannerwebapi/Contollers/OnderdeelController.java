package nl.projectautoplanner.projectautoplannerwebapi.Contollers;

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

    @GetMapping
    public List<Onderdeel> getAllOnderdelen() {
        return onderdeelService.getAllOnderdelen();
    }

    @PostMapping
    public ResponseEntity<OnderdeelResponseDTO> createOnderdeel(@RequestBody Onderdeel onderdeel) {
        Onderdeel savedOnderdeel = onderdeelService.saveOnderdeel(onderdeel);
        OnderdeelResponseDTO dto = new OnderdeelResponseDTO();
        dto.onderdeelnaam = savedOnderdeel.getOnderdeelnaam();
        dto.artikelnummer = savedOnderdeel.getArtikelnummer();
        dto.prijs = savedOnderdeel.getPrijs();
        dto.bestelstatus =savedOnderdeel.getBestelstatus();

        if (savedOnderdeel.getProject() != null) {
            dto.projectId = savedOnderdeel.getProject().getId();
        }
        return ResponseEntity.ok(dto);
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
