package nl.projectautoplanner.projectautoplannerwebapi.DomainModels;

import jakarta.persistence.*;

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

    @OneToMany(mappedBy = "project", cascade = CascadeType.ALL)
    private List<Onderdeel> onderdelen;

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
}
