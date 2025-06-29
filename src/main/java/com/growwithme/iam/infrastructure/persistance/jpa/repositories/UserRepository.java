package com.growwithme.iam.infrastructure.persistance.jpa.repositories;

import com.growwithme.iam.domain.model.aggregates.User;
import com.growwithme.iam.domain.model.entities.Role;
import com.growwithme.iam.domain.model.valueobjects.Roles;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserById(Long id);

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);

    List<User> findAllByRoles_Name(Roles role);
}
