package model;

import java.time.LocalDate;

public class TrainingHistory {
	private LocalDate startDateAndTime;
	private Customer customer;
	private Trainer trainer;
	private Training training;
	
	public TrainingHistory(LocalDate startDateAndTime, Customer customer, Trainer trainer, Training training) {
		super();
		this.startDateAndTime = startDateAndTime;
		this.customer = customer;
		this.trainer = trainer;
		this.training = training;
	}
	public LocalDate getStartDateAndTime() {
		return startDateAndTime;
	}
	public void setStartDateAndTime(LocalDate startDateAndTime) {
		this.startDateAndTime = startDateAndTime;
	}
	public Customer getCustomer() {
		return customer;
	}
	public void setCustomer(Customer customer) {
		this.customer = customer;
	}
	public Trainer getTrainer() {
		return trainer;
	}
	public void setTrainer(Trainer trainer) {
		this.trainer = trainer;
	}
	public Training getTraining() {
		return training;
	}
	public void setTraining(Training training) {
		this.training = training;
	}
	
}
