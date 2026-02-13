package nl.projectautoplanner.projectautoplannerwebapi.Services;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Gebruiker;
import nl.projectautoplanner.projectautoplannerwebapi.Exceptions.BadRequestException;
import nl.projectautoplanner.projectautoplannerwebapi.Exceptions.RecordNotFoundException;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.GebruikerRepository;
import org.springframework.security.oauth2.jwt.Jwt;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class GebruikerService {

    private final GebruikerRepository gebruikerRepository;

    public GebruikerService(GebruikerRepository gebruikerRepository) {
        this.gebruikerRepository = gebruikerRepository;
    }

    public Gebruiker saveGebruiker(Gebruiker gebruiker) {
        return gebruikerRepository.save(gebruiker);
    }
    @Transactional
    public void deleteGebruiker(Long id, Jwt jwt) {
        String ingelogdeNaam = jwt.getClaimAsString("preferred_username");
        Gebruiker teVerwijderen = gebruikerRepository.findById(id).orElseThrow();
        boolean isAdmin = jwt.getClaimAsMap("resource_access")
                .get("ProjectautoPlanner").toString().contains("ADMIN");
        if (isAdmin || teVerwijderen.getGebruikersnaam().equalsIgnoreCase(ingelogdeNaam)) {
            gebruikerRepository.deleteById(id);
        } else {
            throw new BadRequestException("Je mag alleen je eigen account verwijderen!");
        }
    }
    @Transactional
    public Gebruiker updateGebruiker(Long id, Gebruiker nieuweData) {
        return gebruikerRepository.findById(id).map(bestaandeGebruiker -> {
            bestaandeGebruiker.setGebruikersnaam(nieuweData.getGebruikersnaam());
            bestaandeGebruiker.setEmail(nieuweData.getEmail());
            bestaandeGebruiker.setAchternaam(nieuweData.getAchternaam());
            bestaandeGebruiker.setTelefoon(nieuweData.getTelefoon());
            bestaandeGebruiker.setAdres(nieuweData.getAdres());
            return gebruikerRepository.save(bestaandeGebruiker);
        }).orElseThrow(() -> new RecordNotFoundException("Gebruiker niet gevonden"));
    }
    @Transactional
    public Gebruiker getGebruikerByGebruikersnaam(String gebruikersnaam) {
        return gebruikerRepository.findByGebruikersnaamIgnoreCase(gebruikersnaam)
                .orElseThrow(() -> new RecordNotFoundException("Gebruiker niet gevonden."));
    }
    @Transactional
    public Gebruiker updateRolByGebruikersnaam(String gebruikersnaam, Gebruiker.GebruikerRol rolEnum) {
        Gebruiker gebruiker = gebruikerRepository.findByGebruikersnaamIgnoreCase(gebruikersnaam)
                .orElseThrow(() -> new RecordNotFoundException("Gebruiker met naam " + gebruikersnaam + " niet gevonden"));
        gebruiker.setRol(rolEnum);
        return gebruikerRepository.save(gebruiker);
    }
}
