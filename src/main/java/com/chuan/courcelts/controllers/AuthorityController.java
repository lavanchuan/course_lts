package com.chuan.courcelts.controllers;

import com.chuan.courcelts.models.entities.Authority;
import com.chuan.courcelts.services.AuthorityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.EntityModel;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/authority")
public class AuthorityController {
    @Autowired
    AuthorityService service;

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
    public ResponseEntity<?> add(Authority authority){
        switch (service.add(authority)){
            case CONFLICT: return ResponseEntity.status(HttpStatus.CONFLICT).body("Authority is exists");
            default: return ResponseEntity.ok("Added success authority");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody Authority authority){
        switch (service.update(authority)){
            default: return ResponseEntity.ok("Updated success authority");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "id", required = true) int id,
                                    @RequestParam(name = "isDelete", defaultValue = "false") boolean isDelete){
        switch(service.delete(id, isDelete)){
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Authority id is not found");
            case INVALID2: return ResponseEntity.status(HttpStatus.CONFLICT).body("Account records is using this authority");
            default: return ResponseEntity.ok("Deleted authority success");
        }
    }

}
