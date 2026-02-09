package nl.projectautoplanner.projectautoplannerwebapi.DTO.response;

import java.util.List;

public class ProjectResponseDTO {
    public Long id;
    public String projectnaam;
    public String merk;
    public String model;
    public String eigenaarNaam;
    public List<String> monteurNamen;
    public List<OnderdeelResponseDTO> onderdelen;
}
