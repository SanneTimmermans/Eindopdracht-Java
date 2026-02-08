package nl.projectautoplanner.projectautoplannerwebapi.Controllers;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Factuur;
import nl.projectautoplanner.projectautoplannerwebapi.Services.FactuurService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/facturen")
public class FactuurController {

    private final FactuurService factuurService;

    public FactuurController(FactuurService factuurService) {
        this.factuurService = factuurService;
    }

    @PostMapping("/project/{projectId}")
    public ResponseEntity<Factuur> createFactuur(@PathVariable Long projectId) {
        Factuur factuur = factuurService.genereerFactuur(projectId);
        return ResponseEntity.ok(factuur);
    }
}
