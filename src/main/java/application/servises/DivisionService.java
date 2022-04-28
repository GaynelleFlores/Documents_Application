package application.servises;

import application.entities.DivisionEntity;
import application.exceptions.BusinessLogicException;
import application.repositories.DivisionRepository;
import application.validation.DivisionValidation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
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
public class DivisionService {

    private final DivisionRepository divisionRepo;

    private DivisionValidation divisionValidation;

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(@Lazy EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setDivisionValidation(DivisionValidation divisionValidation){
        this.divisionValidation = divisionValidation;
    }

    public void createDivision(DivisionEntity division) {
        if (isDivisionExists(division.getId())) {
            throw new EntityNotFoundException("Division already exists");
        }
        else if (!divisionValidation.isDivisionValid(division)) {
            throw new BusinessLogicException("Division is incorrect");
        }
        division.setManager(employeeService.getEmployeeById(division.getManager().getId()));
        divisionRepo.saveAndFlush(division);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void updateDivision(DivisionEntity division, Integer id) {
        if (!isDivisionExists(id)) {
            throw new EntityNotFoundException("Division does not exist");
        }
        else if (!divisionValidation.isDivisionValid(division)) {
            throw new BusinessLogicException("Division is incorrect");
        }
        divisionRepo.updateDivisionsContacts(id, division.getPhoneNumber(), division.getEmail());
        divisionRepo.updateDivisionsOrganization(id, division.getOrganization());
        divisionRepo.updateDivisionsManager(id, employeeService.getEmployeeById(division.getManager().getId()));
    }

    public DivisionEntity getDivisionById(int id) {
        Optional<DivisionEntity> division = divisionRepo.findById(id);
        if (division.isEmpty()) {
            throw new EntityNotFoundException("Division does not exist");
        }
        return division.get();
    }

    public Page<DivisionEntity> getDivisionsPage(Pageable pageable) {
        return divisionRepo.findAll(pageable);
    }

    public List<DivisionEntity> getAllDivisions() {
        return divisionRepo.findAll();
    }

    public void deleteDivisionById(int id) {
        DivisionEntity forDelete = getDivisionById(id);
        if (forDelete != null) {
            divisionRepo.delete(forDelete);
        }
        else {
            throw new EntityNotFoundException("Division does not exist");
        }
    }

    public boolean isDivisionExists(int id) {
        return divisionRepo.existsById(id);
    }
}