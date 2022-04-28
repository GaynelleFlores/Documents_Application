package application.repositories;

import application.entities.DivisionEntity;
import application.entities.EmployeeEntity;
import application.entities.OrganizationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface DivisionRepository  extends JpaRepository<DivisionEntity, Integer> {
    @Modifying
    @Query("update DivisionEntity div set div.phoneNumber = :phoneNumber, div.email = :email where div.id = :id")
    void updateDivisionsContacts(@Param(value = "id") int id, @Param(value = "phoneNumber")String phoneNumber, @Param(value = "email")String email);

    @Modifying
    @Query("update DivisionEntity div set div.organization = :organization where div.id = :id")
    void updateDivisionsOrganization(@Param(value = "id") int id, @Param(value = "organization")OrganizationEntity organization);

    @Modifying
    @Query("update DivisionEntity div set div.manager = :manager where div.id = :id")
    void updateDivisionsManager(@Param(value = "id") int id, @Param(value = "manager") EmployeeEntity managerId);
}