package com.chuan.courcelts.controllers;

import com.chuan.courcelts.models.dtos.CourceDTO;
import com.chuan.courcelts.services.CourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/cource")
public class CourceController {

    @Autowired
    CourceService service;

    @GetMapping("/find/all")
    public ResponseEntity<?> findAll(){
        return ResponseEntity.ok(service.findAll());
    }

    @GetMapping("/find")
    public ResponseEntity<?> findCourcesByName(@RequestParam(name = "courceName", required = true) String courceName){
        return ResponseEntity.status(HttpStatus.OK)
                .body(service.findCourceDTOsByName(courceName));
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody CourceDTO dto){
        CourceDTO result = service.add(dto);
        if(result == null) return ResponseEntity.status(HttpStatus.CONFLICT)
                .body("Id cource is exists");
        return ResponseEntity.ok(result);
    }

    @PutMapping("/update")
    public ResponseEntity<?> update(@RequestParam(name = "courceId", required = true) int courceId,
                                    @RequestBody CourceDTO dto){
        CourceDTO result = service.update(courceId, dto);
        if(result == null) return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Id cource not exists");
        return ResponseEntity.ok(result);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<?> delete(@RequestParam(name = "courceId", required = true) int courceId,
                                    @RequestParam(name = "isDelete", defaultValue = "false") boolean isDelete){
        if(service.delete(courceId, isDelete)) return ResponseEntity.ok("Delect success");
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Delete failure");
    }

    @GetMapping("/paging/find/all")
    public ResponseEntity<?> pagingFindAll(@RequestParam(name = "pageNum", required = true) int pageNum,
                                           @RequestParam(name = "sizeOf", required = true) int sizeOf){
        return ResponseEntity.ok(service.pagingFindAll(pageNum, sizeOf));
    }

    @GetMapping("/paging/find")
    public ResponseEntity<?> pagingFindByCourceName(@RequestParam(name = "pageNum", required = true) int pageNum,
                                           @RequestParam(name = "sizeOf", required = true) int sizeOf,
                                                    @RequestParam(name = "courceName", required = true) String courceName){
        return ResponseEntity.ok(service.pagingFindByName(courceName, pageNum, sizeOf));
    }


}
