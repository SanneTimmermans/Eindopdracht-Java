package nl.projectautoplanner.projectautoplannerwebapi.Controllers;

import jakarta.validation.Valid;
import nl.projectautoplanner.projectautoplannerwebapi.DTO.Request.GebruikerRequestDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DTO.response.GebruikerResponseDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Gebruiker;
import nl.projectautoplanner.projectautoplannerwebapi.Services.GebruikerService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/gebruikers")
public class GebruikerController {

    private final GebruikerService gebruikerService;

    public GebruikerController(GebruikerService gebruikerService) {
        this.gebruikerService = gebruikerService;
    }

    private GebruikerResponseDTO convertToDTO(Gebruiker gebruiker) {
        GebruikerResponseDTO dto = new GebruikerResponseDTO();
        dto.id = gebruiker.getId();
        dto.gebruikersnaam = gebruiker.getGebruikersnaam();
        dto.voornaam = gebruiker.getVoornaam();
        dto.achternaam = gebruiker.getAchternaam();
        dto.email = gebruiker.getEmail();
        dto.telefoon = gebruiker.getTelefoon();
        dto.adres = gebruiker.getAdres();
        dto.rol = gebruiker.getRol();
        return dto;
    }
    @PostMapping
    public ResponseEntity<GebruikerResponseDTO> createGebruiker(@Valid @RequestBody GebruikerRequestDTO request) {
        Gebruiker gebruiker = new Gebruiker();
        gebruiker.setGebruikersnaam(request.gebruikersnaam);
        gebruiker.setVoornaam(request.voornaam);
        gebruiker.setAchternaam(request.achternaam);
        gebruiker.setEmail(request.email);
        gebruiker.setTelefoon(request.telefoon);
        gebruiker.setAdres(request.adres);
        gebruiker.setRol(Gebruiker.GebruikerRol.EIGENAAR);
        Gebruiker savedGebruiker = gebruikerService.saveGebruiker(gebruiker);
        return ResponseEntity.ok(convertToDTO(savedGebruiker));
    }
    @GetMapping("/me")
    public ResponseEntity<GebruikerResponseDTO> getMyProfile(@AuthenticationPrincipal Jwt jwt) {
        String gebruikersnaam = jwt.getClaimAsString("preferred_username");
        Gebruiker gebruiker = gebruikerService.getGebruikerByGebruikersnaam(gebruikersnaam);
        return ResponseEntity.ok(convertToDTO(gebruiker));
    }
    @GetMapping("/{gebruikersnaam}")
    public ResponseEntity<GebruikerResponseDTO> getGebruikerByName(@PathVariable String gebruikersnaam) {
        Gebruiker gebruiker = gebruikerService.getGebruikerByGebruikersnaam(gebruikersnaam);
        return ResponseEntity.ok(convertToDTO(gebruiker));
    }
    @PutMapping("/{id}")
    public ResponseEntity<GebruikerResponseDTO> updateGebruiker(@PathVariable Long id, @Valid @RequestBody Gebruiker nieuweData) {
        Gebruiker updated = gebruikerService.updateGebruiker(id, nieuweData);
        return ResponseEntity.ok(convertToDTO(updated));
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteGebruiker(@PathVariable Long id, @AuthenticationPrincipal Jwt jwt) {
        gebruikerService.deleteGebruiker(id, jwt);
        return ResponseEntity.noContent().build();
    }
    @PatchMapping("/{gebruikersnaam}/rol")
    public ResponseEntity<GebruikerResponseDTO> updateGebruikerRol(
            @PathVariable String gebruikersnaam,
            @RequestBody String nieuweRol) {
        Gebruiker.GebruikerRol rolEnum = Gebruiker.GebruikerRol.valueOf(nieuweRol.toUpperCase());
        Gebruiker updated = gebruikerService.updateRolByGebruikersnaam(gebruikersnaam, rolEnum);
        return ResponseEntity.ok(convertToDTO(updated));
    }
}
