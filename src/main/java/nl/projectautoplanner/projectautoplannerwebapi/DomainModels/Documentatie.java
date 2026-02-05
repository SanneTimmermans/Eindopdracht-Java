package nl.projectautoplanner.projectautoplannerwebapi.DomainModels;

import jakarta.persistence.*;

@Entity
@Table(name = "documentatie")
public class Documentatie {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String bestandsnaam;
    private String url;
    @Column(columnDefinition = "TEXT")
    private String tekstInhoud;
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
}