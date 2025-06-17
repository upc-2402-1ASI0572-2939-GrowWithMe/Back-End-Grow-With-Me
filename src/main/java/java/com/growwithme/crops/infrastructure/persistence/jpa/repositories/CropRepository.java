package java.com.growwithme.crops.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.com.growwithme.crops.domain.model.aggregates.Crop;
import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    boolean existsCropByProductNameAndCode(String productName, String code);

    boolean existsCropByLocation(String location);

    List<Crop> findAllByFarmerUser_Id(Long farmerUserId);
}
