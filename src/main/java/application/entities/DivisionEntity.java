package application.entities;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString(exclude = { "employees"})
@Table(name = "divisions", schema = "public", catalog = "documentsApplication")
public class DivisionEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;
    
    @Column(name = "title")
    private String title;
    
    @Column(name = "phone_number")
    private String phoneNumber;
    
    @Column(name = "email")
    private String email;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "manager_id", referencedColumnName = "id")
    private EmployeeEntity manager;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name="organization_id")
    @EqualsAndHashCode.Exclude
    @JsonBackReference
    private OrganizationEntity organization;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "division", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    private List<EmployeeEntity> employees;
}
