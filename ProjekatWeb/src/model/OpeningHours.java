package model;

import com.google.gson.annotations.Expose;

import java.util.Calendar;

public class OpeningHours {
	@Expose
	private Integer fromHours;
	@Expose
	private Integer fromMinutes;
	@Expose
	private Integer toHours;
	@Expose
	private Integer toMinutes;
	
	public OpeningHours(Integer fromHours, Integer fromMinutes, Integer toHours, Integer toMinutes) {
		super();
		this.fromHours = fromHours;
		this.fromMinutes = fromMinutes;
		this.toHours = toHours;
		this.toMinutes = toMinutes;
	}




	public Integer getFromHours() {
		return fromHours;
	}




	public void setFromHours(Integer fromHours) {
		this.fromHours = fromHours;
	}




	public Integer getFromMinutes() {
		return fromMinutes;
	}




	public void setFromMinutes(Integer fromMinutes) {
		this.fromMinutes = fromMinutes;
	}




	public Integer getToHours() {
		return toHours;
	}




	public void setToHours(Integer toHours) {
		this.toHours = toHours;
	}




	public Integer getToMinutes() {
		return toMinutes;
	}




	public void setToMinutes(Integer toMinutes) {
		this.toMinutes = toMinutes;
	}




	@SuppressWarnings("deprecation")
	public boolean isOpen() {
		Calendar currentTime = Calendar.getInstance();
		return currentTime.getTime().getHours() >  fromHours &&
				currentTime.getTime().getHours() < toHours;
	}
	
	
}
