package application.repositories;

import application.entities.DivisionEntity;
import application.entities.EmployeeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface EmployeeRepository extends JpaRepository<EmployeeEntity, Integer> {

    @Modifying
    @Query("update EmployeeEntity emp set emp.name = :name, emp.surname = :surname, emp.patronymic = :patronymic where emp.id = :id")
    void updateEmployeesName(@Param(value = "id") int id, @Param(value = "name") String name,
                                 @Param(value = "surname") String surname, @Param(value = "patronymic") String patronymic);

    @Modifying
    @Query("update EmployeeEntity emp set emp.division = :division where emp.id = :id")
    void updateEmployeesDivision(@Param(value = "id") int id, @Param(value = "division")DivisionEntity division);
}