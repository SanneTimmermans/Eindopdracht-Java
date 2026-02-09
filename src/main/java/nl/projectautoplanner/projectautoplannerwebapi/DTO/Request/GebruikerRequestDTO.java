package nl.projectautoplanner.projectautoplannerwebapi.DTO.Request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class GebruikerRequestDTO {
    @NotBlank(message = "Gebruikersnaam mag niet leeg zijn")
    @Size(min = 3, max = 20, message = "Gebruikersnaam moet tussen 3 en 20 tekens zijn")
    public String gebruikersnaam;

    @NotBlank(message = "Voornaam is verplicht")
    public String voornaam;

    @NotBlank(message = "Achternaam is verplicht")
    public String achternaam;

    @Email(message = "Voer een geldig e-mailadres in")
    @NotBlank(message = "E-mail is verplicht")
    public String email;

    @Pattern(regexp = "^[0-9]{10}$", message = "Telefoonnummer moet exact 10 cijfers bevatten")
    public String telefoon;

    @NotBlank(message = "Adres is verplicht voor facturatie")
    public String adres;
}
