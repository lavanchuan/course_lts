package com.chuan.courcelts.controllers;

import com.chuan.courcelts.models.dtos.ArticleDTO;
import com.chuan.courcelts.services.ArticleService;
import org.apache.coyote.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/article")
public class ArticleController {
    @Autowired
    ArticleService service;

    @GetMapping("/find/all")
    public ResponseEntity<?> findAll() {
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/find")
    public ResponseEntity<?> findByArticleName(@RequestParam(name = "articleName", required = true) String articleName) {
        return ResponseEntity.ok(service.findByArticleName(articleName));
    }

    @GetMapping("/paging/find/all")
    public ResponseEntity<?> pagingFindAll(@RequestParam(name = "pageNum", required = true) int pageNum,
                                           @RequestParam(name = "sizeOf", required = true) int sizeOf) {
        return ResponseEntity.ok(service.pagingFindAll(pageNum, sizeOf));
    }

    @GetMapping("/paging/find")
    public ResponseEntity<?> pagingFindByArticleName(@RequestParam(name = "articleName", required = true) String articleName,
                                                     @RequestParam(name = "pageNum", required = true) int pageNum,
                                                     @RequestParam(name = "sizeOf", required = true) int sizeOf) {
        return ResponseEntity.ok(service.pagingFindByArticleName(articleName, pageNum, sizeOf));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody ArticleDTO dto){
        switch(service.add(dto)){
            case IS_EMPTY: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Fields is not null");
            case CONFLICT: return ResponseEntity.status(HttpStatus.CONFLICT).body("Article is exists");
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Theme or account is not found");
            default: return ResponseEntity.ok("Added article success");
        }
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestBody ArticleDTO dto){
        switch(service.update(dto)){
            case IS_EMPTY: return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("article id is not null");
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("article, theme or account is not found");
            default: return ResponseEntity.ok("Updated article success");
        }
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "articleId", required = true) int articleId){
        switch(service.delete(articleId)){
            case IS_NOT_FOUND: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("article is not found");
            default: return ResponseEntity.ok("Deleted article success");
        }
    }
}
