package msc.shiftplanning;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jndi.JndiTemplate;

public class EntitlementHandling {
	
	
	
	@Autowired
	private static JdbcTemplate jdbcTemplateSimple;
	private static NamedParameterJdbcTemplate jdbcTemplate;
	  

	private static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	

	
	
	
	/* statement to query one given contract */
	protected String getQueryOneEntitlement() {
		return "Select a.user_id, a.yearNum, a.start_date, a.end_date, \n"
				+ "   a.entitlement, a.used_vacation_days, a.remaining_vacation \n"
				+ " from entitlements a \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.yearNum = :year \n"
				+ "   and a.start_date = :start_date";
	}

	
	/* read a the vacation account for a user in a given year an with a given start date */
	public VacationEntitlement readEntitlement(int userID, int year, Date startDate) {
		List<VacationEntitlement> listEntitlements;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read Entitlement of user-ID " +userID + "/" +  year + "/" +  dateFormat.format(startDate));
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
	    
	    listEntitlements = jdbcTemplate.query(
	    		getQueryOneEntitlement(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("year", year)
	    	      .addValue("start_date", startDate),
	            new EntitlementRowMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		for (VacationEntitlement elem : listEntitlements) {
			LoggerFactory.getLogger(UserImpl.class).info("Entitlmenet found");
			return elem;
		}

		return null;
	}

	
	
	
	
	
	
	/* statement to query entitlements of a year */
	protected String getQueryEntitlementsOfYear() {
		return "Select a.user_id, a.yearNum, a.start_date, a.end_date, \n"
				+ "   a.entitlement, a.used_vacation_days, a.remaining_vacation \n"
				+ " from entitlements a \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.yearNum = :year \n"
				+ "   and a.start_date > :start_date";
	}

	
	/* the vacation account for user in a year beging from a given start date */
	public List<VacationEntitlement> readEntitlementsOfYear(int userID, int year, Date startDate) {
		List<VacationEntitlement> listEntitlements;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read Entitlement of user-ID " +userID + "/" +  year + "/" +  dateFormat.format(startDate));
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
	    
	    listEntitlements = jdbcTemplate.query(
	    		getQueryEntitlementsOfYear(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("year", year)
	    	      .addValue("start_date", startDate),
	            new EntitlementRowMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		return listEntitlements;
	}

	
	
	
	
	/* statement to query for actual Entitlement */
	protected String getQueryActualEntitlement() {
		return "Select a.user_id, a.yearNum, a.start_date, a.end_date, \n"
				+ "   a.entitlement, a.used_vacation_days, a.remaining_vacation \n"
				+ " from entitlements a \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.yearNum = :year \n"
				+ "   and a.start_date <= :act_date \n"
				+ "   and a.end_date >= :act_date";
	}

	
	/*
	 * read the given entitlement from db
	 */
	public VacationEntitlement readActualEntitlementFromDb(int userID, int year, Date actualDate) {
		List<VacationEntitlement> listEntitlements;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read Entitlement of user-ID " +userID + "/" +  year + "/" +  dateFormat.format(actualDate));
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
	    
	    listEntitlements = jdbcTemplate.query(
	    		getQueryActualEntitlement(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("year", year)
	    	      .addValue("act_date", actualDate),
	            new EntitlementRowMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		for (VacationEntitlement elem : listEntitlements) {
			LoggerFactory.getLogger(UserImpl.class).info("Entitlmenet found");
			return elem;
		}

		return null;
	}

	


	/*
	 * read the given entitlement. If entitlement does not exist create all missing records from contract start
	 */
	public VacationEntitlement readActualEntitlement(int userID, int year, Date actualDate) {
		VacationEntitlement actEntitlement = readActualEntitlementFromDb(userID, year, actualDate);
		if (actEntitlement == null) {
			ContractHandling contractAdministration = new ContractHandling();
			Contract contract = contractAdministration.readActContract(userID, actualDate);
			for (int yearEntitlement = DateUtil.getYear(contract.startDate); yearEntitlement <= DateUtil.getYear(actualDate); yearEntitlement++) {
				buildEntitlementYear(userID, yearEntitlement);
			}
			actEntitlement = readActualEntitlementFromDb(userID, year, actualDate);
		}
				
		return actEntitlement;
	}

	
	
	/* statement to query for previous Entitlement */
	protected String getQueryPreviousEntitlement() {
		return "Select a.user_id, a.yearNum, a.start_date, a.end_date, \n"
				+ "   a.entitlement, a.used_vacation_days, a.remaining_vacation \n"
				+ " from entitlements a \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.end_date <= :end_date \n"
				+ " order by a.start_date";
	}

	
	
	/* read the previous vacation account from a given date */
	public VacationEntitlement readPriviousEntitlement(int userID, Date endDate) {
		List<VacationEntitlement> listEntitlements;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read Entitlement of user-ID " +userID + "/" +  dateFormat.format(endDate));
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
	    
	    listEntitlements = jdbcTemplate.query(
	    		getQueryPreviousEntitlement(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("end_date", endDate),
	            new EntitlementRowMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		for (VacationEntitlement elem : listEntitlements) {
			LoggerFactory.getLogger(UserImpl.class).info("Entitlmenet found");
			return elem;
		}

		return null;
	}

	

	
	
	
	
	
	
	
	
	/* statement to update the Entitlement */
	protected String getUpdateEntitlement() {
		return "Update entitlements a \n"
				+ " set a.end_date = :end_date, \n"
				+ "		a.entitlement = :entitlement, a.used_vacation_days = :used_vacation, \n"
				+ "     a.remaining_vacation = :remaining_vacation \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.yearNum = :year \n"
				+ "   and a.start_date = :start_date";
	}

	
	/* update the vacation account */
	public VacationEntitlement updateEntitlement(VacationEntitlement vacEntitlement) {
		LoggerFactory.getLogger(UserImpl.class).debug("Update Entitlement of user-ID " + vacEntitlement.getUserId() + "/" +  vacEntitlement.getYear() + "/" +  dateFormat.format(vacEntitlement.getStartDate()));
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
	    
	    int checkResult = jdbcTemplate.update(
	    		getUpdateEntitlement(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", vacEntitlement.getUserId())
	    	      .addValue("year", vacEntitlement.getYear())
	    	      .addValue("start_date", vacEntitlement.getStartDate())
	    	      .addValue("end_date", vacEntitlement.getEndDate())
	    	      .addValue("entitlement", vacEntitlement.getEntitlement())
	    	      .addValue("used_vacation", vacEntitlement.getVactionUsed())
	    	      .addValue("remaining_vacation", vacEntitlement.getRemainingVacation()));
	    LoggerFactory.getLogger(UserImpl.class).debug("Update okay!");
		if (checkResult > 0) {
			return vacEntitlement;
		}

		return null;
	}

	

	
	
	/* statement to update the Entitlement */
	protected String getUpdateEntitlementWithFollow() {
		return "Update entitlements a \n"
				+ " set a.remaining_vacation = a.remaining_vacation + :remaining_vacation \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.start_date > :start_date";
	}

	
	
	/* update all following´vacation accounts */ 
	public boolean updateEntitlemenRemainingtWithFollow(int userID, Date startDate, float remaining) {
		LoggerFactory.getLogger(UserImpl.class).debug("Update Entitlement of user-ID " + userID + "/" +  dateFormat.format(startDate));
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
	    
	    int checkResult = jdbcTemplate.update(
	    		getUpdateEntitlementWithFollow(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("start_date", startDate)
	    	      .addValue("remaining_vacation", remaining));
	    LoggerFactory.getLogger(UserImpl.class).debug("Update okay!");
		if (checkResult >= 0) {
			return true;
		}

		return false;
	}

	

	
	/* administrate the vacation entitlement by taken or reject vacatiuon */
	public boolean storeTakenVacation(int userID, Date startDate, float taken) {
		VacationEntitlement entitlement = readActualEntitlement(userID, DateUtil.getYear(startDate), startDate);
		if (entitlement == null) {
			buildEntitlementYear(userID, DateUtil.getYear(startDate));
			entitlement = readActualEntitlement(userID, DateUtil.getYear(startDate), startDate);
		}
		float newUsedVacation = entitlement.getVactionUsed() + taken;
		float remaing = entitlement.getEntitlement() - newUsedVacation;
		entitlement.setVactionUsed(newUsedVacation);
		this.updateEntitlement(entitlement);
		VacationEntitlement entitlementNextYear = this.readActualEntitlement(userID, DateUtil.getYear(startDate) + 1, DateUtil.addMonths(startDate, 12));
		if (entitlementNextYear != null) {
			float diff = remaing - entitlementNextYear.getRemainingVacation();
			this.updateEntitlemenRemainingtWithFollow(userID, DateUtil.addMonths(startDate, 12), diff);
		}
		return true;
	}
	
	
	
	

	
	
	/* statement to insert the Entitlement */
	protected String getInsertEntitlement() {
		return "Insert entitlements \n"
				+ "(user_id, yearNum, start_date, end_date, \n"
				+ "	entitlement, used_vacation_days, remaining_vacation) \n"
				+ "values \n"
				+ " (:user_id, :year, :start_date, :end_date, \n"
				+ "  :days_vacation, :used_vacation, :remaining_vacation)";
	}

	
	
	/* insert a new vacation account entry */
	public VacationEntitlement InsertEntitlement(VacationEntitlement vacEntitlement) {
		LoggerFactory.getLogger(UserImpl.class).debug("Insert Entitlement of user-ID " + vacEntitlement.getUserId() + "/" +  vacEntitlement.getYear() + "/" +  dateFormat.format(vacEntitlement.getStartDate()));
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
	    
	    int checkResult = jdbcTemplate.update(
	    		getInsertEntitlement(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", vacEntitlement.getUserId())
	    	      .addValue("year", vacEntitlement.getYear())
	    	      .addValue("start_date", vacEntitlement.getStartDate())
	    	      .addValue("end_date", vacEntitlement.getEndDate())
	    	      .addValue("days_vacation", vacEntitlement.getEntitlement())
	    	      .addValue("used_vacation", vacEntitlement.getVactionUsed())
	    	      .addValue("remaining_vacation", vacEntitlement.getRemainingVacation()));
	    LoggerFactory.getLogger(UserImpl.class).debug("Insert okay!");
		if (checkResult != 0) {
			return vacEntitlement;
		}

		return null;
	}


	
	

	
	
	/* statement to delete the Entitlement */
	protected String getDeleteEntitlement() {
		return "Delete from entitlements  \n"
				+ " where user_id = :user_id \n"
				+ "   and yearNum = :year \n"
				+ "   and start_date = :start_date";
	}

	
	
	/* delete a vacation account entry */
	public VacationEntitlement deleteEntitlement(VacationEntitlement vacEntitlement) {
		LoggerFactory.getLogger(UserImpl.class).debug("Delete Entitlement of user-ID " + vacEntitlement.getUserId() + "/" +  vacEntitlement.getYear() + "/" +  dateFormat.format(vacEntitlement.getStartDate()));
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
	    
	    int checkResult = jdbcTemplate.update(
	    		getDeleteEntitlement(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", vacEntitlement.getUserId())
	    	      .addValue("year", vacEntitlement.getYear())
	    	      .addValue("start_date", vacEntitlement.getStartDate()));
	    LoggerFactory.getLogger(UserImpl.class).debug("Delete okay!");
		if (checkResult >= 0) {
			return vacEntitlement;
		}

		return null;
	}

	
	
	
	
	
	
	
	
	/* statement to update the Entitlement of a year starting from a given date */
	protected String getDeleteAllEntitlement() {
		return "Delete from entitlements  \n"
				+ " where user_id = :user_id ";
	}

	
	/* delete all vacation account entries for deleting a user */
	public boolean deleteAllEntitlement(int userID) {
		LoggerFactory.getLogger(UserImpl.class).debug("Delete all entitlement of user-ID " + userID );
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
	    
	    int checkResult = jdbcTemplate.update(
	    		getDeleteAllEntitlement(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID) );
	    LoggerFactory.getLogger(UserImpl.class).debug("Delete all entitlement okay!");
	    if (checkResult >= 0) {
			return true;
		}
	    return false;
	}

	
	
	
	
	/* statement to update the Entitlement of a year starting from a given date */
	protected String getDeleteEntitlementYear() {
		return "Delete from entitlements  \n"
				+ " where user_id = :user_id \n"
				+ "   and yearNum = :year \n"
				+ "   and start_date >= :start_date";
	}

	
	
	/* deleting all vacation accounts in a year */
	public void deleteEntitlementYear(int userID, int year, Date startDate) {
		LoggerFactory.getLogger(UserImpl.class).debug("Delete Entitlement of user-ID " + userID + "/" +  year + "/" +  dateFormat.format(startDate));
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
	    
	    int checkResult = jdbcTemplate.update(
	    		getDeleteEntitlement(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("year", year)
	    	      .addValue("start_date", startDate));
	    LoggerFactory.getLogger(UserImpl.class).debug("Delete entitlement of year okay!");
	}


	
	
	
	
	
	
	/* internal function to find the entry with right start date in a list of vacation accounts */
	private VacationEntitlement findEntitelmentEntry(List<VacationEntitlement> listEntitlement, Date startDate) {
		VacationEntitlement foundEntitlement = null;
		
		for (VacationEntitlement entry : listEntitlement) {
			Date checkStartDate = entry.getStartDate();
			if (checkStartDate == startDate) {
				foundEntitlement = entry;
				return foundEntitlement;
			} else if (checkStartDate.after(startDate)) {
				listEntitlement.remove(entry);
			}
		}
		
		return foundEntitlement;
	}
	
	
	/* internal function to calculate the vacation accountb*/
	private float calculateEntitlement(Date startDate, Date endDate, int entitlementYear) {
		float entitlement = 0F;
		float monthlyEntitlement = (float)entitlementYear / (float)12;
		Date tempStartDate = startDate;
		Date tempEndDate = endDate;
		int reducedHalfMonth = 0;
		
		LoggerFactory.getLogger(UserImpl.class).debug("calculateEntitlementt: " + dateFormat.format(startDate) + "/" + dateFormat.format(endDate) + "  entitlementYear: " + entitlementYear );
		if (tempStartDate.toLocalDate().getDayOfMonth() != 1) {
			reducedHalfMonth++;
			tempStartDate = DateUtil.firstDayOfMonth(tempStartDate);
		}
		if (tempEndDate.toLocalDate().getDayOfMonth() != DateUtil.lastDayOfMonth(tempEndDate).toLocalDate().getDayOfMonth()) {
			reducedHalfMonth++;
			tempEndDate = DateUtil.lastDayOfMonth(tempEndDate);
		}
		LoggerFactory.getLogger(UserImpl.class).debug("calculateEntitlementt   MonthBetween: " + DateUtil.getMonthBetween(tempStartDate, tempEndDate) + "   reduce: " +  ((float)reducedHalfMonth/(float)2) + "  monthly entitlement: " + monthlyEntitlement );
		entitlement = (int) Math.ceil((DateUtil.getMonthBetween(tempStartDate, tempEndDate) + 1 - ((float)reducedHalfMonth/(float)2)) * monthlyEntitlement);
		return entitlement;
	}
	
	
	
	/* internal functio to calculate the vacation account an store it in the database */
	private void calculateAndStoreEntitlement(int userId, int year, Date startDate, 
			                                  List<Contract> contractList, VacationEntitlement elem) {
		VacationEntitlement workEntitlement = elem;
		if (workEntitlement == null) {
			this.deleteEntitlementYear(userId, year, startDate);
			workEntitlement = new VacationEntitlement();
			workEntitlement.setUserId(userId);
			workEntitlement.setYear(year);
			workEntitlement.setStartDate(startDate);
		}
		float entitlement = 0;
		for (Contract contract : contractList) {
			workEntitlement.setEndDate(contract.getEndDate()); 
			entitlement =+ calculateEntitlement(contract.getStartDate(), 
					                            contract.getEndDate(), 
					                            contract.getVacationentitlementYear());
		}
		entitlement = Math.round(entitlement);
		workEntitlement.setEntitlement((int) entitlement);
		
		VacationEntitlement previousEntitlement = this.readPriviousEntitlement(userId, DateUtil.addDays(startDate, -1));
		float remainingVacation = 0;
		if (previousEntitlement != null) {
			remainingVacation = previousEntitlement.getRemainingVacation() + previousEntitlement.getEntitlement() - previousEntitlement.getVactionUsed();
		}
		workEntitlement.setRemainingVacation(remainingVacation);
		
		DayEntryhandling dayEntry = new DayEntryhandling();
		float useVacation = dayEntry.readCountVacationOfPeriod(userId, startDate, workEntitlement.getEndDate());
		workEntitlement.setVactionUsed(useVacation);
		
		if (this.updateEntitlement(workEntitlement) == null) {
			this.InsertEntitlement(workEntitlement);
		}
	}
	
	
	
	/*
	 * create entitlement entries for a year 
	 */
	public void buildEntitlementYear(int user_id, int year) {
		Date startDate = DateUtil.getDate(year,1,1);
		Date endDate = DateUtil.getDate(year,12,31);
		
		ContractHandling contractAdmin = new ContractHandling();
		List<Contract> listContracts = contractAdmin.readContractsOfPeriod(user_id, startDate, endDate);
		List<VacationEntitlement> listEntitlement = readEntitlementsOfYear(user_id, year, startDate);
		
		int posContract = -1;
		int posEntitlement = 0;
		List<Contract> tempListContracts = new ArrayList<Contract>();
		VacationEntitlement actEntitlement = null;
		Date lastEndContract = startDate;
		for (Contract contract : listContracts) {
			LoggerFactory.getLogger(UserImpl.class).debug("buildEntitlementYear-contract: " + dateFormat.format(contract.getStartDate()) + "/" + dateFormat.format(contract.getEndDate()));
			posContract++;
			Date actStartDate = contract.getStartDate();
			if (lastEndContract.after(actStartDate)) {
				/* the actual entitlement period ends and the new period starts */
				if (!tempListContracts.isEmpty()) {
					/* the actual use entitlement period must be calculate and be stored */
					calculateAndStoreEntitlement(user_id, year, tempListContracts.get(0).getStartDate(), 
							                     tempListContracts, actEntitlement);
					actEntitlement = null;
					tempListContracts.clear();
				}
			}
			lastEndContract = contract.getEndDate();
			tempListContracts.add(contract);
			if (actEntitlement == null) {
				actEntitlement = findEntitelmentEntry(listEntitlement, actStartDate);
			}
		}
		if (!tempListContracts.isEmpty()) {
			calculateAndStoreEntitlement(user_id, year, tempListContracts.get(0).getStartDate(), 
                    tempListContracts, actEntitlement);
		}
			
		return;
	}
	
	
	
	
	
	
	
	
	

}
