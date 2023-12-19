package msc.shiftplanning;

import java.sql.Date;

public class VacationEntitlement {

	protected int userId;
	protected int year;
	protected Date startDate;
	protected Date endDate;
	protected int entitlement;
	protected float vactionUsed;
	protected float remainingVacation;
	
	
	
	
	public int getUserId() {
		return userId;
	}
	
	
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	public int getYear() {
		return year;
	}
	
	
	
	public void setYear(int year) {
		this.year = year;
	}
	
	
	
	public Date getStartDate() {
		return startDate;
	}
	
	
	
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	
	
	
	public int getEntitlement() {
		return entitlement;
	}
	
	
	
	
	public void setEntitlement(int entitlement) {
		this.entitlement = entitlement;
	}
	
	
	
	
	public float getVactionUsed() {
		return vactionUsed;
	}
	
	
	
	
	public void setVactionUsed(float vactionUsed) {
		this.vactionUsed = vactionUsed;
	}



	public Date getEndDate() {
		return endDate;
	}



	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}



	public float getRemainingVacation() {
		return remainingVacation;
	}



	public void setRemainingVacation(float remainingVacation) {
		this.remainingVacation = remainingVacation;
	}
	

	
	
}
