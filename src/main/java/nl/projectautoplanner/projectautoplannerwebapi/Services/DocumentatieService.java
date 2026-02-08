package nl.projectautoplanner.projectautoplannerwebapi.Services;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Documentatie;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Project;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.DocumentatieRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.ProjectRepository;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Onderdeel;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.OnderdeelRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.swing.text.*;

@Service
public class DocumentatieService {
    private final DocumentatieRepository documentatieRepository;
    private final ProjectRepository projectRepository;
    private final OnderdeelRepository onderdeelRepository;

    public DocumentatieService(DocumentatieRepository documentatieRepository, ProjectRepository projectRepository, OnderdeelRepository onderdeelRepository) {
        this.documentatieRepository = documentatieRepository;
        this.projectRepository = projectRepository;
        this.onderdeelRepository = onderdeelRepository;
    }
    public List<Documentatie> getDocumentatieByProject(Long projectId) {
        return documentatieRepository.findByProjectId(projectId);
    }

    public Documentatie saveDocumentatie(String naam, String type, String url, String tekst, Long projectId, Long onderdeelId) {
        Documentatie doc = new Documentatie();
        doc.setBestandsnaam(naam);
        doc.setBestandtype(type);
        doc.setUrl(url);
        doc.setTekstInhoud(tekst);
        if (projectId != null) {
            Project p = projectRepository.findById(projectId)
                    .orElseThrow(() -> new RuntimeException("Project niet gevonden"));
            doc.setProject(p);
        }
        if (onderdeelId != null) {
            Onderdeel o = onderdeelRepository.findById(onderdeelId)
                    .orElseThrow(() -> new RuntimeException("Onderdeel niet gevonden"));
            doc.setOnderdeel(o);
        }
        if (doc.getProject() == null && doc.getOnderdeel() == null) {
            throw new RuntimeException("Documentatie moet aan een project óf een onderdeel gekoppeld zijn.");
        }
        return documentatieRepository.save(doc);
    }
    public void deleteDocumentatie(Long id) {
        if (!documentatieRepository.existsById(id)) {
            throw new RuntimeException("Documentatie met id " + id + " niet gevonden.");
        }
        documentatieRepository.deleteById(id);
    }
}
