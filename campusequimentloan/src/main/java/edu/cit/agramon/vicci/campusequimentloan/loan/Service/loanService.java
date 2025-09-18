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


    public String createLoan(String equipment, String studentNo, LocalDateTime startDate) {
        System.out.println(studentNo);

        Optional<studentEntity> studentOpt = studentrepo.findByStudentNo(studentNo);

        if (studentOpt.isEmpty()) {
            return "Student with studentNo " + studentNo + " does not exist.";
        }

        long activeLoansCount = loanrepo.countByStudentAndStatus(studentNo, "ACTIVE");

        if (activeLoansCount >= 2) {
            return "A student can only have a maximum of 2 active loans.";
        }

        Optional<equipmentEntity> equipmentOpt = equipmentrepo.findByNameAndAvailability(equipment, true);

        if (equipmentOpt.isEmpty()) {
            return "The equipment is either unavailable or does not exist or it already exists.";
        }

        equipmentEntity equipmentEntity = equipmentOpt.get();

        equipmentEntity.setAvailability(false);

        equipmentrepo.save(equipmentEntity);

        loanEntity loan = new loanEntity();
        loan.setEquipment(equipment);
        loan.setStudent(studentNo);
        loan.setStartDate(startDate);
        loan.setDueDate(startDate.plusDays(7));
        loan.setReturnDate(null);
        loan.setStatus("ACTIVE");

        loanEntity savedLoan = loanrepo.save(loan);

        return "Loan successfully created. Loan ID: " + savedLoan.getId();
    }




    public double calculateLatePenalty(loanEntity loan) {
        if (isLoanOverdue(loan)) {
            long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), LocalDateTime.now());
            return penaltyStrategy.calculatePenalty(overdueDays);
        }
        return 0.0;
    }
    public String returnLoan(Long loanId, LocalDateTime returnDate) {
        // Retrieve the loan entity by ID
        Optional<loanEntity> loanOpt = loanrepo.findById(loanId);

        if (loanOpt.isEmpty()) {
            return "Loan not found for loanId: " + loanId;
        }

        loanEntity loan = loanOpt.get();
        loan.setReturnDate(returnDate);

        double penaltyAmount = 0.0;
        // Check if the return date is after the due date, and calculate the penalty
        if (returnDate.isAfter(loan.getDueDate())) {
            long overdueDays = ChronoUnit.DAYS.between(loan.getDueDate(), returnDate);
            penaltyAmount = penaltyStrategy.calculatePenalty(overdueDays);
        }

        loan.setPenalty(penaltyAmount);
        loan.setStatus("RETURNED");

        // Save the updated loan entity
        loan = loanrepo.save(loan);

        // Retrieve the equipment entity and update its availability
        Optional<equipmentEntity> equipmentOpt = equipmentrepo.findByNameAndAvailability(loan.getEquipment(), false);
        if (equipmentOpt.isPresent()) {
            equipmentEntity equipment = equipmentOpt.get();
            equipment.setAvailability(true);
            equipmentrepo.save(equipment);
        } else {
            return "Equipment not found for loan ID: " + loanId;
        }

        // Return success message with loan ID
        return "Loan successfully returned. Loan ID: " + loanId + " Penalty: " + penaltyAmount;
    }


    public List<loanEntity> getAllLoans(){
        return loanrepo.findAll();
    }


    public boolean isLoanOverdue(loanEntity loan) {
        return loan.getDueDate().isBefore(LocalDateTime.now());
    }
}
