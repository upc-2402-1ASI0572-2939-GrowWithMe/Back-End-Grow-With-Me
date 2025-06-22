package com.growwithme.crops.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.growwithme.crops.domain.model.aggregates.Crop;
import java.util.List;

@Repository
public interface CropRepository extends JpaRepository<Crop, Long> {
    List<Crop> findAllByFarmerUser_Id(Long farmerUserId);

    boolean existsCropByIdAndFarmerUser_IdNot(Long id, Long farmerUserId);

    boolean existsCropByProductNameAndCodeAndFarmerUser_IdNot(String productName, String code, Long farmerUserId);
}
