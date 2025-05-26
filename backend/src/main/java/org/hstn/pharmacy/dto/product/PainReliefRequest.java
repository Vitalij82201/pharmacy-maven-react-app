package org.hstn.pharmacy.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PainReliefRequest {
    private String name;
    private String manufacturer;
    private String contain;
    private String pzn;
    private String pharmaceuticalForm;
    private String description;
    private Short amount;
}
