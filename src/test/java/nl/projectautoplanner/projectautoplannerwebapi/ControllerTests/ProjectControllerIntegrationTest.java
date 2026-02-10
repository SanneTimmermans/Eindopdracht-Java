package nl.projectautoplanner.projectautoplannerwebapi.ControllerTests;

import nl.projectautoplanner.projectautoplannerwebapi.Controllers.ProjectController;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Gebruiker;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Onderdeel;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Project;
import nl.projectautoplanner.projectautoplannerwebapi.Services.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.jwt;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ProjectController.class)
public class ProjectControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ProjectService projectService;

    @Test
    @DisplayName("GET /projecten/{id} - Moet Project met DTO details retourneren")
    void testGetProjectDetailSuccess() throws Exception {
        Long id = (Long) 1L;
        Project mockProject = new Project();
        mockProject.setId(id);
        mockProject.setProjectnaam("Restauratie Kever");
        mockProject.setMerk("VW");
        mockProject.setModel("Kever");

        Gebruiker eigenaar = new Gebruiker();
        eigenaar.setGebruikersnaam("Jan_Klant");
        mockProject.setEigenaar(eigenaar);

        Onderdeel onderdeel = new Onderdeel();
        onderdeel.setOnderdeelnaam("Uitlaat");
        onderdeel.setPrijs(150.0);
        mockProject.setOnderdelen(List.of(onderdeel));

        Mockito.when(projectService.getProjectGevalideerd(Mockito.eq(id), Mockito.any())).thenReturn(mockProject);

        mockMvc.perform(get("/projecten/" + id)
                        .with(jwt()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.projectnaam").value("Restauratie Kever"))
                .andExpect(jsonPath("$.eigenaarNaam").value("Jan_Klant"))
                .andExpect(jsonPath("$.onderdelen[0].onderdeelnaam").value("Uitlaat"))
                .andExpect(jsonPath("$.merk").value("VW"));
    }

    @Test
    @DisplayName("GET /projecten - Moet lijst van projecten retourneren")
    @WithMockUser(roles = "ADMIN")
    void testGetAllProjects() throws Exception {
        Project p1 = new Project();
        p1.setProjectnaam("Project 1");
        Mockito.when(projectService.getAllProjects()).thenReturn(List.of(p1));
        mockMvc.perform(get("/projecten"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].projectnaam").value("Project 1"));
    }
}