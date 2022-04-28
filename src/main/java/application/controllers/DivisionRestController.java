package application.controllers;

import application.entities.DivisionEntity;
import application.servises.DivisionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@CrossOrigin(origins = "*")
@RestController
public class DivisionRestController {

    private DivisionService divisionService;

    @Autowired
    public void setDivisionService(DivisionService divisionService) {
        this.divisionService = divisionService;
    }

    @GetMapping("getDivision/{id}")
    DivisionEntity getDivision(@PathVariable Integer id) {
        return divisionService.getDivisionById(id);
    }

    @GetMapping("getDivisions")
    Page<DivisionEntity> getDivisionsPage(@RequestParam("page") int page, @RequestParam("size") int size) {
        Pageable pageable = PageRequest.of(page, size);
        return divisionService.getDivisionsPage(pageable);
    }

    @GetMapping("getAllDivisions")
    List<DivisionEntity> getAllDivisions() {
        return divisionService.getAllDivisions();
    }

    @PostMapping("createDivision")
    public ResponseEntity<Object> createDivision(@RequestBody DivisionEntity div) {
        divisionService.createDivision(div);
        return ResponseEntity.ok("Division was created");
    }

    @PutMapping("updateDivision/{id}")
    public ResponseEntity<Object> updateDivision(@RequestBody DivisionEntity div, @PathVariable Integer id) {
        divisionService.updateDivision(div, id);
        return ResponseEntity.ok("Division was updated");
    }

    @DeleteMapping("deleteDivision/{id}")
    public ResponseEntity<Object> deleteDivision(@PathVariable Integer id) {
        divisionService.deleteDivisionById(id);
        return ResponseEntity.ok("Division was deleted");
    }
}