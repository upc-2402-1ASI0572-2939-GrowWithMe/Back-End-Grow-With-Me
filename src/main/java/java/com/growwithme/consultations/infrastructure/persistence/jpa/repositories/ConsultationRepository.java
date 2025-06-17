package java.com.growwithme.consultations.infrastructure.persistence.jpa.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.com.growwithme.consultations.domain.model.aggregates.Consultation;
import java.util.List;

@Repository
public interface ConsultationRepository extends JpaRepository<Consultation, Long> {
    List<Consultation> findAllByFarmerUser_Id(Long farmerId);
}
