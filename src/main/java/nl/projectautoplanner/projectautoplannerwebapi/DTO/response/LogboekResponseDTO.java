package nl.projectautoplanner.projectautoplannerwebapi.DTO.response;

import java.time.LocalDateTime;

public class LogboekResponseDTO {
    public Long id;
    public String beschrijving;
    public double uren;
    public LocalDateTime datumTijd;
    public String monteurNaam;
    public String projectNaam;
}
