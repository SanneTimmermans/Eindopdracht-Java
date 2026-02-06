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

    public List<Gebruiker> getAllGebruikers() {
        return gebruikerRepository.findAll();
    }

    public Gebruiker getGebruikerById(Long id) {
        return gebruikerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden"));
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

            if (nieuweData.getWachtwoord() != null && !nieuweData.getWachtwoord().isEmpty()) {
                bestaandeGebruiker.setWachtwoord(nieuweData.getWachtwoord());
            }

            return gebruikerRepository.save(bestaandeGebruiker);
        }).orElseThrow(() -> new RuntimeException("Gebruiker niet gevonden"));
    }
}
