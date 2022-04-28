package application.controllers;

import application.entities.DocumentEntity;
import application.servises.DocumentService;
import application.stateMachine.Event;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class DocumentRestController {

    private final DocumentService documentService;

    @GetMapping("getDocument/{id}")
    DocumentEntity getDocument(@PathVariable Integer id) {
        return documentService.getDocumentById(id);
    }

    @GetMapping("getDocuments")
    Page<DocumentEntity> getDocumentsPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return documentService.getDocumentsPage(pageable);
    }

    @GetMapping("getAllDocuments")
    List<DocumentEntity> getAllDocuments() {
        return documentService.getAllDocuments();
    }

    @PostMapping("createDocument")
    public ResponseEntity<Object> createDocument(@RequestBody DocumentEntity document) {
        documentService.createDocument(document);
        return ResponseEntity.ok("Document was created");
    }

    @PutMapping("updateDocument/{id}")
    public ResponseEntity<Object> updateDocument(@RequestBody DocumentEntity document, @PathVariable Integer id) {
        documentService.updateDocument(document, id);
        return ResponseEntity.ok("Document was updated");
    }

    @DeleteMapping("deleteDocument/{id}")
    public ResponseEntity<Object> deleteDocument(@PathVariable Integer id) {
        documentService.deleteDocumentById(id);
        return ResponseEntity.ok("Document was deleted");
    }

    @PutMapping("executeDocument/{id}")
    public ResponseEntity<Object> executeDocument(@PathVariable Integer id) {
        documentService.changeDocumentsStatus(id, Event.EXECUTE);
        return ResponseEntity.ok("Document was executed");
    }

    @PutMapping("checkDocument/{id}")
    public ResponseEntity<Object> checkDocument(@PathVariable Integer id) {
        documentService.changeDocumentsStatus(id, Event.CHECK);
        return ResponseEntity.ok("Document was successfully checked");
    }

    @PutMapping("reworkDocument/{id}")
    public ResponseEntity<Object> reworkDocument(@PathVariable Integer id) {
        documentService.changeDocumentsStatus(id, Event.REWORK);
        return ResponseEntity.ok("Document was reworked");
    }

    @PutMapping("finishDocument/{id}")
    public ResponseEntity<Object> finishDocument(@PathVariable Integer id) {
        documentService.changeDocumentsStatus(id, Event.FINISH);
        return ResponseEntity.ok("Document was done");
    }
}