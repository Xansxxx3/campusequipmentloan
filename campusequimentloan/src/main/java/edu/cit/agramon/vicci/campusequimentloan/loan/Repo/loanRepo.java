package edu.cit.agramon.vicci.campusequimentloan.loan.Repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import edu.cit.agramon.vicci.campusequimentloan.loan.Entity.loanEntity;

@Repository
public interface loanRepo extends JpaRepository<loanEntity, Long>{

    long countByStudentAndStatus(String student, String status);
}