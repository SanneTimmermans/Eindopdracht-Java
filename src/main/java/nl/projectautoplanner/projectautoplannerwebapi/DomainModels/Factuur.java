package nl.projectautoplanner.projectautoplannerwebapi.DomainModels;

import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "facturen")
public class Factuur {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate factuurDatum;
    private double totaalBedrag;
    private boolean isBetaald;
    public Factuur() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalDate getFactuurDatum() {
        return factuurDatum;
    }

    public void setFactuurDatum(LocalDate factuurDatum) {
        this.factuurDatum = factuurDatum;
    }

    public double getTotaalBedrag() {
        return totaalBedrag;
    }

    public void setTotaalBedrag(double totaalBedrag) {
        this.totaalBedrag = totaalBedrag;
    }

    public boolean isBetaald() {
        return isBetaald;
    }

    public void setBetaald(boolean betaald) {
        isBetaald = betaald;
    }
}