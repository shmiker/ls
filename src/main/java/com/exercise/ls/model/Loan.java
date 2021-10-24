package com.exercise.ls.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

/*
Create a loan with the following properties as input:
        Amount
        Interest rate
        Length of loan in months
        Monthly payment amount*/

@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
public class Loan {

    @Id
    @GeneratedValue
    private Long id;

    private long amount;

    private int interestRate;

    private int length;

    private double monthlyPayment;



    public Loan(long amount, int interestRate, int length, double monthlyPayment) {
        this.amount = amount;
        this.interestRate = interestRate;
        this.length = length;
        this.monthlyPayment = monthlyPayment;
    }
}
