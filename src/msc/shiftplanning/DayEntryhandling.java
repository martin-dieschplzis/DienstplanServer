package msc.shiftplanning;

import java.sql.Date;
import java.sql.Time;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jndi.JndiTemplate;

public class DayEntryhandling {

	
	@Autowired
	private static JdbcTemplate jdbcTemplateSimple;
	private static NamedParameterJdbcTemplate jdbcTemplate;
	  

	private static final DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	private static final DecimalFormat df = new DecimalFormat("0.00");
	
	private static final String DEFAULTDUTYHOURS = "d";
	private static final String DUTYHOURS = "p";
	
	
	
	
	/* statement to query one day entry of activity for a given user */
	protected String getQueryOneActivity() {
		return "Select a.user_id, a.day, \n"
				+ "    a.planed_activity_id, a.planed_hours, \n"
				+ "    a.actual_activity_id, a.start_date, a.end_date, a.actual_hours \n"
				+ " from day_entries a \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.day = :day";
	}

	
	/*
	 * read one entry for a given user and month
	 */
	public DayEntry readActivityEntry(int userID, Date day) {
		List<DayEntry> listActivities;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read Activity of user-ID " +userID + "/" + dateFormat.format(day));
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
	    
	    listActivities = jdbcTemplate.query(
	    		getQueryOneActivity(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("day", day),
	            new DayEntryMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		for (DayEntry elem : listActivities) {
			LoggerFactory.getLogger(UserImpl.class).info("Hours account found");
			return elem;
		}

		return null;
	}

	


	
	
	
	
	/* statement to query one day entry of activity for a given user */
	protected String getQueryCountVacationOfPeriod() {
		return "Select a.user_id, a.day, \n"
				+ "    a.planed_activity_id, a.planed_hours, \n"
				+ "    a.actual_activity_id, a.start_date, a.end_date, a.actual_hours \n"
				+ " from day_entries a \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.day >= :startDay \n"
				+ "   and a.day <= :endDay \n"
				+ " order by a.day ";
	}


	
	/*
	 * read entries for a given user and given period
	 */
	public List<DayEntry> readActivitiesOfPeriod(int userID, Date startDay, Date endDay) {
		float count = 0;
		List<DayEntry> listActivities;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read Activity of user-ID " +userID + "/" + dateFormat.format(startDay)+ "/" + dateFormat.format(endDay));
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
	    
	    listActivities = jdbcTemplate.query(
	    		getQueryCountVacationOfPeriod(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("startDay", startDay)
	    	      .addValue("endDay", endDay),
	            new DayEntryMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");

		return listActivities;
	}


	
	
	
	
	/*
	 * read one entry for a given user and month
	 */
	public float readCountVacationOfPeriod(int userID, Date startDay, Date endDay) {
		float count = 0;
		List<DayEntry> listActivities;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read Activity of user-ID " +userID + "/" + dateFormat.format(startDay)+ "/" + dateFormat.format(endDay));
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
	    
	    listActivities = jdbcTemplate.query(
	    		getQueryCountVacationOfPeriod(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID)
	    	      .addValue("startDay", startDay)
	    	      .addValue("endDay", endDay),
	            new DayEntryMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		for (DayEntry elem : listActivities) {
			if (elem.getPlanedActivity() == 1) {
				/* whole day */
				count++;
			} if (elem.getPlanedActivity() == 0) {
				/* half day */
				count = count + 0.5f;
			}
		}

		return count;
	}

	

	

	
	
	
	
	
	/* statement to insert a activity entry */
	protected String getInsertActivity() {
		return "Insert into day_entries \n"
				+ " (user_id, day,  \n"
				+ "  planed_activity_id, planed_hours, \n"
				+ "  actual_activity_id, start_date, end_date, actual_hours) \n"
				+ " values \n"
				+ " (:user_id, :day, \n"
				+ "  :planed_activity_id, :planed_hours, \n"
				+ "  :actual_activity_id, :start_date, :end_date, :actual_hours)";
	}
	
	

	/* insert a new day entry for user for new day */
	public DayEntry add(DayEntry newDayEntry) {
	    LoggerFactory.getLogger(UserImpl.class).debug("New Contract: " + newDayEntry.getUserId() + "/" +  dateFormat.format(newDayEntry.getDate()));
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
		
		int checkResult = jdbcTemplate.update(getInsertActivity(), new MapSqlParameterSource()
	    		  .addValue("user_id", newDayEntry.getUserId())
	    	      .addValue("day", newDayEntry.getDate())
	    	      .addValue("planed_activity_id", newDayEntry.getPlanedActivity())
	    	      .addValue("planed_hours", newDayEntry.getPlanedHours())
	    	      .addValue("actual_activity_id", newDayEntry.getActualActivity())
	    	      .addValue("start_date", newDayEntry.getStartTime())
	    	      .addValue("end_date", newDayEntry.getEndTime())
	    	      .addValue("actual_hours", newDayEntry.getActualHours()) );
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Insert okay!");
		if (checkResult > 0) {
			return newDayEntry;
		}

		return null;
	}

	

	
	/* statement to update a activity entry for planed values */
	protected String getUpdateActivity() {
		return "update day_entries a \n"
				+ " set a.planed_activity_id=:planed_activity_id, a.planed_hours=:planed_hours, \n"
				+ "     a.actual_activity_id=:actual_activity_id, a.start_date=:start_date, a.end_date=:end_date, a.actual_hours=:actual_hours \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.day = :day";
	}
	
	
	
	/* update a day entry if the activity type is changed or the time period of working has been changed */
	public DayEntry updateDay(DayEntry DayEntry) {
	    LoggerFactory.getLogger(UserImpl.class).debug("Update activity: " + DayEntry.getUserId() + "/" +  dateFormat.format(DayEntry.getDate()));
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
		
		int checkResult = jdbcTemplate.update(getUpdateActivity(), new MapSqlParameterSource()
			    		  .addValue("user_id", DayEntry.getUserId())
			    	      .addValue("day", DayEntry.getDate())
			    	      .addValue("planed_activity_id", DayEntry.getPlanedActivity())
			    	      .addValue("planed_hours", DayEntry.getPlanedHours())
			    	      .addValue("actual_activity_id", DayEntry.getActualActivity())
			    	      .addValue("start_date", DayEntry.getStartTime())
			    	      .addValue("end_date", DayEntry.getEndTime())
			    	      .addValue("actual_hours", DayEntry.getActualHours()) );
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Update okay!");
		if (checkResult >= 0) {
			return DayEntry;
		}

		return null;
	}
	
	
	
	
	
	
	
	
	/* statement to update a activity entry for planed values */
	protected String getUpdateActivityPlaned() {
		return "update day_entries a \n"
				+ " set a.planed_activity_id=:planed_activity_id, a.planed_hours=:planed_hours \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.day = :day";
	}
	

	/* update the day entry if the planned activity has been changed */
	public DayEntry updatePlaned(DayEntry DayEntry) {
	    LoggerFactory.getLogger(UserImpl.class).debug("Update planed activity: " + DayEntry.getUserId() + "/" +  dateFormat.format(DayEntry.getDate()));
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
		
		int checkResult = jdbcTemplate.update(getUpdateActivityPlaned(), new MapSqlParameterSource()
			    		  .addValue("user_id", DayEntry.getUserId())
			    	      .addValue("day", DayEntry.getDate())
			    	      .addValue("planed_activity_id", DayEntry.getPlanedActivity())
			    	      .addValue("planed_hours", DayEntry.getActualHours()) );
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Update okay!");
		if (checkResult != 0) {
			return DayEntry;
		}

		return null;
	}

	
	
	
	/* statement to update a activity entry for actual values */
	protected String getUpdateActivityActual() {
		return "update day_entries a \n"
				+ " set a.actual_activity_id=:actual_activity_id, a.start_date=:start_date, a.end_date=:end_date, a.actual_hours=:actual_hours \n"
				+ " where a.user_id = :user_id \n"
				+ "   and a.day = :day";
	}
	

	/* update the day entry if the working time period has been changed */
	public DayEntry updateActual(DayEntry DayEntry) {
	    LoggerFactory.getLogger(UserImpl.class).debug("Update planed activity: " + DayEntry.getUserId() + "/" +  dateFormat.format(DayEntry.getDate()));
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
		
		int checkResult = jdbcTemplate.update(getUpdateActivityActual(), new MapSqlParameterSource()
			    		  .addValue("user_id", DayEntry.getUserId())
			    	      .addValue("day", DayEntry.getDate())
			    	      .addValue("actual_activity_id", DayEntry.getActualActivity())
			    	      .addValue("start_date", DayEntry.getStartTime())
			    	      .addValue("end_date", DayEntry.getEndTime())
			    	      .addValue("actual_hours", DayEntry.getActualHours()) );
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Update okay!");
		if (checkResult >= 0) {
			return DayEntry;
		}

		return null;
	}


	
	
	

	
	
	
	
	
	
	
	
	
	
	/*
	 * store a plan values for a user and day
	 */
	public boolean savePlanActivity(int userID, Date day, int activity) {
		ContractHandling admContract = new ContractHandling();
		Contract contract = admContract.readActContract(userID, day);
		
		int hourWorkWeek = 0;
		if (contract != null) {
			hourWorkWeek = contract.getHoursToWork();
		}
		
		ActivityDefHandling activityAdmin = new ActivityDefHandling();
		ActivityDef actDef = activityAdmin.readActivityEntry(activity);
		float workHours = 0;
		String usage = " ";
		if (actDef != null) {
			workHours = (Math.floorDiv(hourWorkWeek* 100, 5) * actDef.getFactor()) / 100;
			usage = actDef.getUsage();
		}
		
		DayEntry oldEntry = this.readActivityEntry(userID, day);
		float diffhoursPlan = 0;
		float diffhoursActual = 0;
		if (oldEntry != null) {
			float oldHours = oldEntry.getPlanedHours();
			oldEntry.setPlanedActivity(activity);
			diffhoursPlan = workHours - oldHours;
			oldEntry.setPlanedHours(workHours);
			Date act = DateUtil.getCurrentDate();
			if (day.compareTo(act) > 0 || oldEntry.getStartTime() == null) {
				/* actual or in the past  or no time reported */
				oldEntry.setActualActivity(activity);
				if (usage == "d") {
					oldEntry.setActualHours(workHours);
					diffhoursActual = workHours - oldEntry.getActualHours();
				}
				this.updateActual(oldEntry);
			}
			this.updatePlaned(oldEntry);
		} else {
			DayEntry newEntry = new DayEntry();
			newEntry.setUserId(userID);
			newEntry.setDate(day);
			newEntry.setPlanedActivity(activity);
			newEntry.setPlanedHours(workHours);
			diffhoursPlan = workHours;
			newEntry.setActualActivity(activity);
			if (usage == "d") {
				newEntry.setActualHours(workHours);
				diffhoursActual = workHours;
			}
			this.add(newEntry);
		}	
		if (activity == 1) {
			/* Update vacation account */
		}
		HoursAccountHandling admHourAccount = new HoursAccountHandling();
		admHourAccount.updateAccounts(userID, day, diffhoursPlan, diffhoursActual);
		return true;
	}
	
	
	
	
	/* caclulate the hours for a default duty hours */
	protected float calculateDefaultDutyHours(int userID, Date day, float factor) {
		float hours = 0L;
		ContractHandling contractHandler = new ContractHandling();
		Contract contract = contractHandler.readActContract(userID, day);
		if (contract != null) {
			hours = (float) (Math.round(((contract.getHoursToWork()/5.0) * factor) * 100.0) / 100.0); 
		}
		
		return hours;
	}
	
	
	
	
	/* caclulate the hours for a duty entry */
	protected float calculateDutyHours(Time start, Time end) {
		float hours = 0L;
		
		LocalTime localStartTime = start.toLocalTime();
		LocalTime localEndTime = end.toLocalTime();
		
		if (localStartTime.isAfter(localEndTime)) {
			Long minutesBetween=ChronoUnit.MINUTES.between(localStartTime,localEndTime);
			hours = Math.round((minutesBetween/60.0 * 100.0) / 100.0);
		}
		
		return hours;
	}

	
	
	
	
	/* save a day with his planed and actual values */
	public MonthTimeAccount saveActivity(DayEntry dayEntry) {
		ActivityDefHandling activityHandler = new ActivityDefHandling();
		DayEntry testEntry = this.readActivityEntry(dayEntry.getUserId(), dayEntry.getDate());
		MonthTimeAccount timeAccount = null;
		float planedHoursDay = 0L;
		float actualHoursDay = 0L;
		
		/* calculate the hours of planed activity */
		int planedActivity = dayEntry.getPlanedActivity();
		ActivityDef planedActivityDef = activityHandler.readActivityEntry(planedActivity);
		if (planedActivityDef != null) {
			if (planedActivityDef.getUsage().equals(this.DEFAULTDUTYHOURS) || planedActivityDef.getUsage().equals(this.DUTYHOURS)) {
				planedHoursDay = calculateDefaultDutyHours(dayEntry.getUserId(), dayEntry.getDate(), planedActivityDef.getFactor());
				dayEntry.setPlanedHours(planedHoursDay);
			}
		}
		/* calculate the hours of planed activity */
		int actualActivity = dayEntry.getActualActivity();
		ActivityDef actualActivityDef = activityHandler.readActivityEntry(actualActivity);
		if (actualActivityDef != null) {
			if (actualActivityDef.getUsage().equals(this.DEFAULTDUTYHOURS)) {
				actualHoursDay = calculateDefaultDutyHours(dayEntry.getUserId(), dayEntry.getDate(), planedActivityDef.getFactor());
				dayEntry.setActualHours(actualHoursDay);
			} else if (planedActivityDef.getActivityClass() == this.DUTYHOURS) {
				actualHoursDay = dayEntry.getActualHours();
			}
		}
		
		
		
		float planedhours = 0L;
		float actualhours = 0L;	
		HoursAccountHandling hoursAccount = new HoursAccountHandling();
		if (testEntry != null) {
			/* day entry already exists and must be updated */
			planedhours = planedHoursDay - testEntry.getPlanedHours();
			actualhours = actualHoursDay - testEntry.getActualHours();
			updateDay(dayEntry);
		} else {
			/* day doesn't exists and must be created */
			planedhours = planedHoursDay;
			actualhours = actualHoursDay;
			add(dayEntry);
		}
		/* save the the hours account records */
		hoursAccount.saveAccount(dayEntry.getUserId(), DateUtil.firstDayOfMonth(dayEntry.getDate()), planedhours, actualhours);
		timeAccount = hoursAccount.readAccount(dayEntry.getUserId(), DateUtil.firstDayOfMonth(dayEntry.getDate()));
		
		return timeAccount;
	}
	
	
	
	
	/* statement to delete all activity entry for a user */
	protected String getDeletAllActivityActual() {
		return "delete from day_entries \n"
				+ " where user_id = :user_id ";
	}
	
	
	/* delete all day activities when a user should be deleted */
	public boolean deleteAllEntriesOfUser(int userID) {
	    LoggerFactory.getLogger(UserImpl.class).debug("delete all activity for user: " + userID);
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
		
		int checkResult = jdbcTemplate.update(getDeletAllActivityActual(), new MapSqlParameterSource()
			    		  .addValue("user_id", userID) );
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Delete okay!");
		if (checkResult >= 0) {
			return true;
		}

		return false;
	}




}
