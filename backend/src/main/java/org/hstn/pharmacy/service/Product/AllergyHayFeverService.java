package org.hstn.pharmacy.service.Product;

import lombok.RequiredArgsConstructor;
import org.hstn.pharmacy.dto.product.AllergyHayFeverRequest;
import org.hstn.pharmacy.dto.product.AllergyHayFeverResponse;
import org.hstn.pharmacy.entity.product.AllergyHayFever;
import org.hstn.pharmacy.repository.product.AllergyHayFeverRepository;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AllergyHayFeverService {

    private final AllergyHayFeverRepository allergyHayFeverRepository;

    public AllergyHayFeverResponse create(AllergyHayFeverRequest allergyHayFeverRequest) {
        AllergyHayFever allergyHayFeverEntity = mapRequestToEntity(allergyHayFeverRequest);
        return mapEntityToResponse(allergyHayFeverRepository.save(allergyHayFeverEntity));
    }
    public List<AllergyHayFeverResponse> readAll() {
        return allergyHayFeverRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public AllergyHayFeverResponse update(AllergyHayFeverRequest allergyHayFeverRequest) {
        AllergyHayFever allergyHayFeverEntity = mapRequestToEntity(allergyHayFeverRequest);
        return mapEntityToResponse(allergyHayFeverRepository.save(allergyHayFeverEntity));
    }

    public void delete(Integer id) {
        allergyHayFeverRepository.deleteById(id);
    }

    private AllergyHayFever mapRequestToEntity(AllergyHayFeverRequest allergyHayFeverRequest) {
        return AllergyHayFever.builder() // для update
                .amount(allergyHayFeverRequest.getAmount())
                .contain(allergyHayFeverRequest.getContain())
                .description(allergyHayFeverRequest.getDescription())
                .manufacturer(allergyHayFeverRequest.getManufacturer())
                .name(allergyHayFeverRequest.getName())
                .pharmaceuticalForm(allergyHayFeverRequest.getPharmaceuticalForm())
                .pzn(allergyHayFeverRequest.getPzn())
                .build();
    }

    private AllergyHayFeverResponse mapEntityToResponse(AllergyHayFever allergyHayFeverEntity) {
        return AllergyHayFeverResponse.builder()
                .id(allergyHayFeverEntity.getId())
                .amount(allergyHayFeverEntity.getAmount())
                .contain(allergyHayFeverEntity.getContain())
                .description(allergyHayFeverEntity.getDescription())
                .manufacturer(allergyHayFeverEntity.getManufacturer())
                .name(allergyHayFeverEntity.getName())
                .pharmaceuticalForm(allergyHayFeverEntity.getPharmaceuticalForm())
                .pzn(allergyHayFeverEntity.getPzn())
                .build();
    }
}
