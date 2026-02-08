package nl.projectautoplanner.projectautoplannerwebapi.DomainModels;

import jakarta.persistence.*;

@Entity
@Table(name = "gebruikers")
public class Gebruiker {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String gebruikersnaam;
    private String wachtwoord;
    private String voornaam;
    private String achternaam;
    private String email;
    private String telefoon;
    private String adres;
    @Enumerated(EnumType.STRING)
    private GebruikerRol rol;
    public enum GebruikerRol {EIGENAAR, MONTEUR};

    public Gebruiker() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGebruikersnaam() {
        return gebruikersnaam;
    }

    public void setGebruikersnaam(String gebruikersnaam) {
        this.gebruikersnaam = gebruikersnaam;
    }

    public String getWachtwoord() {
        return wachtwoord;
    }

    public void setWachtwoord(String wachtwoord) {
        this.wachtwoord = wachtwoord;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelefoon() {
        return telefoon;
    }

    public void setTelefoon(String telefoon) {
        this.telefoon = telefoon;
    }

    public GebruikerRol getRol() {
        return rol;
    }

    public void setRol(GebruikerRol gebruikerRol) {
        this.rol = gebruikerRol;
    }

    public String getVoornaam() {
        return voornaam;
    }

    public void setVoornaam(String voornaam) {
        this.voornaam = voornaam;
    }

    public String getAchternaam() {
        return achternaam;
    }

    public void setAchternaam(String achternaam) {
        this.achternaam = achternaam;
    }

    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }
}