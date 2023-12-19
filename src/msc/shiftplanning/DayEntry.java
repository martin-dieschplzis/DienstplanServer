package msc.shiftplanning;

import java.sql.Date;
import java.sql.Time;

public class DayEntry {
	
	
	protected int userId;
	protected Date date;
	protected int planedActivity;
	protected float planedHours;
	protected int actualActivity;
	protected Time startTime;
	protected Time endTime;
	protected float actualHours;
	
	
	
	public int getUserId() {
		return userId;
	}
	
	
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	public Date getDate() {
		return date;
	}
	
	
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	public int getPlanedActivity() {
		return planedActivity;
	}
	
	
	
	public void setPlanedActivity(int planedActivity) {
		this.planedActivity = planedActivity;
	}
	
	
	
	public float getPlanedHours() {
		return planedHours;
	}
	
	
	
	public void setPlanedHours(float planedHours) {
		this.planedHours = planedHours;
	}
	
	
	
	public Time getStartTime() {
		return startTime;
	}
	
	
	
	
	public void setStartTime(Time startTime) {
		this.startTime = startTime;
	}
	
	
	
	public Time getEndTime() {
		return endTime;
	}
	
	
	
	public void setEndTime(Time endTime) {
		this.endTime = endTime;
	}
	
	
	
	public float getActualHours() {
		return actualHours;
	}
	
	
	
	public void setActualHours(float actualHours) {
		this.actualHours = actualHours;
	}



	public int getActualActivity() {
		return actualActivity;
	}



	public void setActualActivity(int actualActivity) {
		this.actualActivity = actualActivity;
	}
	
	
	
	
	

}
