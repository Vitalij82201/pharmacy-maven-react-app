package org.hstn.pharmacy.controller.product;

import lombok.RequiredArgsConstructor;
import org.hstn.pharmacy.dto.product.LaxativeRequest;
import org.hstn.pharmacy.dto.product.LaxativeResponse;
import org.hstn.pharmacy.service.Product.LaxativeService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/laxative")
public class LaxativeController {

    private final LaxativeService laxativeService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LaxativeResponse> create(@RequestBody LaxativeRequest laxativeRequest){
        return ResponseEntity.ok(laxativeService.create(laxativeRequest));
    }
    @GetMapping
    public ResponseEntity<List<LaxativeResponse>> readAll(){
        return ResponseEntity.ok(laxativeService.readAll());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LaxativeResponse> update(@RequestBody LaxativeRequest laxativeRequest){
        return ResponseEntity.ok(laxativeService.update(laxativeRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void>delete(@PathVariable Integer id){
        laxativeService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
