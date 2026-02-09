package nl.projectautoplanner.projectautoplannerwebapi.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public class LogboekRequestDTO {
    @NotBlank(message = "Beschrijving van de werkzaamheden is verplicht")
    public String beschrijving;

    @Positive(message = "Uren moeten meer dan 0 zijn")
    public double uren;

    @NotNull(message = "Monteur ID is verplicht")
    public Long monteurId;

    @NotNull(message = "Project ID is verplicht")
    public Long projectId;
}
