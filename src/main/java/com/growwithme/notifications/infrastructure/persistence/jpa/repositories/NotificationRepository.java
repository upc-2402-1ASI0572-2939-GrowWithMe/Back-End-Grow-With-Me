package com.growwithme.notifications.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.growwithme.notifications.domain.model.aggregates.Notification;
import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    List<Notification> findAllByFarmerUser_Id(Long farmerUserId);
}
