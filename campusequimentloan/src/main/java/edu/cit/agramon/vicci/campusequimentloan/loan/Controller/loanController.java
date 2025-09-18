package edu.cit.agramon.vicci.campusequimentloan.loan.Controller;
import edu.cit.agramon.vicci.campusequimentloan.loan.Entity.loanEntity;
import edu.cit.agramon.vicci.campusequimentloan.loan.Service.loanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/loan")
public class loanController {
    @Autowired
    loanService loanservice;

    @PutMapping("/add")
    public String createLoan(@RequestBody loanEntity loan){
        return loanservice.createLoan(loan.getEquipment(), loan.getStudent(),  loan.getStartDate());
    }

    @GetMapping("/getAll")
    public List<loanEntity> getAllLoans(){
        return loanservice.getAllLoans();
    }


    @PostMapping("/return/{loanId}")
    public String returnLoan(@PathVariable Long loanId, @RequestBody Map<String, String> body) {
        // Extract the returnDate field from the request body
        String returnDateStr = body.get("returnDate");

        // Check if the returnDate is present in the body
        if (returnDateStr == null) {
            return "Return date not provided in the request body.";
        }

        System.out.println("Received returnDate: " + returnDateStr); // Log the returnDate string

        try {
            // Parse the returnDate string into LocalDateTime
            LocalDateTime returnDate = LocalDateTime.parse(returnDateStr);

            // Now call your service method and return its response
            return loanservice.returnLoan(loanId, returnDate);

        } catch (Exception e) {
            // If there's an issue parsing the returnDate, return an error message
            return "Invalid returnDate format. Please provide a valid date-time format.";
        }
    }

}
