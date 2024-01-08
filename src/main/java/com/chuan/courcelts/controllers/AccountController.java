package com.chuan.courcelts.controllers;

import com.chuan.courcelts.models.dtos.AccountDTO;
import com.chuan.courcelts.services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/account")
public class AccountController {
    @Autowired
    AccountService service;

    @GetMapping("/find/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/paging/find/all")
    public ResponseEntity<?> pagingFindAll(@RequestParam(name = "pageNum", required = true) int pageNum,
                                           @RequestParam(name = "sizeOf", required = true) int sizeOf){
        return ResponseEntity.ok(service.pagingFindAll(pageNum, sizeOf));
    }

    @GetMapping("/find")
    public ResponseEntity<?> findByFullname(@RequestParam(name = "fullname", required = true) String fullname){
        return ResponseEntity.ok(service.findByFullname(fullname));
    }

    @GetMapping("/paging/find")
    public ResponseEntity<?> pagingFindByFullname(@RequestParam(name = "fullname", required = true) String fullname,
                                                  @RequestParam(name = "pageNum", required = true) int pageNum,
                                                  @RequestParam(name = "sizeOf", required = true) int sizeOf){
        return ResponseEntity.ok(service.pagingFindByFullname(fullname, pageNum, sizeOf));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody AccountDTO dto){
        switch(service.add(dto)){
            case CONFLICT: return ResponseEntity.status(HttpStatus.CONFLICT).body("Account is exists");
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Authority not found");
            case IS_EMPTY: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username and password is not null");
            case INVALID2: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username is unique or password must be contain numbers and special characters");
            default: return ResponseEntity.ok("Added account success");
        }
    }
    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody AccountDTO dto){
        switch(service.update(dto)){
            case IS_EMPTY: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Account id is not null");
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id account or authority is not found");
            case INVALID: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Username must be unique");
            case INVALID2: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Password must be contain numbers and special characters");
            default: return ResponseEntity.ok("Updated account success");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "id", required = true) int id,
            @RequestParam(name = "isDelete", defaultValue = "false") boolean isDelete) {
        switch (service.delete(id, isDelete)) {
            case IS_NOT_FOUND:
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Accout is not found");
            case INVALID2:
                return ResponseEntity.status(HttpStatus.CONFLICT).body(
                        "Other tables referencing this account"
                );
            default:
                return ResponseEntity.ok("Deleted account success");
        }
    }
}
