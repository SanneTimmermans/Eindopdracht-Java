package nl.projectautoplanner.projectautoplannerwebapi.Services;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Onderdeel;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.OnderdeelRepository;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class OnderdeelService {

    private final OnderdeelRepository onderdeelRepository;

    public OnderdeelService(OnderdeelRepository onderdeelRepository) {
        this.onderdeelRepository = onderdeelRepository;
    }

    public List<Onderdeel> getAllOnderdelen() {
        return onderdeelRepository.findAll();
    }

    public Onderdeel saveOnderdeel(Onderdeel onderdeel) {
        return onderdeelRepository.save(onderdeel);
    }
}
