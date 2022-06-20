package model;



public class Customer extends User{

	private CustomerType type;
	
	public Customer(CustomerType type) {
		super();
		this.type = type;
	}

	public CustomerType getType() {
		return type;
	}

	public void setType(CustomerType type) {
		this.type = type;
	}
	
}
