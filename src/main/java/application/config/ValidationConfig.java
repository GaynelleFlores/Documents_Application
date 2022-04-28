package application.config;

import application.validation.DivisionValidation;
import application.validation.DocumentValidation;
import application.validation.EmployeeValidation;
import application.validation.OrganizationValidation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ValidationConfig {
    @Bean
    public OrganizationValidation getOrganizationValidation() {
        return new OrganizationValidation();
    }

    @Bean
    public EmployeeValidation getEmployeeValidation() {
        return new EmployeeValidation();
    }

    @Bean
    public DivisionValidation getDivisionValidation() {
        return new DivisionValidation();
    }

    @Bean
    public DocumentValidation getDocumentValidation() {
        return new DocumentValidation();
    }
}