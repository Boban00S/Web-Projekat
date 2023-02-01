package model;

import java.time.LocalDate;

public class Membership {
	private Integer id;
	private String code;
	private String membershipType;
	private LocalDate payingDate;
	private LocalDate expireDate;
	private float price;
	private boolean active;
	private int dailyUsage;

	private String description;

	public Membership(String code, String membershipType, float price,
					  int dailyUsage) {
		super();
		this.code = code;
		this.membershipType = membershipType;
		this.price = price;
		this.dailyUsage = dailyUsage;
	}

	public Membership(String code, String membershipType, LocalDate payingDate, LocalDate expireDate, float price,
			boolean active, int dailyUsage) {
		super();
		this.code = code;
		this.membershipType = membershipType;
		this.payingDate = payingDate;
		this.expireDate = expireDate;
		this.price = price;
		this.active = active;
		this.dailyUsage = dailyUsage;
	}

	public String getCode() {
		return code;
	}

	public void setCode(String code) {
		this.code = code;
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

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
}
