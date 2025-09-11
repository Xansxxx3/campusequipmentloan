package edu.cit.agramon.vicci.campusequimentloan.student.Repo;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.cit.agramon.vicci.campusequimentloan.student.Entity.studentEntity;

@Repository
public interface studentRepo extends JpaRepository<studentEntity, Long>{
    Optional<studentEntity> findByStudentNo(String studentNo);
}
