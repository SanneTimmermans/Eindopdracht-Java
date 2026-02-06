package nl.projectautoplanner.projectautoplannerwebapi.DTO.response;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Onderdeel;

public class OnderdeelResponseDTO {
    public String onderdeelnaam;
    public String artikelnummer;
    public double prijs;
    public Long projectId;
    public Onderdeel.Bestelstatus bestelstatus;
}
