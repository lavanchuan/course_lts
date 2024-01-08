package com.chuan.courcelts.controllers;

import com.chuan.courcelts.models.entities.CourceType;
import com.chuan.courcelts.services.CourceTypeService;
import com.chuan.courcelts.services.mappers.CourceTypeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cource-type")
public class CourceTypeController {

    @Autowired
    CourceTypeService service;

    @GetMapping("/find/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.dbContext.courceTypeRepository.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam(name = "name", required = true) String name){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.courceTypeAdd(name));
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody CourceType courceType){
        CourceType result = service.courceTypeUpdate(CourceTypeMapper.toDTO(courceType));
        if(result == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Not found course type id: " + courceType.getCourceTypeId());
        return ResponseEntity.status(HttpStatus.OK)
                .body(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "id", required = true) int id,
                                    @RequestParam(name = "isDelete", defaultValue = "false") boolean isDelete){
        if(service.courceTypeDelete(id, isDelete)) return ResponseEntity.status(HttpStatus.OK)
                .body("Deleted success");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)// che giau api
                .body("Not found");
    }
}
