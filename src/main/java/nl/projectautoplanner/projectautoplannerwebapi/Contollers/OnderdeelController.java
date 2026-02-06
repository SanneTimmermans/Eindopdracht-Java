package nl.projectautoplanner.projectautoplannerwebapi.Contollers;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Onderdeel;
import nl.projectautoplanner.projectautoplannerwebapi.Services.OnderdeelService;
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
    public Onderdeel createOnderdeel(@RequestBody Onderdeel onderdeel) {
        return onderdeelService.saveOnderdeel(onderdeel);
    }
}
