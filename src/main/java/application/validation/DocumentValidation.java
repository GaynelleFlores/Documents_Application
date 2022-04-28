package application.validation;

import application.entities.DocumentEntity;
import application.servises.EmployeeService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;

import java.sql.Date;

public class DocumentValidation implements ValidationInterface {

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

    private boolean checkAuthor(DocumentEntity document) {
        if (!employeeService.isEmployeeExists(document.getAuthor().getId())) {
            logger.info("Trying to create document with invalid author");
            return false;
        }
        return true;
    }

    private boolean checkDeadline(Date deadline) {
        Date today = new Date(System.currentTimeMillis());
        if (today.after(deadline)) {
            logger.info("Trying to create document with incorrect deadline");
            return false;
        }
        return true;
    }

    private boolean checkNotNullFields(DocumentEntity document) {
        if (isNullOrEmpty(document.getSubject())
                || isNullOrEmpty(document.getText()) || document.getAuthor() == null || document.getDeadline() == null) {
            logger.info("Trying to create document with null fields");
            return false;
        }
        return true;
    }

    public boolean isDocumentValid(DocumentEntity document) {
        if (!checkNotNullFields(document) || !checkAuthor(document)
                || !checkDeadline(document.getDeadline())) {
            return false;
        }
        return true;
    }
}