package application.repositories;

import application.entities.DocumentEntity;
import application.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import java.sql.Date;
import org.springframework.data.repository.query.Param;

public interface DocumentRepository extends JpaRepository<DocumentEntity, Integer> {

    @Modifying
    @Query("update DocumentEntity doc set doc.subject = :subject where doc.id = :id")
    void updateDocumentSubject(@Param(value = "id") int id, @Param(value = "subject") String subject);

    @Modifying
    @Query("update DocumentEntity doc set doc.text = :text where doc.id = :id")
    void updateDocumentText(@Param(value = "id") int id, @Param(value = "text") String text);

    @Modifying
    @Query("update DocumentEntity doc set doc.deadline = :deadline where doc.id = :id")
    void updateDocumentDeadline(@Param(value = "id") int id, @Param(value = "deadline") Date deadline);

    @Modifying
    @Query("update DocumentEntity doc set doc.author = :author where doc.id = :id")
    void updateDocumentAuthor(@Param(value = "id") int id, @Param(value = "author") EmployeeEntity author);


    @Modifying
    @Query("update DocumentEntity doc set doc.isDone = :isDone, doc.isVerified = :isVerified where doc.id = :id")
    void updateDocumentsStatus(@Param(value = "id") int id, @Param(value = "isDone") Boolean is_done, @Param(value = "isVerified") Boolean isVerified);
}