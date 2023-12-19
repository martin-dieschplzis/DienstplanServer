package msc.shiftplanning;

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

public class AllowedToDoHandling {


	
	
	
	@Autowired
	private static JdbcTemplate jdbcTemplateSimple;
	private static NamedParameterJdbcTemplate jdbcTemplate;
	  

	private static DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
	

	
	
	
	
	/* statement to query one activity definition */
	protected String getQueryTodDos() {
		return "Select a.user_id, a.todos_id \n"
				+ " from allowed_todos a \n"
				+ " where a.user_id = :user_id ";
	}

	
	/*
	 * read one entry of activity definition
	 */
	public List<AllowedToDo> readListOfDoTosEntry(int userID) {
		List<AllowedToDo> listToDos;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read list of ToDos: " + userID );
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
	    
	    listToDos = jdbcTemplate.query(
	    		getQueryTodDos(),
	    		new MapSqlParameterSource()
	    	      .addValue("user_id", userID),
	            new allowedToDoMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay! ");

		return listToDos;
	}

	
	
	
	
	/* statement to query one activity definition */
	protected String getInsertTodDos() {
		return "insert into allowed_todos (a.user_id, a.todos_id) \n"
				+ " valuse \n"
				+ " (:user_id, :todos_id) ";
	}

	
	/* Insert on new activity which is the user allowed to do */
	public AllowedToDo add(AllowedToDo newToDo) {
	    LoggerFactory.getLogger(UserImpl.class).debug("New ToDo for: " + newToDo.getUserID() + "/" + newToDo.getToDo());
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
		
		int checkResult = jdbcTemplate.update(getInsertTodDos(), new MapSqlParameterSource()
	    		  .addValue("user_id", newToDo.getUserID())
	    	      .addValue("todos_id", newToDo.getToDo()));
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Insert okay!");
	    
	    if (checkResult == 1) {
	    	return newToDo;
	    }
		return new AllowedToDo();
	}


	
	
	
	
	
	/* statement to delete one activity definition */
	protected String getDleteTodDos() {
		return "delete from allowed_todos \n"
				+ " where a.user_id = :user_id \n"
				+ " and todos_id = :todos_id ";
	}

	
	
	
	/* delete one of the activity which the user is allowed to do */
	public AllowedToDo del(AllowedToDo newToDo) {
	    LoggerFactory.getLogger(UserImpl.class).debug("delete ToDo: " + newToDo.getUserID() + "/" + newToDo.getToDo());
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
		
		int checkResult = jdbcTemplate.update(getDleteTodDos(), new MapSqlParameterSource()
	    		  .addValue("user_id", newToDo.getUserID())
	    	      .addValue("todos_id", newToDo.getToDo()));
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Delete okay!");
	    
	    if (checkResult > 0) {
	    	return newToDo;
	    }
		return new AllowedToDo();
	}

	

	
	
	
	/* statement to delete all activity definition */
	protected String getDeleteAllTodDos() {
		return "delete from allowed_todos \n"
				+ " where user_id = :user_id  ";
	}

	
	
	/* deleta all authorizations (activities, which the user is allowed to do) */
	public boolean del(int userID) {
	    LoggerFactory.getLogger(UserImpl.class).debug("delete ToDos: " + userID);
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
		
		int checkResult = jdbcTemplate.update(getDeleteAllTodDos(), new MapSqlParameterSource()
	    		  .addValue("user_id", userID));
		
	    LoggerFactory.getLogger(UserImpl.class).debug("Delete okay!");
		if (checkResult >= 0) {
			return true;
		}

		return false;
	}

	

	

	
	
}
