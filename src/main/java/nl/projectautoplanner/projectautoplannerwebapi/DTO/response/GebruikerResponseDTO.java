package nl.projectautoplanner.projectautoplannerwebapi.DTO.response;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Gebruiker;

public class GebruikerResponseDTO {
    public Long id;
    public String gebruikersnaam;
    public String voornaam;
    public String achternaam;
    public String email;
    public String telefoon;
    public Gebruiker.GebruikerRol rol;
}