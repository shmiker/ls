# LS API Exercise

Branches:

 - develop's client package is lightweight, only has classes for testing API calls from Java programmatically

 - master's client package has Vaadin client for manipulations through browser. Can be skipped since it will require npm installation and was not the requirement
 
   will require 'mvn package' to fetch npm packages

AWS instance:
 - POST ec2-3-82-163-56.compute-1.amazonaws.com:8080/api/loans/ # create Loan with following body:
 
   {
    "amount": 0,
    "interestRate": 0,
     "length": 0,
     "monthlyPayment": 0.0
   }

 - GET ec2-3-82-163-56.compute-1.amazonaws.com:8080/api/loans/{id} # get loan by ID
 
 - PUT ec2-3-82-163-56.compute-1.amazonaws.com:8080/api/loans/{id} # update loan with given ID

