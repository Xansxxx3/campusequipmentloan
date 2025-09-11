package edu.cit.agramon.vicci.campusequimentloan.loan.Service;

import edu.cit.agramon.vicci.campusequimentloan.equipment.Entity.equipmentEntity;
import edu.cit.agramon.vicci.campusequimentloan.equipment.Repo.equipmentRepo;
import edu.cit.agramon.vicci.campusequimentloan.loan.Entity.loanEntity;
import edu.cit.agramon.vicci.campusequimentloan.loan.Penalty.LatePenaltyStrategy;
import edu.cit.agramon.vicci.campusequimentloan.loan.Penalty.penalty;
import edu.cit.agramon.vicci.campusequimentloan.loan.Repo.loanRepo;
import edu.cit.agramon.vicci.campusequimentloan.student.Entity.studentEntity;
import edu.cit.agramon.vicci.campusequimentloan.student.Repo.studentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class loanService {
    @Autowired
    public loanRepo loanrepo;

    @Autowired
    public studentRepo studentrepo;
    @Autowired
    public equipmentRepo equipmentrepo;
    private penalty penaltyStrategy = new LatePenaltyStrategy();


    public loanEntity createLoan(String equipment, Double penalty, String studentNo, LocalDateTime startDate, LocalDateTime dueDate, LocalDateTime returnDate, String status){
       System.out.println(studentNo);

        Optional<studentEntity> studentOpt = studentrepo.findByStudentNo(studentNo);

        if (studentOpt.isEmpty()) {
            throw new IllegalStateException("Student with studentNo " + studentNo + " does not exist.");
        }

        long activeLoansCount = loanrepo.countByStudentAndStatus(studentNo, "ACTIVE");

        if (activeLoansCount >= 2) {
            throw new IllegalStateException("A student can only have a maximum of 2 active loans.");
        }

        Optional<equipmentEntity> equipmentOpt = equipmentrepo.findByNameAndAvailability(equipment, "AVAILABLE");

        if (equipmentOpt.isEmpty()) {
            throw new IllegalStateException("The equipment is either unavailable or does not exist.");
        }


        dueDate = startDate.plusDays(7);
        loanEntity loan = new loanEntity();
        loan.setEquipment(equipment);
        loan.setStudent(studentNo);
        loan.setStartDate(startDate);
        loan.setDueDate(dueDate);
        loan.setReturnDate(returnDate);
        loan.setStatus(status);
        return loanrepo.save(loan);
    }

    public double calculateLatePenalty(loanEntity loan) {
        if (isLoanOverdue(loan)) {
            long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), LocalDateTime.now());
            return penaltyStrategy.calculatePenalty(overdueDays);
        }
        return 0.0;
    }
    public loanEntity returnLoan(Long loanId, LocalDateTime returnDate) {
        // Retrieve the loan by ID
        loanEntity loan = loanrepo.findById(loanId)
                .orElseThrow(() -> new IllegalStateException("Loan not found"));

        // Update the return date of the loan
        loan.setReturnDate(returnDate);

        // Check if the loan is overdue
        double penaltyAmount = 0.0;
        if (returnDate.isAfter(loan.getDueDate())) {
            // If the loan is returned after the due date, calculate the penalty
            long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), returnDate);
            penaltyAmount = penaltyStrategy.calculatePenalty(overdueDays);
        }

        // Update penalty in the loan entity
        loan.setPenalty(penaltyAmount);

        // Update the loan status to "RETURNED"
        loan.setStatus("RETURNED");

        // Save the updated loan entity
        return loanrepo.save(loan);
    }

    public boolean isLoanOverdue(loanEntity loan) {
        return loan.getDueDate().isBefore(LocalDateTime.now());
    }
}
