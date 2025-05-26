package org.hstn.pharmacy.repository.product;

import org.hstn.pharmacy.entity.product.Laxative;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LaxativeRepository extends JpaRepository<Laxative, Integer> {
}
