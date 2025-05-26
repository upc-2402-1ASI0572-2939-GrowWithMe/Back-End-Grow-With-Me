package java.com.growwithme.crops.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import java.com.growwithme.crops.domain.model.aggregates.Crop;

public interface CropRepository extends JpaRepository<Crop, Long> {
}
