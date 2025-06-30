package com.growwithme.devices.infrastructure.persistence.jpa.repositories;

import com.growwithme.devices.domain.model.aggregates.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DeviceRepository extends JpaRepository<Device, Long> {
    boolean existsDeviceByCrop_IdAndFarmerUser_IdNot(Long cropId, Long farmerId);

    List<Device> findAllByFarmerUser_Id(Long farmerUserId);
}
