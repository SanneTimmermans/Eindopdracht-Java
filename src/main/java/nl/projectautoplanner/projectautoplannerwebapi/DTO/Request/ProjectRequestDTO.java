package nl.projectautoplanner.projectautoplannerwebapi.DTO.Request;

import java.util.List;

public class ProjectRequestDTO {
    public String projectnaam;
    public String merk;
    public String model;
    public Long eigenaarId;
    public List<Long> monteurIds;
}
