package application.servises;

import application.entities.EmployeeEntity;
import application.exceptions.BusinessLogicException;
import application.repositories.EmployeeRepository;
import application.validation.EmployeeValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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
public class EmployeeService {

    private final EmployeeRepository employeeRepo;

    private EmployeeValidation employeeValidation;

    @Autowired
    public void setEmployeeValidation(EmployeeValidation employeeValidation) {
        this.employeeValidation = employeeValidation;
    }

    public EmployeeEntity getEmployeeById(int id) {
        Optional<EmployeeEntity> employee = employeeRepo.findById(id);
        if (employee.isEmpty()) {
            throw new EntityNotFoundException("Employee does not exist");
        }
        return employee.get();
    }

    public Page<EmployeeEntity> getEmployeesPage(Pageable pageable) {
        return employeeRepo.findAll(pageable);
    }

    public List<EmployeeEntity> getAllEmployees() {
        return employeeRepo.findAll();
    }

    public void createEmployee(EmployeeEntity employee) {
        if (!employeeValidation.isEmployeeValid(employee)) {
            throw new BusinessLogicException("Employee is incorrect");
        }
        if (isEmployeeExists(employee.getId())) {
            throw new BusinessLogicException("Employee already exists");
        }
        employeeRepo.saveAndFlush(employee);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateEmployee(EmployeeEntity employee, Integer id) {
        if (!isEmployeeExists(id)) {
            throw new EntityNotFoundException("Employee does not exist");
        }
        else if (!employeeValidation.isEmployeeValid(employee)) {
            throw new BusinessLogicException("Employee is incorrect");
        }
        employeeRepo.updateEmployeesName(id, employee.getName(), employee.getSurname(), employee.getPatronymic());
        employeeRepo.updateEmployeesDivision(id, employee.getDivision());
    }

    public boolean isEmployeeExists(int id) {
       return employeeRepo.existsById(id);
    }

    public void deleteEmployeeById(int id) {
        EmployeeEntity forDelete = getEmployeeById(id);
        if (forDelete != null) {
            employeeRepo.delete(forDelete);
        }
        else {
            throw new EntityNotFoundException("Employee does not exist");
        }
    }
}