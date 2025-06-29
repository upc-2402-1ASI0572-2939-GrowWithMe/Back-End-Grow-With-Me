package com.growwithme.crops.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.growwithme.crops.domain.model.aggregates.CropActivity;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CropActivityRepository extends JpaRepository<CropActivity, Long> {
    List<CropActivity> findAllByCrop_Id(Long cropId);

    void deleteAllByCrop_Id(Long cropId);

    boolean existsCropActivityByCrop_IdAndActivityDateNot(Long cropId, LocalDate activityDate);

    boolean existsCropActivityByIdAndCrop_IdNot(Long id, Long cropId);
}
