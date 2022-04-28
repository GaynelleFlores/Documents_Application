package application.entities;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.*;
import java.sql.Date;
import java.util.List;

@Entity
@Data
@ToString(exclude = { "executors"})
@Table(name = "documents", schema = "public", catalog = "documentsApplication")
public class DocumentEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    
    @Column(name = "subject")
    private String subject;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="author_id")
    @EqualsAndHashCode.Exclude
    private EmployeeEntity author;
    
    @Column(name = "deadline")
    private Date deadline;
    
    @Column(name = "is_verified")
    private Boolean isVerified;
    
    @Column(name = "is_done")
    private Boolean isDone;
    
    @Column(name = "text")
    private String text;

    @ManyToMany
    @JoinTable(
            name = "documents_executors",
            joinColumns = @JoinColumn(name = "document_id"),
            inverseJoinColumns = @JoinColumn(name = "employee_id"))
    @EqualsAndHashCode.Exclude
    private List<EmployeeEntity> executors;
}