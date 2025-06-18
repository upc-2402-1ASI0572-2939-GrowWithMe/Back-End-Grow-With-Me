package com.growwithme.profiles.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.growwithme.profiles.domain.model.aggregates.ConsultantUser;

public interface ConsultantUserRepository extends JpaRepository<ConsultantUser, Long> {
    boolean existsConsultantUserByDni(String dni);

    boolean existsConsultantUserByPhone(String phone);
}
