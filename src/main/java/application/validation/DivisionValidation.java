package application.validation;

import application.entities.DivisionEntity;
import application.servises.EmployeeService;
import application.servises.OrganizationService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class DivisionValidation implements ValidationInterface {

    private EmployeeService employeeService;

    private OrganizationService organizationService;

    private Logger logger;

    @Autowired
    public void setEmployeeService(@Lazy EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setOrganizationService(@Lazy OrganizationService organizationService) {
        this.organizationService = organizationService;
    }

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private boolean checkEmail(String email) {
        if (email == null)
            return true;
        String regexp = "^[A-Za-z0-9+_-]+@(.+)\\.(.+)$";
        if (!email.matches(regexp)) {
            logger.info("Trying to create division with incorrect email");
            return false;
        }
        return true;
    }

    private boolean checkPhoneNumber(String phoneNumber) {
        String regexp = "8\\d{4}";
        if (!phoneNumber.matches(regexp)) {
            logger.info("Trying to create division with incorrect phoneNumber");
            return false;
        }
        return true;
    }

    private boolean checkOrganization(DivisionEntity division) {
        if (!organizationService.isOrganizationExists(division.getOrganization().getId())) {
            logger.info("Trying to create division with invalid organization");
            return false;
        }
        return true;
    }

    private boolean checkManager(DivisionEntity division) {
        if (!employeeService.isEmployeeExists(division.getManager().getId())) {
            logger.info("Trying to create division with invalid manager");
            return false;
        }
        return true;
    }

    private boolean checkContacts(DivisionEntity division) {
        return checkEmail(division.getEmail()) && checkPhoneNumber(division.getPhoneNumber());
    }

    private boolean checkNotNullFields(DivisionEntity division) {
        if (isNullOrEmpty(division.getTitle()) || isNullOrEmpty(division.getPhoneNumber())
                || division.getOrganization() == null) {
            logger.info("Trying to create division with null fields");
            return false;
        }
        return true;
    }

    public boolean isDivisionValid(DivisionEntity division) {
        if (!checkNotNullFields(division) || !checkContacts(division)
                || !checkManager(division) || !checkOrganization(division)) {
            return false;
        }
        return true;
    }
}