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

public class HoursAccountHandling {

	
	@Autowired
	private static JdbcTemplate jdbcTemplateSimple;
	private static NamedParameterJdbcTemplate jdbcTemplate;
	  

	private static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	

	
	
	
	/* statement to query one given account of a given user and month */
	protected String getQueryOneAccount() {
		return "Select a.user_id, a.month, \n"
				+ "    a.planed_hours_account, a.actual_hours_account \n"
				+ " from month_time_account a \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.month = :month";
	}

	
	/*
	 * read one entry for a given user and month
	 */
	public MonthTimeAccount readAccount(int userID, Date month) {
		List<MonthTimeAccount> listAccounts;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read HoursAccount of user-ID " +userID + "/" + dateFormat.format(month));
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
	    
	    listAccounts = jdbcTemplate.query(
	    		getQueryOneAccount(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("month", month),
	            new MonthTimeAccountMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		for (MonthTimeAccount elem : listAccounts) {
			LoggerFactory.getLogger(UserImpl.class).info("Hours account found");
			return elem;
		}

		return null;
	}

	

	
	
	
	
	/* statement to query one given account of a given user and month */
	protected String getQueryPreviousAccount() {
		return "Select a.user_id, a.month, \n"
				+ "    a.planed_hours_account, a.actual_hours_account \n"
				+ " from month_time_account a \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.month < :month \n"
				+ " order by a.month desc";
	}

	
	/*
	 * read a previous entry. Looks for next entry existing in the pass.
	 */
	public MonthTimeAccount readPreviousAccount(int userID, Date month) {
		List<MonthTimeAccount> listAccounts;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read previous HoursAccount of user-ID " +userID + "/" + dateFormat.format(month));
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
	    
	    listAccounts = jdbcTemplate.query(
	    		getQueryPreviousAccount(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("month", month),
	            new MonthTimeAccountMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		for (MonthTimeAccount elem : listAccounts) {
			LoggerFactory.getLogger(UserImpl.class).info("Previous account found");
			return elem;
		}

		return null;
	}

	

	
	
	

	
	
	
	
	/* statement to query accounts of a given user and given period */
	protected String getQueryAccountOfPeriod() {
		return "Select a.user_id, a.month, \n"
				+ "    a.planed_hours_account, a.actual_hours_account \n"
				+ " from month_time_account a \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.month >= :month_start \n"
				+ "   and (a.month <= :month_end or :month_end is null) \n"
				+ " order by a.month desc";
	}

	
	/*
	 * read all entries for a user and given period
	 */
	public List<MonthTimeAccount> readAccountOfPeriod(int userID, Date monthStart, Date monthEnd) {
		List<MonthTimeAccount> listAccounts;
		
		if (monthEnd == null) {
			LoggerFactory.getLogger(UserImpl.class).debug("Read Accounts of user-ID " +userID + "/" + dateFormat.format(monthStart) + "/" );
		} else {
			LoggerFactory.getLogger(UserImpl.class).debug("Read Accounts of user-ID " +userID + "/" + dateFormat.format(monthStart) + "/" + dateFormat.format(monthEnd));
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
	    
	    listAccounts = jdbcTemplate.query(
	    		getQueryAccountOfPeriod(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("month_start", monthStart)
	    	      .addValue("month_end", monthEnd),
	            new MonthTimeAccountMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		return listAccounts;
	}

	

	
	

	
	
	
	
	
	/* statement to query one given account of a given user and month */
	protected String getQueryAccountOf12Month() {
		return "Select a.user_id, a.month, \n"
				+ "    a.planed_hours_account, a.actual_hours_account \n"
				+ " from month_time_account a \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.month >= :month_start \n"
				+ "   and a.month <= :month_end";
	}

	
	
	/*
	 * read all all entries of a user for a rooling 12 month period
	 */
	public List<MonthTimeAccount> readAccounts12Months(int userID, Date month) {
		List<MonthTimeAccount> listAccounts;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read HoursAccount (12 months) of user-ID " +userID + "/" + dateFormat.format(month));
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
	    
	    listAccounts = jdbcTemplate.query(
	    		getQueryAccountOf12Month(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("month_start", month)
	    	      .addValue("month_start", DateUtil.getDate(month.toLocalDate().getYear() + 1, 
	    	    		                                    month.toLocalDate().getMonthValue(), 
	    	    		                                    month.toLocalDate().getDayOfMonth())),
	            new MonthTimeAccountMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");

		return listAccounts;
	}

	

	

	
	
	/* statement to update the Account */
	protected String getUpdateAccount() {
		return "Update month_time_account a \n"
				+ " set a.planed_hours_account = :planed_hours_account, \n"
				+ "		a.actual_hours_account = :actual_hours_account \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.month = :month";
	}

	
	
	public MonthTimeAccount updateAccount(MonthTimeAccount account) {
		
		LoggerFactory.getLogger(UserImpl.class).debug("Update account of user-ID " + account.getUserID() + "/" +  dateFormat.format(account.getMonth()));
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
	    		getUpdateAccount(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", account.getUserID())
	    	      .addValue("month", account.getMonth())
	    	      .addValue("planed_hours_account", account.getPlanedHours())
	    	      .addValue("actual_hours_account", account.getActualHours()));
	    LoggerFactory.getLogger(UserImpl.class).debug("Update okay!");
		if (checkResult != 0) {
			return account;
		}

		return null;
	}

	

	

	

	
	
	/* statement to update the Accounts from a given month up */
	protected String getUpdateAccounts() {
		return "Update month_time_account a \n"
				+ " set a.planed_hours_account = a.planed_hours_account + :planed_hours_account, \n"
				+ "		a.actual_hours_account = a.actual_hours_account + :actual_hours_account \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.month >= :month";
	}

	
	
	
	/* update the hours account of a given user and month */
	public boolean updateAccounts(int userID, Date month, float planedHoursDiff, float actualHoursDiff) {
		
		LoggerFactory.getLogger(UserImpl.class).debug("Update account of user-ID " + userID + "/" +  dateFormat.format(month));
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
	    		getUpdateAccounts(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("month", month)
	    	      .addValue("planed_hours_account", planedHoursDiff)
	    	      .addValue("actual_hours_account", actualHoursDiff));
	    LoggerFactory.getLogger(UserImpl.class).debug("Update okay!");
		if (checkResult > 0) {
			return true;
		}

		return false;
	}

	

	
	
	
	

	
	
	/* statement to insert the new hour account entry */
	protected String getInsertAccount() {
		return "Insert month_time_account \n"
				+ "(user_id, month, \n"
				+ "	planed_hours_account, actual_hours_account) \n"
				+ "values \n"
				+ " (:user_id, :month, \n"
				+ "  :planed_hours_account, :actual_hours_account)";
	}

	
	
	
	/* insert a new hours account entry */
	public MonthTimeAccount InsertAccount(MonthTimeAccount account) {
		
		LoggerFactory.getLogger(UserImpl.class).debug("Insert hour account of user-ID " + account.getUserID() + "/" +  dateFormat.format(account.getMonth()));
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
	    		getInsertAccount(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", account.getUserID())
	    	      .addValue("month", account.getMonth())
	    	      .addValue("planed_hours_account", account.getPlanedHours())
	    	      .addValue("actual_hours_account", account.getActualHours()));
	    LoggerFactory.getLogger(UserImpl.class).debug("Insert okay!");
		if (checkResult > 0) {
			return account;
		}

		return null;
	}


	

	
	
	

	
	
	/* statement to delete all hours account */
	protected String getDeleteAllAccount() {
		return "Delete from month_time_account \n"
				+ " where user_id = :user_id ";
	}

	
	
	
	/* delete all hour account entries for a given user when the user should be deleted */
	public boolean deleteAllAccount(int userID) {
		LoggerFactory.getLogger(UserImpl.class).debug("Delete hours account of user-ID " + userID);
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
	    		getDeleteAllAccount(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID));
	    LoggerFactory.getLogger(UserImpl.class).debug("Delete okay!");
		if (checkResult >= 0) {
			return true;
		}

		return false;
	}

	


	
	
	/* statement to delete the hours account */
	protected String getDeleteAccount() {
		return "Delete from month_time_account \n"
				+ " where user_id = :user_id \n"
				+ "   and month = :month";
	}

	
	
	/* delete a given hour account entry */
	public MonthTimeAccount deleteAccount(MonthTimeAccount account) {
		LoggerFactory.getLogger(UserImpl.class).debug("Delete hours account of user-ID " + account.getUserID() + "/" +  dateFormat.format(account.getMonth()));
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
	    		getDeleteAccount(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", account.getUserID())
	    	      .addValue("month", account.getMonth()));
	    LoggerFactory.getLogger(UserImpl.class).debug("Delete okay!");
		if (checkResult >= 0) {
			return account;
		}

		return null;
	}

	
	
	
	/*
	 * create new month records and return the new create record
	 */
	protected MonthTimeAccount createMonthAccount(int userID, Date month, float startPlanHours, float startActualHours) {
		MonthTimeAccount newRecord = new MonthTimeAccount();
		float workingPlanedHoursOfMonth = DateUtil.workingHours(userID, DateUtil.firstDayOfMonth(month), DateUtil.lastDayOfMonth(month)) * -1;
		newRecord.setUserID(userID);
		newRecord.setMonth(month);
		newRecord.setPlanedHours(startPlanHours + workingPlanedHoursOfMonth);
		newRecord.setActualHours(startActualHours + workingPlanedHoursOfMonth);
		if (this.InsertAccount(newRecord) == null) {
			return null;
		}
		return newRecord;
	}
	
	
	

	
	/*
	 * store the actual state of a hours account of an user.
	 * If no account exists, then it will be create and initialized with values of a found previous entry.
	 * Entries more in the future will be also update with new given differences.
	 */
	public boolean saveAccount(int userID, Date month, float planedHoursDiff, float actualHoursDiff) {
		LoggerFactory.getLogger(UserImpl.class).debug("Save hours account of user-ID " + userID + "/" +  dateFormat.format(month));

		
		float testPlanedHours = 0;
		float testActualHours = 0;
		float hoursPlanedFromPriviousPeriod = 0;
		float hoursActualFromPriviousPeriod = 0;
		float workingPlanedHoursOfMonth = 0;
		
		MonthTimeAccount testAccount = this.readAccount(userID, month);
		if (testAccount == null) {
			/* no Account for the month exist*/
			MonthTimeAccount previousAccount = this.readPreviousAccount(userID, month);
			Date end = DateUtil.addDays(month, -1);
			Date start;
			if (previousAccount != null) {
				/* a previous entry found */
				hoursPlanedFromPriviousPeriod = previousAccount.getPlanedHours();
				hoursActualFromPriviousPeriod = previousAccount.getActualHours();
				Date testMonth = DateUtil.addMonths(previousAccount.getMonth(),1);
				while (testMonth.before(DateUtil.addDays(month, -1))) {
					MonthTimeAccount newMonthRecord = createMonthAccount(userID, testMonth, hoursPlanedFromPriviousPeriod, hoursActualFromPriviousPeriod);
					hoursPlanedFromPriviousPeriod = newMonthRecord.getPlanedHours();
					hoursActualFromPriviousPeriod = newMonthRecord.getActualHours();
					testMonth = DateUtil.addMonths(testMonth, 1);
				}
				start = DateUtil.addMonths(previousAccount.getMonth(), 1);
				workingPlanedHoursOfMonth = DateUtil.workingHours(userID, start, end);
			} else {
				/* not previous found and start from start of contract */ 
				ContractHandling contractAdministration = new ContractHandling();
				Contract contract = contractAdministration.readActContract(userID, month);
				start = contract.getStartDate();
				workingPlanedHoursOfMonth = DateUtil.workingHours(userID, start, DateUtil.addMonths(month,-1)) * -1;
			}
			workingPlanedHoursOfMonth = DateUtil.workingHours(userID, DateUtil.firstDayOfMonth(month), DateUtil.lastDayOfMonth(month)) * -1;
			MonthTimeAccount account = new MonthTimeAccount();
			account.setUserID(userID);
			account.setMonth(month);
			account.setPlanedHours(hoursPlanedFromPriviousPeriod + workingPlanedHoursOfMonth);
			account.setActualHours(hoursActualFromPriviousPeriod + workingPlanedHoursOfMonth);
			if (this.InsertAccount(account) == null) {
				return false;
			}
		}
		/* change all accounts which based on */	
		return this.updateAccounts(userID, month, 
				                   planedHoursDiff, 
				                   actualHoursDiff);
	}
	
	
	
	
	
	/*
	 * update the plan hours in the account by changing the work hours in the contract
	 */
	public boolean updatePlanedHoursEntry(int userID, Date startContract, Date endContract, int diffHours) {
		List<MonthTimeAccount> listAccounts;
		Date endDateContract;
		
		if (endContract == null) {
			endDateContract =DateUtil.addMonths(DateUtil.getCurrentDate(), 12);
		} else {
			endDateContract = endContract;
		}
		if (endContract == null) {
			listAccounts = this.readAccountOfPeriod(userID, DateUtil.firstDayOfMonth(startContract), endDateContract);
		} else {
			listAccounts = this.readAccountOfPeriod(userID, DateUtil.firstDayOfMonth(startContract), DateUtil.firstDayOfMonth(endDateContract));
		}
		int workingDays = 0;
		for (Date act = DateUtil.firstDayOfMonth(startContract); act.compareTo(DateUtil.firstDayOfMonth(endDateContract)) < 0; act = DateUtil.addMonths(act, 1)) {
			Date start = act;
			if (start.compareTo(startContract) < 0) {
				/* we must start with the beginning of the contract and not with the months because the contract start later in the month */
				start = startContract;
			}
			Date end = DateUtil.lastDayOfMonth(act);
			if (end.compareTo(endDateContract) < 0) {
				/* we must end with the ending date of the contract and not with the last day of the month because the contract end before end of month */
				end = endDateContract;
			}
			MonthTimeAccount account = this.readAccount(userID, act);
			workingDays += DateUtil.getNumberWorkingDays(start, end);
			if (account != null) {
				account.setPlanedHours(account.getPlanedHours() + (workingDays * diffHours));
				account.setActualHours(account.getActualHours() + (workingDays * diffHours));
				this.updateAccount(account);
			}
		}
		
		return true;
	}
	
	

	
}
