package nl.projectautoplanner.projectautoplannerwebapi.DomainModels;

import jakarta.persistence.*;

@Entity
@Table(name = "onderdelen")
public class Onderdeel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String onderdeelnaam;
    private String artikelnummer;
    private double prijs;
    @Enumerated(EnumType.STRING)
    private Bestelstatus bestelstatus;
    public enum Bestelstatus{NOG_BESTELLEN, BESTELD, ONTVANGEN, GEANNULEERD};

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Onderdeel() {
    }

    public Bestelstatus getBestelstatus() {
        return bestelstatus;
    }

    public void setBestelstatus(Bestelstatus bestelstatus) {
        this.bestelstatus = bestelstatus;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public Long getId() {
        return id;
    }

    public Project getProject() {
        return project;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOnderdeelnaam() {
        return onderdeelnaam;
    }

    public void setOnderdeelnaam(String onderdeelnaam) {
        this.onderdeelnaam = onderdeelnaam;
    }

    public String getArtikelnummer() {
        return artikelnummer;
    }

    public void setArtikelnummer(String artikelnummer) {
        this.artikelnummer = artikelnummer;
    }

    public double getPrijs() {
        return prijs;
    }

    public void setPrijs(double prijs) {
        this.prijs = prijs;
    }
}


