package com.exercise.ls;

import com.exercise.ls.model.Loan;
import com.exercise.ls.repository.LoanRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class LnstrtApplication {

	private static final Logger log = LoggerFactory.getLogger(LnstrtApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(LnstrtApplication.class, args);
	}

	@Bean
	public CommandLineRunner loadData(LoanRepository repository) {
		return (args) -> {
			repository.save(new Loan(1000, 10, 5, 30));
			repository.save(new Loan(2000, 15, 10, 40));
			repository.save(new Loan(3000, 21, 12, 35));


			log.info("Loans found with findAll():");
			log.info("-------------------------------");
			for (Loan loan : repository.findAll()) {
				log.info(loan.toString());
			}

			Loan loan = repository.findById(1L).get();
			log.info("Loan found with findOne(1L):");
			log.info(loan.toString());

		};
	}

}
