package java.com.growwithme.profiles.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.com.growwithme.profiles.domain.model.aggregates.FarmerUser;

public interface FarmerUserRepository extends JpaRepository<FarmerUser, Long> {
    boolean existsFarmerUserByDni(String dni);

    boolean existsFarmerUserByPhone(String phone);
}
