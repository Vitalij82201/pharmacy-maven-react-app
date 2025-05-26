package org.hstn.pharmacy.controller.product;

import lombok.RequiredArgsConstructor;
import org.hstn.pharmacy.dto.product.PainReliefRequest;
import org.hstn.pharmacy.dto.product.PainReliefResponse;
import org.hstn.pharmacy.service.Product.PainReliefService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pain_relief")
public class PainReliefController {

    private final PainReliefService painReliefService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PainReliefResponse> create(@RequestBody PainReliefRequest painReliefRequest){
        return ResponseEntity.ok(painReliefService.create(painReliefRequest));
    }
    @GetMapping
    public ResponseEntity<List<PainReliefResponse>> readAll(){
        return ResponseEntity.ok(painReliefService.readAll());
    }
    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<PainReliefResponse> update(@RequestBody PainReliefRequest painReliefRequest){
        return ResponseEntity.ok(painReliefService.update(painReliefRequest));
    }
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        painReliefService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
