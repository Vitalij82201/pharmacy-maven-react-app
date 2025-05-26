package org.hstn.pharmacy.service.Product;

import lombok.RequiredArgsConstructor;
import org.hstn.pharmacy.dto.product.LovePotencyRequest;
import org.hstn.pharmacy.dto.product.LovePotencyResponse;
import org.hstn.pharmacy.entity.product.LovePotency;
import org.hstn.pharmacy.repository.product.LovePotencyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class LovePotencyService {

    private final LovePotencyRepository lovePotencyRepository;

    public LovePotencyResponse create(LovePotencyRequest lovePotencyRequest) {
        LovePotency lovePotencyEntity = mapRequestToEntity(lovePotencyRequest);
        return mapEntityToResponse(lovePotencyRepository.save(lovePotencyEntity));
    }

    public List<LovePotencyResponse> readAll() {
        return lovePotencyRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public LovePotencyResponse update(LovePotencyRequest lovePotencyRequest) {
        LovePotency lovePotencyEntity = mapRequestToEntity(lovePotencyRequest);
        return mapEntityToResponse(lovePotencyRepository.save(lovePotencyEntity));
    }

    public void delete(Integer id) {
        lovePotencyRepository.deleteById(id);
    }

    private LovePotency mapRequestToEntity(LovePotencyRequest lovePotencyRequest) {
        return LovePotency.builder() // для update
                .amount(lovePotencyRequest.getAmount())
                .contain(lovePotencyRequest.getContain())
                .description(lovePotencyRequest.getDescription())
                .manufacturer(lovePotencyRequest.getManufacturer())
                .name(lovePotencyRequest.getName())
                .pharmaceuticalForm(lovePotencyRequest.getPharmaceuticalForm())
                .pzn(lovePotencyRequest.getPzn())
                .build();
    }

    private LovePotencyResponse mapEntityToResponse(LovePotency lovePotencyEntity) {
        return LovePotencyResponse.builder()
                .id(lovePotencyEntity.getId())
                .amount(lovePotencyEntity.getAmount())
                .contain(lovePotencyEntity.getContain())
                .description(lovePotencyEntity.getDescription())
                .manufacturer(lovePotencyEntity.getManufacturer())
                .name(lovePotencyEntity.getName())
                .pharmaceuticalForm(lovePotencyEntity.getPharmaceuticalForm())
                .pzn(lovePotencyEntity.getPzn())
                .build();
    }
}