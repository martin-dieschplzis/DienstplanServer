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

public class ActivityDefHandling {

	
	@Autowired
	private static JdbcTemplate jdbcTemplateSimple;
	private static NamedParameterJdbcTemplate jdbcTemplate;
	  

	private static DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
	

	
	
	
	
	/* statement to query one activity definition */
	protected String getQueryOneActivity() {
		return "Select a.activity_id, a.name, a.factor, \n"
				+ "    a.default_hours, a.name, a.colour, \n"
				+ "    a.class, a.order \n"
				+ " from activities a \n"
				+ " where a.activity_id = :activity_id ";
	}

	
	/*
	 * read one entry of activity definition
	 */
	public ActivityDef readActivityEntry(int activityID) {
		List<ActivityDef> listActivities;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read activity: " + activityID );
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
	    	      .addValue("activity_id", activityID),
	            new ActivityDefMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay! ");
		for (ActivityDef elem : listActivities) {
			LoggerFactory.getLogger(UserImpl.class).info("Hours account found");
			return elem;
		}

		return null;
	}

	


	
	
	/* statement to query all activity definition */
	protected String getQueryAllActivity() {
		return "Select a.activity_id, a.name, a.factor, \n"
				+ "    a.default_hours, a.name, a.colour, \n"
				+ "    a.class, a.order \n"
				+ " from activities a ";
	}

	
	/*
	 * read one entry of activity definition
	 */
	public List<ActivityDef> readAllActivityEntry() {
		List<ActivityDef> listActivities;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read all activity" );
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
	    		new MapSqlParameterSource(),
	            new ActivityDefMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");

		return  listActivities;
	}

	
	
	
}
