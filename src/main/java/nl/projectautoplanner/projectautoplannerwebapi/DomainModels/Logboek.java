package nl.projectautoplanner.projectautoplannerwebapi.DomainModels;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "logboeken")
public class Logboek {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String beschrijving;
    private LocalDateTime datumTijd;
    private double uren;

    @ManyToOne
    @JoinColumn(name = "monteur_id")
    private Gebruiker monteur;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Logboek() {
        this.datumTijd = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBeschrijving() {
        return beschrijving;
    }

    public void setBeschrijving(String beschrijving) {
        this.beschrijving = beschrijving;
    }

    public LocalDateTime getDatumTijd() {
        return datumTijd;
    }

    public void setDatumTijd(LocalDateTime datumTijd) {
        this.datumTijd = datumTijd;
    }

    public double getUren() {
        return this.uren;
    }

    public void setUren(double uren) {
        this.uren = uren;
    }

    public Gebruiker getMonteur() {
        return monteur;
    }

    public void setMonteur(Gebruiker monteur) {
        this.monteur = monteur;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}