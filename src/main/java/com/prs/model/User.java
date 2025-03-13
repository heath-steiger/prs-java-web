package com.prs.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	private String username;
	private String password;
	private String firstName;
	private String lastName;
	private String PhoneNumber;
	private String Email;
	private boolean Reviewer;
	private boolean Admin;
	
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}	
		public String getUsername() {
			return username;
		}
		public void setUsername(String username) {
			this.username = username;
		}
	
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getFirstName() {
		return firstName;
	}
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	public String getLastName() {
		return lastName;
	}
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	public String getPhoneNumber() {
		return PhoneNumber;
	}
	public void setPhoneNumber(String phoneNumber) {
		PhoneNumber = phoneNumber;
	}
	public String getEmail() {
		return Email;
	}
	public void setEmail(String email) {
		Email = email;
	}
	public boolean isReviewer() {
		return Reviewer;
	}
	public void setReviewer(boolean reviewer) {
		Reviewer = reviewer;
	}
	public boolean isAdmin() {
		return Admin;
	}
	public void setAdmin(boolean admin) {
		Admin = admin;
	}
	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", password=" + password + ", firstName=" + firstName
				+ ", lastName=" + lastName + ", gender=" +  ", PhoneNumber=" + PhoneNumber + ", Email=" + Email
				+ ", Reviewer=" + Reviewer + ", Admin=" + Admin + "]";
	}
}
