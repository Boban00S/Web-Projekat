package model;

public class CustomerType {

	private String name;
	private int discount;
	private int points;
	
	public CustomerType(String name, int discount, int points) {
		super();
		this.name = name;
		this.discount = discount;
		this.points = points;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public int getDiscount() {
		return discount;
	}
	public void setDiscount(int discount) {
		this.discount = discount;
	}
	public int getPoints() {
		return points;
	}
	public void setPoints(int points) {
		this.points = points;
	}
	
	
	
}
