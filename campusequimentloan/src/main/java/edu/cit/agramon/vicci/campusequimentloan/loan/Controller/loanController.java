package edu.cit.agramon.vicci.campusequimentloan.loan.Controller;
import edu.cit.agramon.vicci.campusequimentloan.loan.Entity.loanEntity;
import edu.cit.agramon.vicci.campusequimentloan.loan.Service.loanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/loan")
public class loanController {
    @Autowired
    loanService loanservice;

    @PutMapping("/add")
    public loanEntity createLoan(@RequestBody loanEntity loan){
        return loanservice.createLoan(loan.getEquipment(), loan.getPenalty(),loan.getStudent(), loan.getStartDate(), loan.getDueDate(), loan.getReturnDate(), loan.getStatus());
    }
    @PostMapping("/return/{loanId}")
    public loanEntity returnLoan(@PathVariable Long loanId, @RequestBody String returnDateStr) {
        System.out.println("Received returnDate: " + returnDateStr); // Log the returnDate string
        LocalDateTime returnDate = LocalDateTime.parse(returnDateStr); // Manually parse if necessary
        return loanservice.returnLoan(loanId, returnDate);
    }
}
