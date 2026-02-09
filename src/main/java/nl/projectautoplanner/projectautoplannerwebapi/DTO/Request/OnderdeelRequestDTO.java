package nl.projectautoplanner.projectautoplannerwebapi.DTO.Request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Onderdeel;

public class OnderdeelRequestDTO {
    @NotBlank(message = "Onderdeelnaam is verplicht")
    public String onderdeelnaam;

    @NotBlank(message = "Artikelnummer is verplicht")
    public String artikelnummer;

    @PositiveOrZero(message = "Prijs kan niet negatief zijn")
    public double prijs;

    @NotNull(message = "Bestelstatus is verplicht")
    public Onderdeel.Bestelstatus bestelstatus;
}
