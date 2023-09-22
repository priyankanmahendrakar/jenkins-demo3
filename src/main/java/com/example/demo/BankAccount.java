package com.example.demo;

import java.util.Date;

import org.hibernate.annotations.CreationTimestamp;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Entity
@Table(name = "accountdetails")
public class BankAccount {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	// Owner name should not be blank and should be within 50 characters
	@NotBlank(message = "ownerName is mandatory")
	@Size(max = 50)
	private String ownerName;
	// city should not be blank
	@NotBlank(message = "city is mandatory")
	private String city;
	// State should not be blank
	@NotBlank(message = "state is mandatory")
	private String state;
	private Integer pin;
	private float balance;
	private float overdraftBalance;
	private ACCOUNTTYPE accountType;
	// Automatically generated timestamp for account creation
	@CreationTimestamp
	private Date createdDate;
	private ACCOUNTSTATUS status;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public Integer getPin() {
		return pin;
	}

	public void setPin(Integer pin) {
		// Validate pin to have exactly 6 digits
		String pinStr = String.valueOf(pin);
		if (pinStr.length() == 6) {
			this.pin = pin;
		} else {
			throw new IllegalArgumentException("Pin must be exactly 6 digits.");
		}
	}

	public float getBalance() {
		return balance;
	}

	public void setBalance(float balance) {
		this.balance = Math.max(balance, 0);
	}

	public float getOverdraftBalance() {
		return overdraftBalance;
	}

	public void setOverdraftBalance(float overdraftBalance) {
		this.overdraftBalance = overdraftBalance;
	}

	public ACCOUNTTYPE getAccountType() {
		return accountType;
	}

	public void setAccountType(ACCOUNTTYPE accountType) {
		this.accountType = accountType;
	}

	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}

	public ACCOUNTSTATUS getStatus() {
		return status;
	}

	public void setStatus(ACCOUNTSTATUS status) {
		this.status = status;
	}
}
