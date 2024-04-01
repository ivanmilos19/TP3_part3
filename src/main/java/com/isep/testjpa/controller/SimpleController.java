package com.isep.testjpa.controller;

import com.isep.testjpa.repository.EmpRepository;
import com.isep.testjpa.model.Emp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;

@RestController
public class SimpleController {

//    @Autowired
//    private EmpRepository empRepository;
//
//    @RequestMapping(value="/", method= RequestMethod.GET)
//    public String hello(@Param("name") String name) {
//        return "Hello " + name;
//    }
//
//    @RequestMapping(value="/employees", method= RequestMethod.GET)
//    public List<Emp> getEmployees() {
//        return empRepository.findAll();
//    }
//
//    @PostMapping(value="/employees")
//    public Emp addEmployee(@RequestBody Emp emp) {
//        return empRepository.save(emp);
//    }

    @Autowired
    private EmpRepository empRepository;

    @GetMapping("/")
    public String hello(@RequestParam(value = "name", required = false) String name) {
        return "Hello " + name;
    }

    @GetMapping("/employees")
    public List<Emp> getAllEmployees() {
        return empRepository.findAll();
    }

    @GetMapping("/employees/{id}")
    public Emp getEmployeeById(@PathVariable Long id) {
        Optional<Emp> emp = empRepository.findById(id);
        if(emp.isPresent()) {
            return emp.get();
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found");
        }
    }

    @PostMapping("/employees")
    public Emp addEmployee(@RequestBody Emp emp) {
        return empRepository.save(emp);
    }

    @PutMapping("/employees/{id}")
    public Emp updateEmployee(@PathVariable Long id, @RequestBody Emp employeeDetails) {
        Emp emp = empRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        emp.setEname(employeeDetails.getEname());
        emp.setEfirst(employeeDetails.getEfirst());
        emp.setJob(employeeDetails.getJob());
        emp.setMgr(employeeDetails.getMgr());
        emp.setSal(employeeDetails.getSal());
        return empRepository.save(emp);
    }

    @DeleteMapping("/employees/{id}")
    public void deleteEmployee(@PathVariable Long id) {
        Emp emp = empRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Employee not found"));
        empRepository.delete(emp);
    }

}
