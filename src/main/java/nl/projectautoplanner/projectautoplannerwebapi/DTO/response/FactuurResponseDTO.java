package nl.projectautoplanner.projectautoplannerwebapi.DTO.response;

import java.time.LocalDate;

public class FactuurResponseDTO {
    public Long id;
    public Long projectId;
    public LocalDate factuurDatum;
    public double totaalBedrag;
    public boolean isBetaald;
}
