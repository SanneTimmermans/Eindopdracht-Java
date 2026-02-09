package nl.projectautoplanner.projectautoplannerwebapi.Services;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Gebruiker;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.GebruikerRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class GebruikerService {

    private final GebruikerRepository gebruikerRepository;

    public GebruikerService(GebruikerRepository gebruikerRepository) {
        this.gebruikerRepository = gebruikerRepository;
    }

    public Gebruiker saveGebruiker(Gebruiker gebruiker) {
        return gebruikerRepository.save(gebruiker);
    }

    public void deleteGebruiker(Long id) {
        gebruikerRepository.deleteById(id);
    }

    public Gebruiker updateGebruiker(Long id, Gebruiker nieuweData) {
        return gebruikerRepository.findById(id).map(bestaandeGebruiker -> {
            bestaandeGebruiker.setGebruikersnaam(nieuweData.getGebruikersnaam());
            bestaandeGebruiker.setEmail(nieuweData.getEmail());
            bestaandeGebruiker.setAchternaam(nieuweData.getAchternaam());
            bestaandeGebruiker.setTelefoon(nieuweData.getTelefoon());
            bestaandeGebruiker.setAdres(nieuweData.getAdres());
            return gebruikerRepository.save(bestaandeGebruiker);
        }).orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden"));
    }
    public Gebruiker getGebruikerByGebruikersnaam(String gebruikersnaam) {
        return gebruikerRepository.findByGebruikersnaamIgnoreCase(gebruikersnaam)
                .orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden."));
    }

    public Gebruiker updateRolByGebruikersnaam(String gebruikersnaam, Gebruiker.GebruikerRol rolEnum) {
        Gebruiker gebruiker = gebruikerRepository.findByGebruikersnaamIgnoreCase(gebruikersnaam)
                .orElseThrow(() -> new RuntimeException("Gebruiker met naam " + gebruikersnaam + " niet gevonden"));
        gebruiker.setRol(rolEnum);
        return gebruikerRepository.save(gebruiker);
    }
}
