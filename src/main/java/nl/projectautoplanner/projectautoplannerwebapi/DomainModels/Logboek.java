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
}