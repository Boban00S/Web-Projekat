package model;

import com.google.gson.annotations.Expose;

public class SportsObjectType {
	@Expose
	private String name;

	public SportsObjectType() {
		
	}
	
	
	public SportsObjectType(String name) {
		super();
		this.name = name;
	}


	public String getName() {
		return name;
	}


	public void setName(String name) {
		this.name = name;
	}
	
	
	
	

}


