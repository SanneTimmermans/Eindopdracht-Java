package nl.projectautoplanner.projectautoplannerwebapi.ServiceTests;

import nl.projectautoplanner.projectautoplannerwebapi.DTO.Request.ProjectRequestDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Gebruiker;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Project;
import nl.projectautoplanner.projectautoplannerwebapi.Exceptions.BadRequestException;
import nl.projectautoplanner.projectautoplannerwebapi.Exceptions.RecordNotFoundException;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.GebruikerRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.ProjectRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Services.ProjectService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.oauth2.jwt.Jwt;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProjectServiceTest {

    @Mock
    private ProjectRepository projectRepository;

    @Mock
    private GebruikerRepository gebruikerRepository;

    @InjectMocks
    private ProjectService projectService;

    @Test
    @DisplayName("CreateProject - Succes")
    void testCreateProjectSuccess() {
        ProjectRequestDTO dto = new ProjectRequestDTO();
        dto.projectnaam = "Restauratie";
        dto.eigenaarId = 1L;
        dto.monteurIds = List.of(2L);
        Gebruiker eigenaar = new Gebruiker();
        eigenaar.setId(1L);

        when(gebruikerRepository.findById(1L)).thenReturn(Optional.of(eigenaar));
        when(gebruikerRepository.findAllById(dto.monteurIds)).thenReturn(List.of(new Gebruiker()));
        when(projectRepository.save(any(Project.class))).thenAnswer(i -> i.getArguments()[0]);

        Project result = projectService.createProject(dto);
        assertNotNull(result);
        assertEquals("Restauratie", result.getProjectnaam());
        verify(projectRepository).save(any(Project.class));
    }

    @Test
    @DisplayName("CreateProject - Eigenaar niet gevonden")
    void testCreateProjectNoEigenaar() {
        ProjectRequestDTO dto = new ProjectRequestDTO();
        dto.eigenaarId = 99L;
        when(gebruikerRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> projectService.createProject(dto));
    }

    @Test
    @DisplayName("CreateProject - Geen monteurs geselecteerd")
    void testCreateProjectNoMonteurs() {
        ProjectRequestDTO dto = new ProjectRequestDTO();
        dto.eigenaarId = 1L;
        dto.monteurIds = List.of();
        when(gebruikerRepository.findById(1L)).thenReturn(Optional.of(new Gebruiker()));

        assertThrows(BadRequestException.class, () -> projectService.createProject(dto));
    }

    @Test
    @DisplayName("CreateProject - MonteurIds is null")
    void testCreateProjectMonteursNull() {
        ProjectRequestDTO dto = new ProjectRequestDTO();
        dto.eigenaarId = 1L;
        dto.monteurIds = null;
        when(gebruikerRepository.findById(1L)).thenReturn(Optional.of(new Gebruiker()));

        assertThrows(BadRequestException.class, () -> projectService.createProject(dto));
    }

    @Test
    @DisplayName("GetProjectGevalideerd - Toegang als Admin")
    void testGetProjectAdminAccess() {
        Long id = 1L;
        Project p = new Project();
        Gebruiker eigenaar = new Gebruiker();
        eigenaar.setGebruikersnaam("testnaam");
        p.setEigenaar(eigenaar);
        p.setMonteurs(List.of());

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("preferred_username")).thenReturn("adminUser");
        when(jwt.getClaimAsMap("resource_access")).thenReturn(Map.of("ProjectautoPlanner", Map.of("roles", List.of("ADMIN"))));
        when(projectRepository.findById(id)).thenReturn(Optional.of(p));

        Project result = projectService.getProjectGevalideerd(id, jwt);
        assertNotNull(result);
    }

    @Test
    @DisplayName("GetProjectGevalideerd - Toegang geweigerd")
    void testGetProjectAccessDenied() {
        Long id = 1L;
        Project p = new Project();
        Gebruiker eigenaar = new Gebruiker();
        eigenaar.setGebruikersnaam("testnaam");
        p.setEigenaar(eigenaar);
        p.setMonteurs(List.of());

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("preferred_username")).thenReturn("OnbekendeGebruiker");
        when(jwt.getClaimAsMap("resource_access")).thenReturn(Map.of("ProjectautoPlanner", Map.of("roles", List.of("EIGENAAR"))));
        when(projectRepository.findById(id)).thenReturn(Optional.of(p));

        assertThrows(BadRequestException.class, () -> projectService.getProjectGevalideerd(id, jwt));
    }

    @Test
    @DisplayName("GetProjectGevalideerd - Project niet gevonden")
    void testGetProjectGevalideerdNotFound() {
        Long id = 999L;
        when(projectRepository.findById(id)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () ->
                projectService.getProjectGevalideerd(id, mock(Jwt.class)));
    }

    @Test
    @DisplayName("GetProjectGevalideerd - Toegang als Eigenaar")
    void testGetProjectEigenaarAccess() {
        Long id = 1L;
        Project p = new Project();
        Gebruiker eigenaar = new Gebruiker();
        eigenaar.setGebruikersnaam("Testnaam");
        p.setEigenaar(eigenaar);
        p.setMonteurs(List.of());

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("preferred_username")).thenReturn("Testnaam");
        when(jwt.getClaimAsMap("resource_access")).thenReturn(Map.of("ProjectautoPlanner", Map.of("roles", List.of("USER"))));

        when(projectRepository.findById(id)).thenReturn(Optional.of(p));
        Project result = projectService.getProjectGevalideerd(id, jwt);
        assertNotNull(result);
        assertEquals("Testnaam", result.getEigenaar().getGebruikersnaam());
    }

    @Test
    @DisplayName("GetProjectGevalideerd - Toegang als Monteur")
    void testGetProjectMonteurAccess() {
        Long id = 1L;
        Project p = new Project();
        Gebruiker monteur = new Gebruiker();
        monteur.setGebruikersnaam("testnaam");
        p.setMonteurs(List.of(monteur));
        Gebruiker eigenaar = new Gebruiker();
        eigenaar.setGebruikersnaam("testnaam");
        p.setEigenaar(eigenaar);

        Jwt jwt = mock(Jwt.class);
        when(jwt.getClaimAsString("preferred_username")).thenReturn("testnaam");
        when(jwt.getClaimAsMap("resource_access")).thenReturn(Map.of("ProjectautoPlanner", Map.of("roles", List.of("MONTEUR"))));
        when(projectRepository.findById(id)).thenReturn(Optional.of(p));

        Project result = projectService.getProjectGevalideerd(id, jwt);
        assertNotNull(result);
        assertTrue(p.getMonteurs().get(0).getGebruikersnaam().equalsIgnoreCase("testnaam"));
    }

    @Test
    @DisplayName("GetAllProjects - Moet alle projecten retourneren")
    void testGetAllProjects() {
        when(projectRepository.findAll()).thenReturn(List.of(new Project(), new Project()));
        List<Project> result = projectService.getAllProjects();
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("GetAllProjects - Lege lijst")
    void testGetAllProjectsEmpty() {
        when(projectRepository.findAll()).thenReturn(List.of());
        assertThrows(RecordNotFoundException.class, () -> projectService.getAllProjects());
    }
}

