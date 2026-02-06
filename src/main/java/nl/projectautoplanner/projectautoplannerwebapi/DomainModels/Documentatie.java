package nl.projectautoplanner.projectautoplannerwebapi.DomainModels;

import jakarta.persistence.*;

@Entity
@Table(name = "documentatie")
public class Documentatie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bestandsnaam;
    private String bestandtype;
    private String url;
    @Column(columnDefinition = "TEXT")
    private String tekstInhoud;

    @ManyToOne
    @JoinColumn(name = "project_id")
    private Project project;

    public Documentatie() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getBestandsnaam() {
        return bestandsnaam;
    }

    public void setBestandsnaam(String bestandsnaam) {
        this.bestandsnaam = bestandsnaam;
    }

    public String getBestandtype() {
        return bestandtype;
    }

    public void setBestandtype(String bestandtype) {
        this.bestandtype = bestandtype;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getTekstInhoud() {
        return tekstInhoud;
    }

    public void setTekstInhoud(String tekstInhoud) {
        this.tekstInhoud = tekstInhoud;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}