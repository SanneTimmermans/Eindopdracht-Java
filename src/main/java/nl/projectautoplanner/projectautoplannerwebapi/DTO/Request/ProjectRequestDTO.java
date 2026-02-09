package nl.projectautoplanner.projectautoplannerwebapi.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public class ProjectRequestDTO {
    @NotBlank(message = "Projectnaam is verplicht")
    public String projectnaam;

    @NotBlank(message = "Automerk is verplicht")
    public String merk;

    @NotBlank(message = "Automodel is verplicht")
    public String model;

    @NotNull(message = "Een project moet een eigenaar hebben")
    public Long eigenaarId;

    @NotEmpty(message = "Wijs minimaal één monteur toe aan het project")
    public List<Long> monteurIds;
}
