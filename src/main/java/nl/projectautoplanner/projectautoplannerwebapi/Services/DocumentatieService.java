package nl.projectautoplanner.projectautoplannerwebapi.Services;

import nl.projectautoplanner.projectautoplannerwebapi.Exceptions.BadRequestException;
import nl.projectautoplanner.projectautoplannerwebapi.Exceptions.RecordNotFoundException;
import org.springframework.core.io.Resource;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Documentatie;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Project;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.DocumentatieRepository;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.ProjectRepository;
import nl.projectautoplanner.projectautoplannerwebapi.DomainModels.Onderdeel;
import nl.projectautoplanner.projectautoplannerwebapi.Repositories.OnderdeelRepository;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

@Service
public class DocumentatieService {
    private final Path storageLocation = Paths.get("uploads").toAbsolutePath().normalize();
    private final DocumentatieRepository documentatieRepository;
    private final ProjectRepository projectRepository;
    private final OnderdeelRepository onderdeelRepository;

    public DocumentatieService(DocumentatieRepository documentatieRepository, ProjectRepository projectRepository, OnderdeelRepository onderdeelRepository) {
        this.documentatieRepository = documentatieRepository;
        this.projectRepository = projectRepository;
        this.onderdeelRepository = onderdeelRepository;
        try { Files.createDirectories(storageLocation); } catch (Exception e) { throw new BadRequestException("Map aanmaken mislukt"); }
    }
    @Transactional
    public Documentatie storeDocument(MultipartFile file, String tekst, Long projectId, Long onderdeelId) {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());
        Documentatie doc = new Documentatie();
        doc.setBestandsnaam(fileName);
        doc.setBestandtype(file.getContentType());
        doc.setTekstInhoud(tekst);
        doc.setUrl("/documentatie/download/" + fileName);
        try {
            Path targetLocation = storageLocation.resolve(fileName);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);
            if (projectId != null) {
                Project project = projectRepository.findById(projectId)
                        .orElseThrow(() -> new RecordNotFoundException("Project niet gevonden"));
                doc.setProject(project);
                project.getDocumentatieLijst().add(doc);
            }
            if (onderdeelId != null) {
                Onderdeel onderdeel = onderdeelRepository.findById(onderdeelId)
                        .orElseThrow(() -> new RecordNotFoundException("Onderdeel niet gevonden"));
                doc.setOnderdeel(onderdeel);
            }

            return documentatieRepository.save(doc);
        } catch (IOException ex) {
            throw new BadRequestException("Kon bestand niet opslaan", ex);
        }
    }
    @Transactional
    public Resource loadFile(String fileName) {
        try {
            Path filePath = storageLocation.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if(resource.exists()) return resource;
            else throw new RecordNotFoundException("Bestand niet gevonden");
        } catch (Exception e) { throw new RecordNotFoundException("Bestand niet gevonden", e); }
    }
    @Transactional
    public List<Documentatie> getDocumentatieByProject(Long projectId) {
        return documentatieRepository.findByProject_Id(projectId);
    }
    @Transactional
    public void deleteDocumentatie(Long id) {
        if (!documentatieRepository.existsById(id)) {
            throw new RecordNotFoundException("Documentatie met id " + id + " niet gevonden.");
        }
        documentatieRepository.deleteById(id);
    }
}
