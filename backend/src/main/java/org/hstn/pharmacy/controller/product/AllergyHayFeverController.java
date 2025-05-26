package org.hstn.pharmacy.controller.product;

import lombok.RequiredArgsConstructor;
import org.hstn.pharmacy.dto.product.AllergyHayFeverRequest;
import org.hstn.pharmacy.dto.product.AllergyHayFeverResponse;
import org.hstn.pharmacy.service.Product.AllergyHayFeverService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/allergy_hay_fever")
public class AllergyHayFeverController {

    private final AllergyHayFeverService allergyHayFeverService;

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AllergyHayFeverResponse> create(@RequestBody AllergyHayFeverRequest allergyHayFeverRequest){
        return ResponseEntity.ok(allergyHayFeverService.create(allergyHayFeverRequest));
    }

    @GetMapping
    public ResponseEntity<List<AllergyHayFeverResponse>> readAll(){
        return ResponseEntity.ok(allergyHayFeverService.readAll());
    }

    @PutMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<AllergyHayFeverResponse> update(@RequestBody AllergyHayFeverRequest allergyHayFeverRequest){
        return ResponseEntity.ok(allergyHayFeverService.update(allergyHayFeverRequest));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
        allergyHayFeverService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
