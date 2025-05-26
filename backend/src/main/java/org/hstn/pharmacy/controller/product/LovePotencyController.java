package org.hstn.pharmacy.controller.product;

import lombok.RequiredArgsConstructor;
import org.hstn.pharmacy.dto.product.LovePotencyRequest;
import org.hstn.pharmacy.dto.product.LovePotencyResponse;
import org.hstn.pharmacy.service.Product.LovePotencyService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/love_potency")
public class LovePotencyController {

    private final LovePotencyService lovePotencyService;

@PostMapping
@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LovePotencyResponse> create(@RequestBody LovePotencyRequest lovePotencyRequest){
    return ResponseEntity.ok(lovePotencyService.create(lovePotencyRequest));
}

@GetMapping
    public ResponseEntity<List<LovePotencyResponse>> readAll(){
    return ResponseEntity.ok(lovePotencyService.readAll());
}

@PutMapping
@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<LovePotencyResponse> update(@RequestBody LovePotencyRequest lovePotencyRequest){
    return ResponseEntity.ok(lovePotencyService.update(lovePotencyRequest));
}

@DeleteMapping("/{id}")
@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> delete(@PathVariable Integer id){
    lovePotencyService.delete(id);
    return ResponseEntity.noContent().build();
}
}

