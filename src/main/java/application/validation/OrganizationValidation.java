package application.validation;

import application.entities.OrganizationEntity;
import application.servises.EmployeeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

public class OrganizationValidation implements ValidationInterface {

    private EmployeeService employeeService;

    private Logger logger;

    @Autowired
    public void setEmployeeService(@Lazy EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    @Autowired
    public void setLogger(Logger logger) {
        this.logger = logger;
    }

    private boolean checkDirector(OrganizationEntity organization) {
        if (!employeeService.isEmployeeExists(organization.getDirector().getId())) {
            logger.info("Trying to create organization with invalid director");
            return false;
        }
        return true;
    }

    private boolean checkNotNullFields(OrganizationEntity organization) {
        if (isNullOrEmpty(organization.getTitle())
                || isNullOrEmpty(organization.getAddress())
                || isNullOrEmpty(organization.getLegalAddress())) {
            logger.info("Trying to create organization with null fields");
            return false;
        }
        return true;
    }

    public boolean isOrganizationValid(OrganizationEntity organization) {
        if (!checkNotNullFields(organization) || !checkDirector(organization))
            return false;
        return true;
    }
}