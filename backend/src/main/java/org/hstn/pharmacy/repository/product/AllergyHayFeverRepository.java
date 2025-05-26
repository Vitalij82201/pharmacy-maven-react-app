package org.hstn.pharmacy.repository.product;

import org.hstn.pharmacy.entity.product.AllergyHayFever;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AllergyHayFeverRepository  extends JpaRepository<AllergyHayFever, Integer> {
}
