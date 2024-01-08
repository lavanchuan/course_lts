package com.chuan.courcelts.controllers;

import com.chuan.courcelts.advices.exceptions.RegisterNotFoundException;
import com.chuan.courcelts.models.StatusReturn;
import com.chuan.courcelts.models.dtos.RegisterDTO;
import com.chuan.courcelts.models.entities.Register;
import com.chuan.courcelts.services.RegisterService;
import com.chuan.courcelts.services.mappers.RegisterMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("/api/register")
public class RegisterController {
    @Autowired
    RegisterService service;

    @GetMapping("/find/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/paging/find/all")
    public ResponseEntity<?> pagingFindAll(@RequestParam(name = "pageNum", required = true) int pageNum,
                                           @RequestParam(name = "sizeOf", required = true) int sizeOf){
        return ResponseEntity.ok(service.pagingFindAll(pageNum, sizeOf));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody RegisterDTO dto){
        switch(service.add(dto)){
            case INVALID: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date invalid");
            case CONFLICT: return ResponseEntity.status(HttpStatus.CONFLICT).body("Register id or student id and course id is exists");
            case INVALID2: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Student id and course id is required");
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student, Course, State or Account not found");
            default: return ResponseEntity.ok("Added success");
        }
    }

    // TEST ADD
    @PostMapping("/add-test")
    public ResponseEntity<?> addTest(@RequestBody RegisterDTO dto){
        return ResponseEntity.ok(service.dbContext.registerRepository
                .save(RegisterMapper.toEntity(dto, service.dbContext))
        );
    }

    @GetMapping("/get")
    public ResponseEntity<?> getTest(@RequestBody RegisterDTO dto){
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/get2")
    public ResponseEntity<?> getTest2(@RequestBody RegisterDTO dto){
        return ResponseEntity.ok(RegisterMapper.toEntity(dto, service.dbContext));
    }

    @GetMapping("/find/{id}")
    public ResponseEntity<?> findById(@PathVariable int id){
        return ResponseEntity.ok(
                RegisterMapper.toDTO(service.dbContext.registerRepository.findById(id)
                        .orElseThrow(()->new RegisterNotFoundException(id))));

    }

    @GetMapping("/toDTO")
    public ResponseEntity<?> toDTO(@RequestBody RegisterDTO data){
        RegisterDTO dto = RegisterMapper.toDTO(service.dbContext.registerRepository
                .findById(data.getRegisterId()).orElseThrow(()->new RegisterNotFoundException(data.getRegisterId())));
        return ResponseEntity.ok(dto);
    }

    @GetMapping("/toEntity")
    public ResponseEntity<?> toEntity(@RequestBody RegisterDTO dto){
        Register entity = RegisterMapper.toEntity(dto, service.dbContext);
        String result = String.format("registerId: %d\n" +
                "courceId: %d\n" +
                "studentId: %d\n" +
                "accountId: %d\n" +
                "statusId: %d\n" +
                "regisDate: %s\n" +
                "startDate: %s\n" +
                "endDate: %s",
                entity.getRegisterId(),
                entity.getCource().getCourceId(),
                entity.getStudent().getStudentId(),
                entity.getAccount().getAccountId(),
                entity.getStatusStudy().getStatusId(),
                entity.getRegisDate() == null ? "null" : "yes",
                entity.getStartDate() == null ? "null" : "yes",
                entity.getEndDate() == null ? "null" : "yes");
//        return ResponseEntity.ok(result.toString());
        return ResponseEntity.ok(RegisterMapper.toDTO(service.dbContext.registerRepository.save(entity)));
    }

    @GetMapping("test-date/{date}")
    public ResponseEntity<?> testDate(@PathVariable @DateTimeFormat(pattern = "dd/mm/yyyy") LocalDate date){
        return ResponseEntity.ok(date);
    }

    @GetMapping("/test-date/{date1}/{date2}")
    public ResponseEntity<?> compareDate(@PathVariable @DateTimeFormat(pattern = "dd/mm/yyyy") LocalDate date1,
                                         @PathVariable @DateTimeFormat(pattern = "dd/mm/yyyy") LocalDate date2){
        return ResponseEntity.ok(date1 + "\n" + date2+"\n" +
                "compareTo: " + date1.compareTo(date2));
    }

    //////

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam(name = "registerId", required = true) int registerId,
                                 @RequestBody RegisterDTO dto){
        switch(service.update(registerId, dto)){
            case INVALID: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Date invalid");
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Student, status, course or account is not found");
            case CONFLICT: return ResponseEntity.status(HttpStatus.CONFLICT).body("Student and course is exists");
            default: return ResponseEntity.ok("Updated success");
        }
    }

    @DeleteMapping("delete")
    public ResponseEntity<?> delete(@RequestParam(name = "registerId", required = true) int registerId){
        switch(service.delete(registerId)){
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id register is not found");
            default: return ResponseEntity.ok("Deleted success");
        }
    }
}
