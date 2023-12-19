package msc.shiftplanning;

import java.sql.Date;

public class MonthTimeAccount {
	
	
	private int userID;
	private Date month;
	private float planedHours;
	private float actualHours;
	
	
	
	
	public int getUserID() {
		return userID;
	}
	
	
	
	
	public void setUserID(int userID) {
		this.userID = userID;
	}
	
	
	
	public Date getMonth() {
		return month;
	}
	
	
	
	public void setMonth(Date month) {
		this.month = month;
	}
	
	
	
	
	public float getPlanedHours() {
		return planedHours;
	}
	
	
	
	
	public void setPlanedHours(float planedHours) {
		this.planedHours = planedHours;
	}
	
	
	
	
	public float getActualHours() {
		return actualHours;
	}
	
	
	
	
	public void setActualHours(float actualHours) {
		this.actualHours = actualHours;
	}
	
	
	
	

}
