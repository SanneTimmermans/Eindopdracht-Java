package nl.projectautoplanner.projectautoplannerwebapi.DomainModels;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "projecten")
public class Project {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String projectnaam;
    private String merk;
    private String model;

    @ManyToOne
    @JoinColumn(name = "eigenaar_id", nullable = false)
    private Gebruiker eigenaar;

    @ManyToMany
    @JoinTable(
            name = "project_monteurs",
            joinColumns = @JoinColumn(name = "project_id"),
            inverseJoinColumns = @JoinColumn(name = "monteur_id")
    )
    private List<Gebruiker> monteurs = new ArrayList<>();

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Onderdeel> onderdelen;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Documentatie> documentatieLijst;

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Logboek> logboeken;

    @OneToOne(mappedBy = "project")
    private Factuur factuur;

    public Project() {
    }

    public List<Onderdeel> getOnderdelen() {
        return onderdelen;
    }

    public void setOnderdelen(List<Onderdeel> onderdelen) {
        this.onderdelen = onderdelen;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getProjectnaam() {
        return projectnaam;
    }

    public void setProjectnaam(String projectnaam) {
        this.projectnaam = projectnaam;
    }

    public String getMerk() {
        return merk;
    }

    public void setMerk(String merk) {
        this.merk = merk;
    }

    public String getModel() {
        return model;
    }

    public void setModel(String model) {
        this.model = model;
    }

    public List<Documentatie> getDocumentatieLijst() {
        return documentatieLijst;
    }

    public void setDocumentatieLijst(List<Documentatie> documentatieLijst) {
        this.documentatieLijst = documentatieLijst;
    }

    public List<Logboek> getLogboeken() {
        return logboeken;
    }

    public void setLogboeken(List<Logboek> logboeken) {
        this.logboeken = logboeken;
    }

    public Factuur getFactuur() {
        return factuur;
    }

    public void setFactuur(Factuur factuur) {
        this.factuur = factuur;
    }

    public Gebruiker getEigenaar() {
        return eigenaar;
    }

    public void setEigenaar(Gebruiker eigenaar) {
        this.eigenaar = eigenaar;
    }

    public List<Gebruiker> getMonteurs() {
        return monteurs;
    }

    public void setMonteurs(List<Gebruiker> monteurs) {
        this.monteurs = monteurs;
    }
}
