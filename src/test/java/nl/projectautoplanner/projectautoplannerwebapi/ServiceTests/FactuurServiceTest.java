package nl.projectautoplanner.projectautoplannerwebapi.ServiceTests;

import nl.projectautoplanner.projectautoplannerwebapi.DTO.Request.FactuurRequestDTO;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Factuur;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Logboek;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Onderdeel;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Project;
import nl.projectautoplanner.projectautoplannerwebapi.Exceptions.RecordNotFoundException;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.FactuurRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.LogboekRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.OnderdeelRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.ProjectRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Services.FactuurService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FactuurServiceTest {

    @Mock
    private FactuurRepository factuurRepository;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private LogboekRepository logboekRepository;
    @Mock
    private OnderdeelRepository onderdeelRepository;

    @InjectMocks
    private FactuurService factuurService;

    @Test
    @DisplayName("GenereerFactuur - Succesvolle berekening van uren en onderdelen")
    void testGenereerFactuurSuccess() {
        FactuurRequestDTO dto = new FactuurRequestDTO();
        dto.projectId = 1L;

        Project mockProject = new Project();
        mockProject.setId(1L);

        Logboek logboekRegel = new Logboek();
        logboekRegel.setUren(2.0);

        Onderdeel onderdeel = new Onderdeel();
        onderdeel.setPrijs(50.0);

        when(projectRepository.findById(1L)).thenReturn(Optional.of(mockProject));
        when(logboekRepository.findByProject_Id(1L)).thenReturn(List.of(logboekRegel));
        when(onderdeelRepository.findByProject_Id(1L)).thenReturn(List.of(onderdeel));
        when(factuurRepository.save(any(Factuur.class))).thenAnswer(i -> i.getArguments()[0]);

        Factuur result = factuurService.genereerFactuur(dto);

        assertNotNull(result);
        assertEquals(200.0, result.getTotaalBedrag(), "De berekening van de factuur klopt niet!");
        assertEquals(mockProject, result.getProject());
        verify(factuurRepository).save(any(Factuur.class));
    }

    @Test
    @DisplayName("GenereerFactuur - Project niet gevonden")
    void testGenereerFactuurProjectNotFound() {
        FactuurRequestDTO dto = new FactuurRequestDTO();
        dto.projectId = 99L;
        when(projectRepository.findById(99L)).thenReturn(Optional.empty());

        assertThrows(RecordNotFoundException.class, () -> factuurService.genereerFactuur(dto));
    }

    @Test
    @DisplayName("GetFactuurByProject_Id - Succesvol gevonden")
    void testGetFactuurByProjectIdSuccess() {
        when(factuurRepository.findByProjectId(1L)).thenReturn(Optional.of(new Factuur()));
        Factuur result = factuurService.getFactuurByProject_Id(1L);
        assertNotNull(result);
    }

    @Test
    @DisplayName("GetFactuurByProject_Id - Factuur niet gevonden")
    void testGetFactuurByProjectIdNotFound() {
        when(factuurRepository.findByProjectId(1L)).thenReturn(Optional.empty());
        assertThrows(RecordNotFoundException.class, () -> factuurService.getFactuurByProject_Id(1L));
    }

    @Test
    @DisplayName("GetAllFacturen - Succesvol lijst ophalen")
    void testGetAllFacturenSuccess() {
        when(factuurRepository.findAll()).thenReturn(List.of(new Factuur(), new Factuur()));
        List<Factuur> result = factuurService.getAllFacturen();
        assertEquals(2, result.size());
    }

    @Test
    @DisplayName("GetAllFacturen - Foutmelding bij lege lijst")
    void testGetAllFacturenEmpty() {
        when(factuurRepository.findAll()).thenReturn(List.of());
        RecordNotFoundException ex = assertThrows(RecordNotFoundException.class, () -> factuurService.getAllFacturen());
        assertEquals("Er zijn momenteel geen facturen aanwezig in het systeem.", ex.getMessage());
    }
}