package nl.projectautoplanner.projectautoplannerwebapi.Contollers;

import nl.projectautoplanner.projectautoplannerwebapi.DTO.response.GebruikerResponseDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Gebruiker;
import nl.projectautoplanner.projectautoplannerwebapi.Services.GebruikerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gebruikers")
public class GebruikerController {

    private final GebruikerService gebruikerService;

    public GebruikerController(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }

    private GebruikerResponseDTO convertToDTO(Gebruiker g) {
        GebruikerResponseDTO dto = new GebruikerResponseDTO();
        dto.id = g.getId();
        dto.gebruikersnaam = g.getGebruikersnaam();
        dto.voornaam = g.getVoornaam();
        dto.achternaam = g.getAchternaam();
        dto.email = g.getEmail();
        dto.telefoon = g.getTelefoon();
        dto.rol = g.getRol();
        return dto;
    }
    @PostMapping
    public ResponseEntity<GebruikerResponseDTO> createGebruiker(@RequestBody Gebruiker gebruiker) {
        gebruiker.setRol(Gebruiker.GebruikerRol.EIGENAAR);
        Gebruiker savedGebruiker = gebruikerService.saveGebruiker(gebruiker);
        GebruikerResponseDTO dto = new GebruikerResponseDTO();
        return ResponseEntity.ok(convertToDTO(savedGebruiker));
    }
    @GetMapping
    public ResponseEntity<GebruikerResponseDTO> getGebruiker(@PathVariable Long id) {
        Gebruiker g = gebruikerService.getGebruikerById(id);
        return ResponseEntity.ok(convertToDTO(g));
    }
    @PutMapping("/{id}")
    public ResponseEntity<GebruikerResponseDTO> updateGebruiker(@PathVariable Long id, @RequestBody Gebruiker nieuweData) {
        Gebruiker updated = gebruikerService.updateGebruiker(id, nieuweData);
        return ResponseEntity.ok(convertToDTO(updated));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGebruiker(@PathVariable Long id) {
        gebruikerService.deleteGebruiker(id);
        return ResponseEntity.noContent().build();
    }
}
