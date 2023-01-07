package model;

import com.google.gson.annotations.Expose;

public class Location {
	@Expose
	private String longitude;
	@Expose
	private String latitude;
	@Expose
	private String street;
	@Expose
	private String place;
	@Expose
	private String country;
	
	public Location(String longitude, String latitude, String street, String place, String country) {
		super();
		this.longitude = longitude;
		this.latitude = latitude;
		this.street = street;
		this.place = place;
		this.country = country;
	}

	public String getLongitude() {
		return longitude;
	}

	public void setLongitude(String longitude) {
		this.longitude = longitude;
	}

	public String getLatitude() {
		return latitude;
	}

	public void setLatitude(String latitude) {
		this.latitude = latitude;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getPlace() {
		return place;
	}

	public void setPlace(String place) {
		this.place = place;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}
	
	
}
