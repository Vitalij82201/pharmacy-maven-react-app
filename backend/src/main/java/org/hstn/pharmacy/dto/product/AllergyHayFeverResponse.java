package org.hstn.pharmacy.dto.product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AllergyHayFeverResponse {
    private Integer id;
    private Short amount;
    private String contain;
    private String description;
    private String manufacturer;
    private String name;
    private String pharmaceuticalForm;
    private String pzn;
}
