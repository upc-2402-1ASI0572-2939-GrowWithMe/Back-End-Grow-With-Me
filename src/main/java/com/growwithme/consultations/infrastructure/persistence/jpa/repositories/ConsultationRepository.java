package com.growwithme.consultations.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.growwithme.consultations.domain.model.aggregates.Consultation;
import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findAllByFarmerUser_Id(Long farmerId);

    boolean existsConsultationByIdAndFarmerUser_Id(Long id, Long farmerId);

    boolean findByTitleAndFarmerUser_Id(String title, Long farmerId);

    boolean existsConsultationByTitleAndFarmerUser_Id(String title, Long farmerId);
}
