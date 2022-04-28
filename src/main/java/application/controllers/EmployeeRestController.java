package application.controllers;

import application.entities.EmployeeEntity;
import application.servises.EmployeeService;
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
public class EmployeeRestController {

    private final EmployeeService employeeService;

    @GetMapping("getEmployee/{id}")
    EmployeeEntity getEmployee(@PathVariable Integer id) {
        return employeeService.getEmployeeById(id);
    }

    @GetMapping("getEmployees")
    Page<EmployeeEntity> getEmployeesPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return employeeService.getEmployeesPage(pageable);
    }

    @GetMapping("getAllEmployees")
    List<EmployeeEntity> getAllEmployees() {
        return employeeService.getAllEmployees();
    }

    @PostMapping("createEmployee")
    public ResponseEntity<Object> createEmployee(@RequestBody EmployeeEntity employee) {
        employeeService.createEmployee(employee);
        return ResponseEntity.ok("Employee was created");
    }

    @PutMapping("updateEmployee/{id}")
    public ResponseEntity<Object> updateEmployee(@RequestBody EmployeeEntity employee, @PathVariable Integer id) {
        employeeService.updateEmployee(employee, id);
        return ResponseEntity.ok("Employee was updated");
    }

    @DeleteMapping("deleteEmployee/{id}")
    public ResponseEntity<Object> deleteEmployee(@PathVariable Integer id) {
        employeeService.deleteEmployeeById(id);
        return ResponseEntity.ok("Employee was deleted");
    }
}