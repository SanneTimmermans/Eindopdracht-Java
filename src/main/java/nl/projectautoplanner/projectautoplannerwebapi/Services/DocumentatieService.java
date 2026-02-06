package nl.projectautoplanner.projectautoplannerwebapi.Services;

import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Documentatie;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Project;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.DocumentatieRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.ProjectRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import javax.swing.text.*;

@Service
public class DocumentatieService {
    private final DocumentatieRepository documentatieRepository;
    private final ProjectRepository projectRepository;

    public DocumentatieService(DocumentatieRepository documentatieRepository, ProjectRepository projectRepository) {
        this.documentatieRepository = documentatieRepository;
        this.projectRepository = projectRepository;
    }
    public List<Documentatie> getDocumentatieByProject(Long projectId) {
        return documentatieRepository.findByProjectId(projectId);
    }

    public Documentatie saveDocumentatie(String naam, String type, String url, String tekst, Long projectId) {
        Project project = projectRepository.findById(projectId)
                .orElseThrow(() -> new RuntimeException("Project niet gevonden."));
        Documentatie doc = new Documentatie();
        doc.setBestandsnaam(naam);
        doc.setBestandtype(type);
        doc.setUrl(url);
        doc.setTekstInhoud(tekst);
        doc.setProject(project);

        return documentatieRepository.save(doc);
    }
    public void deleteDocumentatie(Long id) {
        if (!documentatieRepository.existsById(id)) {
            throw new RuntimeException("Documentatie met id " + id + " niet gevonden.");
        }
        documentatieRepository.deleteById(id);
    }
}
