package com.chuan.courcelts.controllers;

import com.chuan.courcelts.models.entities.ArticleType;
import com.chuan.courcelts.services.ArticleTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/articleType")
public class ArticleTypeController {
    @Autowired
    ArticleTypeService service;

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
    public ResponseEntity<?> add(@RequestBody ArticleType articleType){
        switch(service.add(articleType)){
            case CONFLICT: return ResponseEntity.status(HttpStatus.CONFLICT).body("Article type is exists");
            default: return ResponseEntity.ok("Added article type success");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ArticleType articleType){
        switch(service.update(articleType)){
            case IS_EMPTY: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("article type id is not null");
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("article type is not found");
            default: return ResponseEntity.ok("Updated article type success");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "articleTypeId", required = true) int articleTypeId,
                                    @RequestParam(name = "isDelete", defaultValue = "false") boolean isDelete){
        switch(service.delete(articleTypeId, isDelete)){
            case INVALID2: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Other tables referencing this article type");
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("article type is not found");
            default: return ResponseEntity.ok("Deleted article type success");
        }
    }


}
