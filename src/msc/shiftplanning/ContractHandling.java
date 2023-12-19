package msc.shiftplanning;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jndi.JndiTemplate;

public class ContractHandling {

	
	
	@Autowired
	private static JdbcTemplate jdbcTemplateSimple;
	private static NamedParameterJdbcTemplate jdbcTemplate;

	
	private static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy'T'HH:mm:ss");
	
	
	/* statement to query one given contract */
	protected String getQueryOneContract() {
		return "Select a.user_id, a.start_date, a.end_date, \n"
				+ "   a.days_vacation, a.hours_working, a.working_role_id \n"
				+ " from contracts a \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.start_date = :start_date";
	}

	
	
	public Contract readContract(int userID, Date startDate) {
		List<Contract> listContracts;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read Contract of user-ID " +userID + "/" +  dateFormat.format(startDate));
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
	    
		listContracts = jdbcTemplate.query(
	    		getQueryOneContract(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("start_date", startDate),
	            new ContractRowMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		for (Contract elem : listContracts) {
			LoggerFactory.getLogger(UserImpl.class).info("Contract found");
			return elem;
		}

		return null;
	}


	

	/* statement to query the actual valid contract for a given user, which is actual or in future valid */
	protected String getQueryActContract() {
		return "Select a.user_id, a.start_date, a.end_date, \n"
				+ "   a.days_vacation, a.hours_working, a.working_role_id \n"
				+ " from contracts a \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.start_date <= :start_date \n"
				+ "   and (a.end_date >= :start_date or a.end_date is null) \n"
				+ " order by a.start_date";
	}

	
	/* read the actual valid contract for a given day */
	public Contract readActContract(int userID, Date startDate) {
		List<Contract> listContracts;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read Contract of user-ID " +userID + "/" +  dateFormat.format(startDate));
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
	    
		listContracts = jdbcTemplate.query(
				getQueryActContract(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("start_date", startDate),
	            new ContractRowMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		for (Contract elem : listContracts) {
			LoggerFactory.getLogger(UserImpl.class).info("Actual contract found");
			return elem;
		}

		return null;
	}


	

	
	
	
	
	/* statement to query all contract valid in a period */
	protected String getQueryContracts() {
		return "Select a.user_id, greatest(a.start_date, :start_date) start_date, least(nvl(a.end_date, :end_date), nvl(:end_date,a.end_date)) end_date, \n"
				+ "   a.days_vacation, a.hours_working, a.working_role_id \n"
				+ " from contracts a \n"
				+ " where a.user_id = :user_id \n"
				+ "   and (a.end_date >= :start_date or a.end_date is null) \n"
				+ "   and (:end_date  is null or a.start_date <= :end_date) \n"
				+ " order by a.start_date";
	}


	/* read all valid contract for a given period. the contract must be valid in minimum for 1 day in the given period */
	public List<Contract> readContractsOfPeriod(int userID, Date startDate, Date endDate) {
		List<Contract> listContracts = null;
		
		if (endDate == null) {
			LoggerFactory.getLogger(UserImpl.class).debug("Read Contract of user-ID " +userID + "/" +  dateFormat.format(startDate) + "/" );
			
		} else {
			LoggerFactory.getLogger(UserImpl.class).debug("Read Contract of user-ID " +userID + "/" +  dateFormat.format(startDate) + "/" +  dateFormat.format(endDate));
		}
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
	    
		listContracts = jdbcTemplate.query(
				getQueryContracts(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("start_date", startDate)
	    	      .addValue("end_date", endDate),
	            new ContractRowMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Contracts found");

		return listContracts;
	}


	

	
	
	
	/* statement to insert a contract entry */
	protected String getInsertContract() {
		return "Insert into contracts \n"
				+ " (user_id, start_date, end_date,  \n"
				+ "  days_vacation, hours_working, working_role_id) \n"
				+ " values \n"
				+ " (:user_id, :start_date, :end_date, \n"
				+ "  :days_vacation, :hours_working, :working_role_id)";
	}
	
	
	/* insert a new contract to a user */
	public Contract add(Contract newContract) {
		if (newContract.getEndDate() == null) {
			LoggerFactory.getLogger(UserImpl.class).debug("New Contract: " + newContract.getUserId() + "/" +  dateFormat.format(newContract.getStartDate()) + "/");
		} else {
			LoggerFactory.getLogger(UserImpl.class).debug("New Contract: " + newContract.getUserId() + "/" +  dateFormat.format(newContract.getStartDate()) + "/" +  dateFormat.format(newContract.getEndDate()));
		}
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
		
		int checkResult = jdbcTemplate.update(getInsertContract(), new MapSqlParameterSource()
	    		  .addValue("user_id", newContract.getUserId())
	    	      .addValue("start_date", newContract.getStartDate())
	    	      .addValue("end_date", newContract.getEndDate())
	    	      .addValue("days_vacation", newContract.getVacationentitlementYear())
	    	      .addValue("hours_working", newContract.getHoursToWork())
	    	      .addValue("working_role_id", newContract.getWorkingRole()));
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Insert okay!");
		if (checkResult > 0) {
			EntitlementHandling EntitlementAdmin = new EntitlementHandling();
			
			int startYear =  DateUtil.getYear(newContract.getStartDate());
			int endYear = DateUtil.getYear(new Date(System.currentTimeMillis())) + 1;
			for (int year = startYear; year <= endYear; year++) {
				EntitlementAdmin.buildEntitlementYear(newContract.getUserId(), year);
			}
			return newContract;
		}

		return null;
	}

	
	
	
	
	
	/* statement to update a contract entry */
	protected String getUpdateContract() {
		return "update contracts a \n"
				+ " set a.user_id=:user_id, a.start_date=:start_date_new, a.end_date=:end_date,  \n"
				+ "     a.days_vacation=:days_vacation, a.hours_working=:hours_working, a.working_role_id=:working_role_id \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.start_date = :start_date_old";
	}
	

	/* change a contract for an user identified by the old start date of the contract */
	public Contract update(Contract newContract, Date startDateOld) {
		if (newContract.getEndDate() == null) {
			LoggerFactory.getLogger(UserImpl.class).debug("Update Contract: " + newContract.getUserId() + "/" +  dateFormat.format(newContract.getStartDate()) + "/");
		} else {
			LoggerFactory.getLogger(UserImpl.class).debug("Update Contract: " + newContract.getUserId() + "/" +  dateFormat.format(newContract.getStartDate()) + "/" +  dateFormat.format(newContract.getEndDate()));
		}
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
		
		int checkResult = jdbcTemplate.update(getUpdateContract(), new MapSqlParameterSource()
	    		  .addValue("user_id", newContract.getUserId())
	    	      .addValue("start_date_new", newContract.getStartDate())
	    	      .addValue("end_date", newContract.getEndDate())
	    	      .addValue("days_vacation", newContract.getVacationentitlementYear())
	    	      .addValue("hours_working", newContract.getHoursToWork())
	    	      .addValue("working_role_id", newContract.getWorkingRole())
	    	      .addValue("start_date_old", startDateOld));
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Update okay!");
		if (checkResult > 0) {
			return newContract;
		}

		return null;
	}

	
	
	
	
	
	
	/* statement to delete a contract entry */
	protected String getDeleteContract() {
		return "delete from contracts \n"
				+ " where user_id = :user_id \n"
				+ "   and start_date = :start_date";
	}
	
	
	
	/* delete a contract of an user identified by the start date of the contract */
	public Contract delete(Contract delContract) {
		if (delContract.getEndDate() == null) {
			LoggerFactory.getLogger(UserImpl.class).debug("Delete Contract: " + delContract.getUserId() + "/" +  dateFormat.format(delContract.getStartDate()) + "/");
		} else {
			LoggerFactory.getLogger(UserImpl.class).debug("Delete Contract: " + delContract.getUserId() + "/" +  dateFormat.format(delContract.getStartDate()) + "/" +  dateFormat.format(delContract.getEndDate()));
		}
	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
		
		int checkResult = jdbcTemplate.update(getDeleteContract(), new MapSqlParameterSource()
	    		  .addValue("user_id", delContract.getUserId())
	    	      .addValue("start_date", delContract.getStartDate()));
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Delete okay!");
		if (checkResult >= 0) {
			return delContract;
		}

		return null;
	}


	
	
	
	
	
	
	/* statement to delete all contract entry */
	protected String getDeleteAllContract() {
		return "delete from contracts \n"
				+ " where user_id = :user_id";
	}
	

	
	/* deletee all contract of an user */
	public boolean delete(int userID) {
		LoggerFactory.getLogger(UserImpl.class).debug("Delete all Contract: " + userID);

	    if (jdbcTemplate == null) {
	    	JndiTemplate jndiTemplate = new JndiTemplate();
	        DataSource dataSource = null;
			try {
				dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/jdbc/DienstPlanDB");
			} catch (NamingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
	    }
		
		int checkResult = jdbcTemplate.update(getDeleteAllContract(), new MapSqlParameterSource()
	    		  .addValue("user_id", userID));
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Delete okay!");
		if (checkResult >= 0) {
			return true;
		}

		return false;
	}

	
	
	
	
	/* check if an overlapping with existing contract for the user exists. If such overlapping
	 * contracts exists the they will be closed or deleted
	 */
	private boolean handleOverlaps(int userID, Contract newContract) {
		boolean ret = false;
		Date startDate = newContract.getStartDate();
		LoggerFactory.getLogger(UserImpl.class).debug("handleOverlaps - satrtDate :" + dateFormat.format(startDate) );
		Date endDate = newContract.getEndDate();
		List<Contract> listContracts = this.readContractsOfPeriod(userID, startDate, endDate);
		HoursAccountHandling adminHoursAccount = new HoursAccountHandling();
		
		if (listContracts == null || listContracts.isEmpty()) {
			LoggerFactory.getLogger(UserImpl.class).debug("handleOverlaps - no contract found");
		}
		
		Date tempStart;
		Date tempEnd = null;
		Date newCheckStartDate = startDate;
		int newWorkHours = newContract.getHoursToWork();
		int oldWorkHours = 0;
		for (Contract elem : listContracts) {
			tempStart = elem.getStartDate();
			tempEnd = elem.getEndDate();
			if (newCheckStartDate.compareTo(tempStart) < 0) {
				/* no overlaps exist for the period of newCheckStartDate and tempStart */
				adminHoursAccount.updatePlanedHoursEntry(userID, newCheckStartDate, DateUtil.addDays(tempStart, -1), newWorkHours);
			}
			
			oldWorkHours = elem.getHoursToWork();
			int status = newCheckStartDate.compareTo(tempStart);
			LoggerFactory.getLogger(UserImpl.class).debug("handleOverlaps - new contract :" + dateFormat.format(newCheckStartDate) + "   overlapping contract: " + 
                    dateFormat.format(tempStart) + "   check status: " + status);
			if (newCheckStartDate.compareTo(tempStart) > 0) {
				/* newCheckStartDate > tempStart */
				ret = false;
				/* overlapping contract start before new contract starts */
				if ((tempEnd == null) || (tempEnd != null && endDate != null && tempEnd.compareTo(endDate) < 0)) {
					/* overlapping contracts starts earlier and a new end date must be set */
					LoggerFactory.getLogger(UserImpl.class).debug("overlapping contracts starts earlier and a new end date must be set");
					elem.setEndDate(DateUtil.addDays(startDate, -1));
					this.update(elem, tempStart);
					if (tempEnd == null) {
						adminHoursAccount.updatePlanedHoursEntry(userID, startDate, endDate, newWorkHours - oldWorkHours);
						adminHoursAccount.updatePlanedHoursEntry(userID, DateUtil.addDays(endDate, 1), tempEnd, 0 - oldWorkHours);
					} else {
						adminHoursAccount.updatePlanedHoursEntry(userID, startDate, endDate, newWorkHours - oldWorkHours);
					}
					newCheckStartDate = DateUtil.addDays(tempEnd, 1);
					adminHoursAccount.updatePlanedHoursEntry(userID, newCheckStartDate, DateUtil.addDays(tempStart, -1), newWorkHours-oldWorkHours);
				} else {
					/* overlapping contract is greater then the new on. The overlapping contract must be splitted into 2 part:
					 *      1.: before new contract
					 *      2.: after new contract */
					LoggerFactory.getLogger(UserImpl.class).debug("overlapping contract totally averlaps new contract");
					Contract splitContract = new Contract();
					splitContract.setUserId(elem.getUserId());
					splitContract.setStartDate(DateUtil.addDays(elem.getEndDate(), 1));
					splitContract.setEndDate(elem.getEndDate());
					splitContract.setWorkingRole(elem.getWorkingRole());
					splitContract.setHoursToWork(elem.getHoursToWork());
					splitContract.setVacationentitlementYear(elem.getVacationentitlementYear());
					elem.setEndDate(DateUtil.addDays(startDate, -1));
					this.update(elem, tempStart);
					this.add(splitContract);
					newCheckStartDate = DateUtil.addDays(endDate, 1);
					adminHoursAccount.updatePlanedHoursEntry(userID, newCheckStartDate, endDate, newWorkHours-oldWorkHours);
				} 
			} else if (newCheckStartDate.compareTo(tempStart) < 0) {
				/* newCheckStartDate < tempStart */
				ret = false;
				/* overlapping contract start after the new contract starts */ 
				if ((tempEnd == null) || (tempEnd != null && endDate != null && endDate.compareTo(tempEnd) < 0)) {
					/* overlap contract end after the new one start date must be set */
					LoggerFactory.getLogger(UserImpl.class).debug("overlap contract end after the new one start date must be set");
					elem.setStartDate(DateUtil.addDays(endDate, 1));
					this.update(elem, tempStart);
					newCheckStartDate = DateUtil.addDays(endDate, 1);
					adminHoursAccount.updatePlanedHoursEntry(userID, newCheckStartDate, endDate, newWorkHours-oldWorkHours);
				} else {
					/* overlapping contract is totally in the period of the new contract and must be deleted */
					LoggerFactory.getLogger(UserImpl.class).debug("overlapping contract is totally in the period of the new contract and must be deleted");
					this.delete(elem);
					newCheckStartDate = DateUtil.addDays(tempEnd, 1);
					adminHoursAccount.updatePlanedHoursEntry(userID, newCheckStartDate, tempEnd, newWorkHours-oldWorkHours);
				}
				
			} else if (newCheckStartDate.compareTo(tempStart) == 0) {
				/* is itself. The end date can be different */
				ret = true;
				if ((tempEnd == null) || (tempEnd != null && endDate != null && endDate.compareTo(tempEnd) < 0)) {
					/* the contract is shortened */
					LoggerFactory.getLogger(UserImpl.class).debug("overlap contract end after the new one start date must be set");
					if (endDate != null) {
						elem.setStartDate(DateUtil.addDays(endDate, 1));
					}
					this.update(elem, tempStart);
					adminHoursAccount.updatePlanedHoursEntry(userID, newCheckStartDate, endDate, newWorkHours-oldWorkHours);
					if (endDate != null) {
						newCheckStartDate = DateUtil.addDays(endDate, 1);
					}
				} else {
					LoggerFactory.getLogger(UserImpl.class).debug("the new contract as a geater or equal end date as theoverlap contract");
					this.update(newContract, tempStart);
					adminHoursAccount.updatePlanedHoursEntry(userID, newCheckStartDate, tempEnd, newWorkHours-oldWorkHours);
					if (tempEnd != null) {
						adminHoursAccount.updatePlanedHoursEntry(userID, DateUtil.addDays(newCheckStartDate, 1), endDate, newWorkHours);
					}
				}
			}
		}
		if (tempEnd != null && newCheckStartDate.compareTo(tempEnd) <= 0) {
			adminHoursAccount.updatePlanedHoursEntry(userID, newCheckStartDate, tempEnd, newWorkHours);
		}
		return ret;
	}
	
	
	
	
	
	/* 
	 * add a new contract
	 * First check if overlapping contract exist. If exists, change begin or end date or delete such a contract.
	 * Change the hours account entries which are affected. Buildup the vacations account again.
	 */
	public Contract addContract(Contract newContract) {
		if (!handleOverlaps(newContract.userId, newContract)) {
			this.add(newContract);
		}
		Date actDate = DateUtil.getCurrentDate();
		EntitlementHandling adminEntitlement = new EntitlementHandling();
		LoggerFactory.getLogger(UserImpl.class).debug("addContract start with year " + DateUtil.getYear(newContract.getStartDate()));
		for (int i = DateUtil.getYear(newContract.getStartDate()); i <= DateUtil.getYear(DateUtil.addMonths(actDate, 1)); i++) {
			LoggerFactory.getLogger(UserImpl.class).debug("addContract: build entitlement: " + newContract.getUserId() + " for " + i);
			adminEntitlement.buildEntitlementYear(newContract.getUserId(), i);
		}
		return newContract;
	}
	

	
	
	/* 
	 * modify an existing contract
	 * First check if overlapping contract exist. If exists, change begin or end date or delete such a contract.
	 * Change the hours account entries which are affected. Buildup the vacations account again.
	 */
	public Contract modifyContract(Contract updContract) {
		Date startDate = updContract.getStartDate();
		LoggerFactory.getLogger(UserImpl.class).debug("modifyContract: start: " + dateFormat.format(startDate) );
		if (!handleOverlaps(updContract.userId, updContract)) {
			this.addContract(updContract);
		}
		Date actDate = DateUtil.getCurrentDate();
		EntitlementHandling adminEntitlement = new EntitlementHandling();
		for (int i = DateUtil.getYear(updContract.startDate); i <= DateUtil.getYear(DateUtil.addMonths(actDate, 1)); i++) {
			LoggerFactory.getLogger(UserImpl.class).debug("modifyContract: build entitlement: " + updContract.getUserId() + " for " + i);
			adminEntitlement.buildEntitlementYear(updContract.getUserId(), i);
		}
		return updContract;
	}
	

	
	/* 
	 * delete an existing contract
	 * First check if overlapping contract exist. If exists, change begin or end date or delete such a contract.
	 * Change the hours account entries which are affected. Buildup the vacations account again.
	 */
	public Contract delContract(Contract delContract) {
		delContract.setHoursToWork(0);
		delContract.setVacationentitlementYear(0);
		if (!handleOverlaps(delContract.userId, delContract)) {
			this.delete(delContract);
		}
		Date actDate = DateUtil.getCurrentDate();
		EntitlementHandling adminEntitlement = new EntitlementHandling();
		for (int i = DateUtil.getYear(delContract.startDate); i <= DateUtil.getYear(DateUtil.addMonths(actDate, 1)); i++) {
			adminEntitlement.buildEntitlementYear(delContract.getUserId(), i);
		}
		return delContract;
	}
	
	
	
}
