package org.hstn.pharmacy.repository.product;

import org.hstn.pharmacy.entity.product.LovePotency;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LovePotencyRepository extends JpaRepository<LovePotency, Integer> {
}
