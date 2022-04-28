package application.controllers;

import application.entities.DivisionEntity;
import application.entities.OrganizationEntity;
import application.servises.OrganizationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequiredArgsConstructor
public class OrganizationRestController {

    private final OrganizationService organizationService;

    @GetMapping("getOrganization/{id}")
    OrganizationEntity getOrganization(@PathVariable Integer id) {
        return organizationService.getOrganizationById(id);
    }

    @GetMapping("getOrganizations")
    Page<OrganizationEntity> getOrganizationsPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return organizationService.getOrganizationsPage(pageable);
    }

    @GetMapping("getAllOrganizations")
    List<OrganizationEntity> getAllOrganizations() {
        return organizationService.getAllOrganizations();
    }

    @GetMapping("divisionsByOrganization/{id}")
    List<DivisionEntity> getDivisionsByOrganization(@PathVariable Integer id) {
        return organizationService.getOrganizationById(id).getDivisions();
    }

    @PostMapping("createOrganization")
    public ResponseEntity<Object> createOrganization(@RequestBody OrganizationEntity org) {
        organizationService.createOrganization(org);
        return ResponseEntity.ok("Organization was created");
    }

    @PutMapping("updateOrganization/{id}")
    public ResponseEntity<Object> updateOrganization(@RequestBody OrganizationEntity org, @PathVariable Integer id) {
        organizationService.updateOrganization(org, id);
        return ResponseEntity.ok("Organization was updated");
    }

    @DeleteMapping("deleteOrganization/{id}")
    public ResponseEntity<Object> deleteOrganization(@PathVariable Integer id) {
        organizationService.deleteOrganizationById(id);
        return ResponseEntity.ok("Organization was deleted");
    }
}