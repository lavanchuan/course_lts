package com.chuan.courcelts.controllers;

import com.chuan.courcelts.models.dtos.ThemeDTO;
import com.chuan.courcelts.services.ThemeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/theme")
public class ThemeController {
    @Autowired
    ThemeService service;

    @GetMapping("/find/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/paging/find")
    public ResponseEntity<?> pagingFindAll(@RequestParam(name = "pageNum", required = true) int pageNum,
                                           @RequestParam(name = "sizeOf", required = true) int sizeOf){
        return ResponseEntity.ok(service.pagingFindAll(pageNum, sizeOf));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ThemeDTO dto){
        switch(service.add(dto)){
            case CONFLICT: return ResponseEntity.status(HttpStatus.CONFLICT).body("Theme is exists");
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Article type is not found");
            default: return ResponseEntity.ok("Added theme success");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ThemeDTO dto){
        switch(service.update(dto)){
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theme or article type is not found");
            default: return ResponseEntity.ok("Updated theme success");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "themeId", required = true) int themeId,
                                    @RequestParam(name = "isDelete", defaultValue = "false") boolean isDelete){
        switch(service.delete(themeId, isDelete)){
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theme is not found");
            case INVALID2: return ResponseEntity.status(HttpStatus.CONFLICT).body("Other tables is referencing this theme");
            default: return ResponseEntity.ok("Deleted theme success");
        }
    }


}
