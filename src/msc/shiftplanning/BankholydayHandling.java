package msc.shiftplanning;

import java.sql.Date;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.naming.NamingException;
import javax.sql.DataSource;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jndi.JndiTemplate;

public class BankholydayHandling {
	
	private static List<Bankholyday> listBankholydays = new ArrayList<Bankholyday>();
	
	
	@Autowired
	private static JdbcTemplate jdbcTemplateSimple;
	private static NamedParameterJdbcTemplate jdbcTemplate;
	  
	
	
	
	/* 
	 * statement to query the definition of bankholiday 
	 */
	protected static String getQueryBankholydays() {
		return "Select a.name, a.fix_date, \n"
				+ "    a.offset, a.reference_to_offset \n"
				+ " from bankholiday";
	}

	
	/*
	 * read all definitions for bank holidays
	 */
	public static List<BankholydayDef> readBankholydays() {
		List<BankholydayDef> listBankholyday;
		
		LoggerFactory.getLogger(UserImpl.class).debug("Read Bankholydays");
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
	    
	    listBankholyday = jdbcTemplate.query(
	    		getQueryBankholydays(),
	    		new MapSqlParameterSource(),
	            new BankholydayDefMapper());
	    LoggerFactory.getLogger(UserImpl.class).debug("Select and Fetch okay!");
		return listBankholyday;
	}


	
	
	/*
	 * calculate the exact date of a bank holiday in a year based on the definition.
	 */
	private static Date getBankholydayDate(BankholydayDef def, Date eastern, int year) {
		Date calcDate = null;
		if (def.getFixDate() != null) {
			/* fix date definition */
			calcDate = DateUtil.getDate(year, def.getFixDate().toLocalDate().getMonthValue(), def.getFixDate().toLocalDate().getDayOfMonth());
		} else if (def.getReferenceOffset() == "E") {
			calcDate = DateUtil.addDays(eastern, def.getOffset());
		} 
		
		return calcDate;
	}
	
	
	
	
	/*
	 * build up a list of bank holiday for a given period
	 */
	public static boolean buildupBankholidayList(int startYear, int endYear) {
		List<BankholydayDef> listBankholydayDefsList = readBankholydays();
		
		for (int i = startYear; i <= endYear; i++) {
			Date eastern = DateUtil.getEastern(i);
			for (BankholydayDef def : listBankholydayDefsList) {
				listBankholydays.add(new Bankholyday(def.getName(), getBankholydayDate(def, eastern, i)));
			}
		}
		
		Collections.sort(listBankholydays, new Comparator<Bankholyday>() {
										            @Override
										            public int compare(Bankholyday p1, Bankholyday p2) {
										                return p1.getDate().compareTo(p2.getDate());
										            }
										        });
		return true;
	}
	
	
	
	
	/* 
	 * returned the name of the bank holiday of a given date.
	 * If the day isn't a bank holiday a NULL will be returned.
	 */
	public static String getBankholyday(Date checkDate) {
		String name = null;
		if (listBankholydays == null) {
			/* Bank holiday list does not exist and must be created */
			Date date = new Date(Calendar.getInstance().getTime().getTime());  
			buildupBankholidayList(date.toLocalDate().getYear() - 1, date.toLocalDate().getYear() + 2);
		}
		for (Bankholyday elem : listBankholydays) {
			if (elem.getDate().compareTo(checkDate) == 0) {
				return elem.getName();
			} else if (elem.getDate().compareTo(checkDate) > 0) {
				/* bankholyday > checkDate */
				return null;
			}
		}
		return null;
	}
	

}
