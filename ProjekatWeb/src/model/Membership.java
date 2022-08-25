package model;

import java.time.LocalDate;

public class Membership {
	private String id;
	private String membershipType;
	private LocalDate payingDate;
	private LocalDate expireDate;
	private float price;
	private boolean active;
	private int dailyUsage;
	
	public Membership(String id, String membershipType, LocalDate payingDate, LocalDate expireDate, float price,
			boolean active, int dailyUsage) {
		super();
		this.id = id;
		this.membershipType = membershipType;
		this.payingDate = payingDate;
		this.expireDate = expireDate;
		this.price = price;
		this.active = active;
		this.dailyUsage = dailyUsage;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getMembershipType() {
		return membershipType;
	}

	public void setMembershipType(String membershipType) {
		this.membershipType = membershipType;
	}

	public LocalDate getPayingDate() {
		return payingDate;
	}

	public void setPayingDate(LocalDate payingDate) {
		this.payingDate = payingDate;
	}

	public LocalDate getExpireDate() {
		return expireDate;
	}

	public void setExpireDate(LocalDate expireDate) {
		this.expireDate = expireDate;
	}

	public float getPrice() {
		return price;
	}

	public void setPrice(float price) {
		this.price = price;
	}

	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	public int getDailyUsage() {
		return dailyUsage;
	}

	public void setDailyUsage(int dailyUsage) {
		this.dailyUsage = dailyUsage;
	}
	
	
}
