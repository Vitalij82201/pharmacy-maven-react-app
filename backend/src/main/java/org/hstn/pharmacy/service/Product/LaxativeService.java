package org.hstn.pharmacy.service.Product;

import lombok.RequiredArgsConstructor;
import org.hstn.pharmacy.dto.product.LaxativeRequest;
import org.hstn.pharmacy.dto.product.LaxativeResponse;
import org.hstn.pharmacy.entity.product.Laxative;
import org.hstn.pharmacy.repository.product.LaxativeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class LaxativeService {

    private final LaxativeRepository laxativeRepository;

    public LaxativeResponse create(LaxativeRequest laxativeRequest){
        Laxative laxativeEntity = mapRequestToEntity(laxativeRequest);
        return mapEntityToResponse(laxativeRepository.save(laxativeEntity));
    }

    public List<LaxativeResponse> readAll(){
        return laxativeRepository.findAll().stream()
                .map(this::mapEntityToResponse)
                .collect(Collectors.toList());
    }

    public LaxativeResponse update(LaxativeRequest laxativeRequest){
        Laxative laxativeEntity = mapRequestToEntity(laxativeRequest);
        return mapEntityToResponse(laxativeRepository.save(laxativeEntity));
    }

    public void delete(Integer id) { laxativeRepository.deleteById(id);}

    private Laxative mapRequestToEntity(LaxativeRequest laxativeRequest){
        return Laxative.builder()
                .amount(laxativeRequest.getAmount())
                .contain(laxativeRequest.getContain())
                .description(laxativeRequest.getDescription())
                .manufacturer(laxativeRequest.getManufacturer())
                .name(laxativeRequest.getName())
                .pharmaceuticalForm(laxativeRequest.getPharmaceuticalForm())
                .pzn(laxativeRequest.getPzn())
                .build();
    }

    private LaxativeResponse mapEntityToResponse(Laxative laxativeEntity) {
        return LaxativeResponse.builder()
                .id(laxativeEntity.getId())
                .amount(laxativeEntity.getAmount())
                .contain(laxativeEntity.getContain())
                .description(laxativeEntity.getDescription())
                .manufacturer(laxativeEntity.getManufacturer())
                .name(laxativeEntity.getName())
                .pharmaceuticalForm(laxativeEntity.getPharmaceuticalForm())
                .pzn(laxativeEntity.getPzn())
                .build();
    }
}
