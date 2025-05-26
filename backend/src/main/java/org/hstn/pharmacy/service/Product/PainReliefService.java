package org.hstn.pharmacy.service.Product;

import lombok.RequiredArgsConstructor;
import org.hstn.pharmacy.dto.product.PainReliefRequest;
import org.hstn.pharmacy.dto.product.PainReliefResponse;
import org.hstn.pharmacy.entity.product.PainRelief;
import org.hstn.pharmacy.repository.product.PainReliefRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
@RequiredArgsConstructor
public class PainReliefService {

    private final PainReliefRepository painReliefRepository;

    public PainReliefResponse create(PainReliefRequest painReliefRequest) {
        PainRelief painReliefEntity = mapRequestToEntity(painReliefRequest);
        return mapEntityToResponse(painReliefRepository.save(painReliefEntity));
    }
        public List<PainReliefResponse> readAll () {
            return painReliefRepository.findAll().stream()
                    .map(this::mapEntityToResponse)
                    .collect(Collectors.toList());
        }
        public PainReliefResponse update (PainReliefRequest painReliefRequest){
            PainRelief painReliefEntity = mapRequestToEntity(painReliefRequest);
            return mapEntityToResponse(painReliefRepository.save(painReliefEntity));
        }
        public void delete (Integer id){
            painReliefRepository.deleteById(id);
        }

        private PainRelief mapRequestToEntity (PainReliefRequest painReliefRequest){
            return PainRelief.builder()
                    .amount(painReliefRequest.getAmount())
                    .contain(painReliefRequest.getContain())
                    .description(painReliefRequest.getDescription())
                    .manufacturer(painReliefRequest.getManufacturer())
                    .name(painReliefRequest.getName())
                    .pharmaceuticalForm(painReliefRequest.getPharmaceuticalForm())
                    .pzn(painReliefRequest.getPzn())
                    .build();
        }

        private PainReliefResponse mapEntityToResponse(PainRelief painReliefEntity){
            return PainReliefResponse.builder()
                    .id(painReliefEntity.getId())
                    .amount(painReliefEntity.getAmount())
                    .contain(painReliefEntity.getContain())
                    .description(painReliefEntity.getDescription())
                    .manufacturer(painReliefEntity.getManufacturer())
                    .name(painReliefEntity.getName())
                    .pharmaceuticalForm(painReliefEntity.getPharmaceuticalForm())
                    .pzn(painReliefEntity.getPzn())
                    .build();
        }
    }

