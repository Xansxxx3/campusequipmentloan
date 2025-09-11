package edu.cit.agramon.vicci.campusequimentloan.equipment.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.cit.agramon.vicci.campusequimentloan.equipment.Entity.equipmentEntity;

@Repository
public interface equipmentRepo extends JpaRepository<equipmentEntity, Long>{

    Optional<equipmentEntity> findByNameAndAvailability(String equipment, String availability);
}