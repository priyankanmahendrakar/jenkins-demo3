package com.example.demo;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/bank-accounts")
public class BankAccountController {

	@Autowired
	private BankAccountRepository bankAccountRepository;

	// Create a new bank account
	@PostMapping
	public ResponseEntity<BankAccount> createBankAccount(@Valid @RequestBody BankAccount bankAccount) {
		try {
			BankAccount createdBankAccount = bankAccountRepository.save(bankAccount);
			return ResponseEntity.status(HttpStatus.CREATED).body(createdBankAccount);
		} catch (Exception e) {
			return ResponseEntity.badRequest().build();
		}
	}

	// Retrieve a bank account by its ID
	@GetMapping("/{id}")
	public ResponseEntity<BankAccount> getBankAccountById(@PathVariable int id) {
		Optional<BankAccount> bankAccount = bankAccountRepository.findById(id);
		return bankAccount.map(value -> ResponseEntity.ok().body(value))
				.orElseGet(() -> ResponseEntity.notFound().build());
	}

	// Retrieve a list of all bank accounts
	@GetMapping
	public ResponseEntity<List<BankAccount>> getAllBankAccounts() {
		List<BankAccount> bankAccounts = (List<BankAccount>) bankAccountRepository.findAll();
		return ResponseEntity.ok().body(bankAccounts);
	}

	// Deposit money into a bank account
	@PostMapping("/{id}/deposit/{amount}")
	public ResponseEntity<BankAccount> depositMoney(@PathVariable int id, @PathVariable double amount) {
		// Check if the deposit amount is non-negative
		if (amount < 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}
		Optional<BankAccount> optionalAccount = bankAccountRepository.findById(id);
		if (optionalAccount.isPresent()) {
			BankAccount bankAccount = optionalAccount.get();
			// Update the balance after a valid deposit
			bankAccount.setBalance((float) (bankAccount.getBalance() + amount));
			BankAccount updatedAccount = bankAccountRepository.save(bankAccount);
			return ResponseEntity.ok().body(updatedAccount);
		} else {
			return ResponseEntity.notFound().build();
		}
	}
	
	// Withdraw money from a bank account
	@PostMapping("/{id}/withdraw/{amount}")
	public ResponseEntity<BankAccount> withdrawMoney(@PathVariable int id, @PathVariable double amount) {
		// Check if the withdrawal amount is non-negative
		if (amount < 0) {
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
		}

		Optional<BankAccount> optionalAccount = bankAccountRepository.findById(id);
		if (optionalAccount.isPresent()) {
			BankAccount bankAccount = optionalAccount.get();//code duplication try to call a function and then add it
			// Check if the withdrawal amount exceeds the balance
			if (amount > bankAccount.getBalance()) {
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
			}
			// Update the balance after a valid withdrawal
			bankAccount.setBalance((float) (bankAccount.getBalance() - amount));
			BankAccount updatedAccount = bankAccountRepository.save(bankAccount);
			return ResponseEntity.ok().body(updatedAccount);
		} else {
			return ResponseEntity.notFound().build();
		}
	}

}
