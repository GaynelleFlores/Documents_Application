package application.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import javax.persistence.*;
import java.util.List;

@Entity
@Data
@ToString(exclude = { "divisions"})
@Table(name = "organizations", schema = "public", catalog = "documentsApplication")
public class OrganizationEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id")
    private int id;

    @Column(name = "title")
    private String title;

    @Column(name = "legal_address")
    private String legalAddress;

    @Column(name = "address")
    private String address;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "director_id", referencedColumnName = "id")
    private EmployeeEntity director;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "organization", cascade = {CascadeType.REMOVE, CascadeType.MERGE, CascadeType.PERSIST})
    @EqualsAndHashCode.Exclude
    @JsonManagedReference
    private List<DivisionEntity> divisions;
}