package application.servises;

import application.entities.DocumentEntity;
import application.exceptions.BusinessLogicException;
import application.repositories.DocumentRepository;
import application.stateMachine.Event;
import application.stateMachine.SimpleStateMachine;
import application.validation.DocumentValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import javax.persistence.EntityNotFoundException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DocumentService {

    private final DocumentRepository documentRepo;

    private final DocumentValidation documentValidation;

    private final SimpleStateMachine stateMachine;

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(@Lazy EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    public DocumentEntity getDocumentById(int id) {
        Optional<DocumentEntity> document = documentRepo.findById(id);
        if (document.isEmpty()) {
            throw new EntityNotFoundException("Document does not exist");
        }
        return document.get();
    }

    public Page<DocumentEntity> getDocumentsPage(Pageable pageable) {
        return documentRepo.findAll(pageable);
    }

    public List<DocumentEntity> getAllDocuments() {
        return documentRepo.findAll();
    }

    public void createDocument(DocumentEntity document) {
        if (!documentValidation.isDocumentValid(document)) {
            throw new BusinessLogicException("Document is incorrect");
        }
        document.setAuthor(employeeService.getEmployeeById(document.getAuthor().getId()));
        documentRepo.saveAndFlush(document);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateDocument(DocumentEntity document, Integer id) {
        if (getDocumentById(id) == null) {
            throw new EntityNotFoundException("Document does not exist");
        }
        else if (!documentValidation.isDocumentValid(document)) {
            throw new BusinessLogicException("Document is incorrect");
        }
        documentRepo.updateDocumentDeadline(id, document.getDeadline());
        documentRepo.updateDocumentAuthor(id, document.getAuthor());
        documentRepo.updateDocumentText(id, document.getText());
        documentRepo.updateDocumentSubject(id, document.getSubject());
    }

    public void deleteDocumentById(int id) {
        DocumentEntity forDelete = getDocumentById(id);
        if (forDelete != null) {
            documentRepo.delete(forDelete);
        }
        else {
            throw new EntityNotFoundException("Document does not exist");
        }
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void changeDocumentsStatus(Integer id, Event event) {
        DocumentEntity document = getDocumentById(id);
        stateMachine.setState(document.getIsDone(), document.getIsVerified());
        stateMachine.sendEvent(event);
        updateDocumentsStatus(document);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateDocumentsStatus(DocumentEntity document) {
        switch(stateMachine.getState()) {
            case PREPARE -> {
                document.setIsDone(null);
                document.setIsVerified(null);
            }
            case EXECUTION -> {
                document.setIsDone(false);
                document.setIsVerified(false);
            }
            case DONE -> {
                document.setIsDone(true);
                document.setIsVerified(true);
            }
            case REWORK -> {
                document.setIsDone(true);
                document.setIsVerified(false);
            }
            case CONTROL -> {
                document.setIsDone(false);
                document.setIsVerified(true);
            }
        }
        documentRepo.updateDocumentsStatus(document.getId(), document.getIsDone(), document.getIsVerified());
    }

    public boolean isDocumentExists(int id) {
        return documentRepo.existsById(id);
    }
}