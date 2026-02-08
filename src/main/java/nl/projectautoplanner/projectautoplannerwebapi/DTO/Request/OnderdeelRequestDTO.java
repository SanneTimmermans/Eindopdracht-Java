package nl.projectautoplanner.projectautoplannerwebapi.DTO.Request;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Onderdeel;

public class OnderdeelRequestDTO {
    public String onderdeelnaam;
    public String artikelnummer;
    public double prijs;
    public Onderdeel.Bestelstatus bestelstatus;
}
