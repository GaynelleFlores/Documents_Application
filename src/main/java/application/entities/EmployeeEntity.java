package application.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString(exclude = { "createdDocuments", "documentsForExecution", "managedDivision", "division"})
@Table(name = "employees", schema = "public", catalog = "documentsApplication")
public class EmployeeEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    
    @Column(name = "name")
    private String name;
    
    @Column(name = "surname")
    private String surname;
    
    @Column(name = "patronymic")
    private String patronymic;
    
    @Column(name = "position")
    private String position;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="division_id")
    @JsonBackReference
    @EqualsAndHashCode.Exclude
    private DivisionEntity division;

    @OneToOne(mappedBy = "manager")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private DivisionEntity managedDivision;

    @OneToOne(mappedBy = "director")
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private OrganizationEntity managedOrganization;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "author", cascade = {CascadeType.MERGE, CascadeType.PERSIST})
    @JsonIgnore
    @EqualsAndHashCode.Exclude
    private List<DocumentEntity> createdDocuments;

    @ManyToMany
    @JoinTable(
            name = "documents_executors",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "document_id"))
    @EqualsAndHashCode.Exclude
    @JsonIgnore
    private List<DocumentEntity> documentsForExecution;
}