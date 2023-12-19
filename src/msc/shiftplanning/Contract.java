package msc.shiftplanning;

import java.sql.Date;

public class Contract {

	
	protected int userId;
	protected Date startDate;
	protected Date endDate;
	protected int vacationentitlementYear;
	protected int hoursToWork;
	protected int workingRole;
	
	
	
	
	public int getUserId() {
		return userId;
	}
	
	
	
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
	
	
	public Date getStartDate() {
		return DateUtil.truncateDate(startDate);
	}
	
	
	
	
	public void setStartDate(Date startDate) {
		this.startDate = startDate;
	}
	
	
	
	public Date getEndDate() {
		return DateUtil.truncateDate(endDate);
	}
	
	
	
	
	public void setEndDate(Date endDate) {
		this.endDate = endDate;
	}
	
	
	
	public int getVacationentitlementYear() {
		return vacationentitlementYear;
	}
	
	
	
	public void setVacationentitlementYear(int vacationentitlementYear) {
		this.vacationentitlementYear = vacationentitlementYear;
	}
	
	
	
	public int getHoursToWork() {
		return hoursToWork;
	}
	
	
	
	
	public void setHoursToWork(int hoursToWork) {
		this.hoursToWork = hoursToWork;
	}
	
	
	
	public int getWorkingRole() {
		return workingRole;
	}
	
	
	
	
	public void setWorkingRole(int workingRole) {
		this.workingRole = workingRole;
	}
	
	
	
	
}
