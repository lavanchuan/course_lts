package com.chuan.courcelts.controllers;

import com.chuan.courcelts.models.entities.Student;
import com.chuan.courcelts.services.IStudentService;
import com.chuan.courcelts.services.StudentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/student")
public class StudentController {
    @Autowired
    StudentService service;

    @GetMapping("/find/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/find")
    public ResponseEntity<?> findByNameAndEmail(@RequestParam(name = "fullname", required = false) String studentName,
                                                @RequestParam(name = "email", required = false) String email){
        return ResponseEntity.ok(service.findByNameAndEmail(studentName, email));
    }

    @GetMapping("/paging/find/all")
    public ResponseEntity<?> pagingFindAll(@RequestParam(name = "pageNum", required = true) int pageNum,
                                           @RequestParam(name = "sizeOf", required = true) int sizeOf){
        return ResponseEntity.ok(service.pagingFindAll(pageNum, sizeOf));
    }

    @GetMapping("/paging/find")
    public ResponseEntity<?> pagingFindByNameAndEmail(@RequestParam(name = "fullname", required = false) String fullname,
                                                      @RequestParam(name = "email", required = false) String email,
                                                      @RequestParam(name = "pageNum", required = true) int pageNum,
                                                      @RequestParam(name = "sizeOf", required = true) int sizeOf){
        return ResponseEntity.ok(service.pagingFindByNameAndEmail(fullname, email, pageNum, sizeOf));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody Student student){
        switch (service.add(student)){
            case IStudentService.CONFLICT: return ResponseEntity.status(HttpStatus.CONFLICT).body("Id student is exists");
            case IStudentService.IS_NULL: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("phone or email may be not null");
            case IStudentService.IS_EXISTS: return ResponseEntity.status(HttpStatus.CONFLICT).body("phone or email may be unique");
            default: return ResponseEntity.ok("Add success");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam(name = "studentId", required = true) int studentId,
                                    @RequestBody Student student){
        switch(service.update(studentId, student)){
            case IStudentService.NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id student is not exists");
            case IStudentService.IS_EXISTS: return ResponseEntity.status(HttpStatus.CONFLICT).body("Email or phone may be unique");
            default: return ResponseEntity.ok("Updated success");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "studentId", required = true) int studentId,
                                    @RequestParam(name = "isDelete", defaultValue = "false") boolean isDelete){
        switch (service.delete(studentId, isDelete)){
            case IStudentService.NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id student is not exists");
            case IStudentService.CONFLICT: return ResponseEntity.status(HttpStatus.CONFLICT).body("Other tables referencing this id");
            default: return ResponseEntity.ok("Deleted success");
        }
    }
}
