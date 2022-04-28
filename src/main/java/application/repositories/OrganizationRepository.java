package application.repositories;

import application.entities.EmployeeEntity;
import application.entities.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrganizationRepository extends JpaRepository<OrganizationEntity, Integer> {

    @Modifying
    @Query("update OrganizationEntity org set org.title = :title where org.id = :id")
    void updateOrganizationTitle(@Param(value = "id") int id, @Param(value = "title") String title);

    @Modifying
    @Query("update OrganizationEntity org set org.address = :address, org.legalAddress = :legalAddress where org.id = :id")
    void updateOrganizationAddresses(@Param(value = "id") int id, @Param(value = "address") String address, @Param(value = "legalAddress") String legalAddress);

    @Modifying
    @Query("update OrganizationEntity org set org.director = :director where org.id = :id")
    void updateOrganizationDirector(@Param(value = "id") int id, @Param(value = "director") EmployeeEntity director);
}
