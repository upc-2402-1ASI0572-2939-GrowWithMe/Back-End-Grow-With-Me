package java.com.growwithme.crops.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.com.growwithme.crops.domain.model.aggregates.CropActivity;
import java.util.Date;
import java.util.List;

@Repository
public interface CropActivityRepository extends JpaRepository<CropActivity, Long> {
    List<CropActivity> findAllByCrop_Id(Long cropId);

    void deleteAllByCrop_Id(Long cropId);

    boolean existsCropActivityByCrop_IdAndActivityDate(Long id, Date date);
}
