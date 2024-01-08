package com.chuan.courcelts.controllers;

import com.chuan.courcelts.models.entities.StatusStudy;
import com.chuan.courcelts.services.IStatusService;
import com.chuan.courcelts.services.StatusService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/status")
public class StatusController {
    @Autowired
    StatusService service;

    @GetMapping("/find/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestParam(name = "statusName", required = true) String statusName){
        switch(service.add(statusName)){
            default: return ResponseEntity.ok("Added success");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody StatusStudy statusStudy){
        switch(service.update(statusStudy)){
            case IStatusService.NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id status is not exists");
            default: return ResponseEntity.ok("Updated success");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "statusId", required = true) int statusId,
                                    @RequestParam(name = "isDelete", defaultValue = "false") boolean isDelete){
        switch(service.delete(statusId, isDelete)){
            case IStatusService.NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Id status is not exists");
            case IStatusService.CONFLICT: return ResponseEntity.status(HttpStatus.CONFLICT).body("Other table referencing this id");
            default: return ResponseEntity.ok("Deleted success");
        }
    }
}
