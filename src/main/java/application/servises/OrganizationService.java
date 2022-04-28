package application.servises;

import application.entities.OrganizationEntity;
import application.exceptions.BusinessLogicException;
import application.repositories.OrganizationRepository;
import application.validation.OrganizationValidation;
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
public class OrganizationService {

    private final OrganizationRepository organizationRepo;

    private EmployeeService employeeService;

    @Autowired
    public void setEmployeeService(@Lazy EmployeeService employeeService) {
        this.employeeService = employeeService;
    }

    private final OrganizationValidation organizationValidation;

    public OrganizationEntity getOrganizationById(int id) {
        Optional<OrganizationEntity> organization = organizationRepo.findById(id);
        if (organization.isEmpty()) {
            throw new EntityNotFoundException("Organization does not exist");
        }
        return organization.get();
    }

    public Page <OrganizationEntity> getOrganizationsPage(Pageable pageable) {
        return organizationRepo.findAll(pageable);
    }

    public List<OrganizationEntity> getAllOrganizations() {
        return organizationRepo.findAll();
    }

    public void createOrganization(OrganizationEntity organization) {
        if (!organizationValidation.isOrganizationValid(organization)) {
            throw new BusinessLogicException("Organization is incorrect");
        }
        organization.setDirector(employeeService.getEmployeeById(organization.getDirector().getId()));
        organizationRepo.saveAndFlush(organization);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, readOnly = false)
    public void updateOrganization(OrganizationEntity organization, Integer id) {
        if (getOrganizationById(id) == null) {
            throw new EntityNotFoundException("Organization does not exist");
        }
        else if (!organizationValidation.isOrganizationValid(organization)) {
            throw new BusinessLogicException("Organization is incorrect");
        }
        organizationRepo.updateOrganizationTitle(id, organization.getTitle());
        organizationRepo.updateOrganizationAddresses(id, organization.getAddress(), organization.getLegalAddress());
        organizationRepo.updateOrganizationDirector(id, employeeService.getEmployeeById( organization.getDirector().getId())               );
    }

    public void deleteOrganizationById(int id) {
        OrganizationEntity forDelete = getOrganizationById(id);
        if (forDelete != null) {
            organizationRepo.delete(forDelete);
        }
        else {
            throw new EntityNotFoundException("Organization does not exist");
        }
    }

    public boolean isOrganizationExists(int id) {
        return organizationRepo.existsById(id);
    }
}