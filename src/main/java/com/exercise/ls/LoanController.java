package com.exercise.ls;

import com.exercise.ls.model.Loan;
import com.exercise.ls.repository.LoanRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api")
public class LoanController {

    @Autowired
    LoanRepository repository;

    @GetMapping("/loans/{id}")
    public ResponseEntity<Loan> getLoanById(@PathVariable("id") long id) {
        Optional<Loan> loanData = repository.findById(id);

        return loanData.map(loan -> new ResponseEntity<>(loan, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/loans")
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        try {
            repository.saveAndFlush(new Loan(loan.getAmount(), loan.getInterestRate(), loan.getLength(), loan.getMonthlyPayment()));
            return new ResponseEntity<>(loan, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/loans/{id}")
    public ResponseEntity<Loan> updateLoan(@PathVariable("id") long id, @RequestBody Loan loan) {
        Optional<Loan> loanData = repository.findById(id);

        if (loanData.isPresent()) {
            Loan updLoan = loanData.get();
            updLoan.setAmount(loan.getAmount());
            updLoan.setInterestRate(loan.getInterestRate());
            updLoan.setLength(loan.getLength());
            updLoan.setMonthlyPayment(loan.getMonthlyPayment());
            return new ResponseEntity<>(repository.save(updLoan), HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
