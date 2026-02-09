package nl.projectautoplanner.projectautoplannerwebapi.DTO.Request;

import jakarta.validation.constraints.NotNull;

public class FactuurRequestDTO {
    @NotNull(message = "Project ID is verplicht voor het genereren van een factuur")
    public Long projectId;
}
