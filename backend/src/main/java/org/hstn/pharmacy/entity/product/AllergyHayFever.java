package org.hstn.pharmacy.entity.product;

import jakarta.persistence.*;

import lombok.*;

import java.util.Set;


@Entity
@Table(name = "Allergy_Hay_Fever")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AllergyHayFever {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private Short amount;
    private String contain;
    private String description;
    private String manufacturer;
    private String name;
    private String pharmaceuticalForm;
    private String pzn;

    @ManyToMany
    @JoinTable(
            name = "all_manufactures",
            joinColumns = @JoinColumn(name = "allergy_hay_fever_manufacteres"),
            inverseJoinColumns = @JoinColumn(name = "laxative_manufactures")
    )
    private Set<Laxative> collaborate;
}
