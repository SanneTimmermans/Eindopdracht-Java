package nl.projectautoplanner.projectautoplannerwebapi.DTO.Request;

import jakarta.validation.constraints.NotNull;

public class DocumentatieRequestDTO {
    @NotNull(message = "Project ID is verplicht")
    public Long projectId;
}
