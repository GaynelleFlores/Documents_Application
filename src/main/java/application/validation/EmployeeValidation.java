package application.validation;

import application.entities.EmployeeEntity;
import application.servises.DivisionService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class EmployeeValidation implements ValidationInterface {

    private DivisionService divisionService;

    private Logger logger;

    @Autowired
    public void setDivisionService(@Lazy DivisionService divisionService) {
        this.divisionService = divisionService;
    }

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private boolean checkDivision(EmployeeEntity employee) {
        if (!divisionService.isDivisionExists(employee.getDivision().getId())) {
            logger.info("Trying to create employee with invalid division");
            return false;
        }
        return true;
    }

    private boolean checkNotNullFields(EmployeeEntity employee) {
        if (isNullOrEmpty(employee.getName())
                || isNullOrEmpty(employee.getSurname())
                || isNullOrEmpty(employee.getPosition())
                || employee.getDivision() == null) {
            logger.info("Trying to create employee with null fields");
            return false;
        }
        return true;
    }

    public boolean isEmployeeValid(EmployeeEntity employee) {
        if (!checkNotNullFields(employee) || !checkDivision(employee)) {
            return false;
        }
        return true;
    }
}