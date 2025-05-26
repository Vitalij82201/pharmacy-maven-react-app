package org.hstn.pharmacy.repository.user;

import org.hstn.pharmacy.entity.entityUser.ConfirmationCode;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;

public interface ConfirmationCodeRepository extends JpaRepository<ConfirmationCode, Integer> {

    Optional<ConfirmationCode> findByCodeAndExpiredDateTimeAfter(String code, LocalDateTime now);
    Optional<ConfirmationCode> findByCode(String code);

    @Modifying
    @Query("DELETE FROM ConfirmationCode c WHERE c.user.id = :userId")
    void deleteByUserId(@Param("userId") Integer userId);

    // Або (якщо ви хочете використовувати стандартний метод Spring Data JPA):
    void deleteByUser_Id(Integer userId);
}
