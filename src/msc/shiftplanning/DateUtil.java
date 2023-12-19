package msc.shiftplanning;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import org.slf4j.LoggerFactory;

public class DateUtil {


	private static DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
	
	
	/*
	 * return the actual Date
	 */
	public static java.sql.Date getCurrentDate() {
		return new Date(System.currentTimeMillis());
	}
	
	
	
	
	/* 
	 * create a Date from tripel year, month and day 
	 */
	public static java.sql.Date getDate(int year, int month, int day) {
        Calendar cal = new GregorianCalendar(year,month-1,day,0,0,0);;
        /*
        cal.set(Calendar.DAY_OF_MONTH, day);
        cal.set(Calendar.MONTH, month - 1);
        cal.set(Calendar.YEAR, year);
        cal.set(Calendar.HOUR_OF_DAY, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.SECOND, 0);
         */
        cal.set(Calendar.MILLISECOND, 0);
        //LoggerFactory.getLogger(UserImpl.class).debug("DateUtil.DateUtil cal: " + cal.get(Calendar.YEAR) + "/" + cal.get(Calendar.MONTH) + "/" + cal.get(Calendar.DAY_OF_MONTH));
        java.sql.Date sqlDate = new java.sql.Date(cal.getTimeInMillis());
        //LoggerFactory.getLogger(UserImpl.class).debug("DateUtil.DateUtil sqlDate: " + dateFormat.format(sqlDate));
        return sqlDate;
    }
	
	
	
	/*
	 * add or subtract a number of days from a date
	 */
	public static Date addDays(Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new Date(c.getTimeInMillis());
    }
	
	
	
	
	/*
	 * add or subtract a number of months from a date
	 */
	public static Date addMonths(Date date, int months) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.MONTH, months);
        return new Date(c.getTimeInMillis());
    }

	

	
	
	/*
	 * returned the only the date without time
	 */
	public static Date truncateDate(Date date) {
		if (date == null) {
			return null;
		}
		return getDate(date.toLocalDate().getYear(), date.toLocalDate().getMonthValue(), date.toLocalDate().getDayOfMonth());
        
    }

	
	
	
	/*
	 * returned the first day of a Month
	 */
	public static Date firstDayOfMonth(Date date) {
		return getDate(date.toLocalDate().getYear(), date.toLocalDate().getMonthValue(), 1);
        
    }

	
	/*
	 * returned the last day of the month
	 */
	public static Date lastDayOfMonth(Date date) {
		return addDays(addMonths(firstDayOfMonth(date), 1),-1) ;
    }


	
	/*
	 * return the year of a given date
	 */
	public static int getYear(Date date) {
        return date.toLocalDate().getYear();
	}
	
	
	
	/*
	 * return the actual date
	 */
	public static Date today() {
		LocalDateTime actuaDate = java.time.LocalDateTime.now();
		return new java.sql.Date(Date.from(actuaDate.atZone(ZoneId.systemDefault()).toInstant()).getTime());
	}
	
	
	/*
	 * calculate the number of working day in a period
	 */
	public static int getNumberWorkingDays(Date start, Date end) {
		int count = 0;
		
		for (Date act = start; act.compareTo(end) < 0; act = addDays(act,1)) {
			String holiDay = BankholydayHandling.getBankholyday(act);
			if (holiDay == null) {
				if (DateUtil.getDayNumberofWeek(act) != Calendar.SATURDAY && DateUtil.getDayNumberofWeek(act) != Calendar.SUNDAY) {
					count++;
				}
			}
		}
		if (end == null) {
			LoggerFactory.getLogger(UserImpl.class).debug("getNumberWorkingDays   start: " + dateFormat.format(start) + "    count: " + count); 
		} else {
			LoggerFactory.getLogger(UserImpl.class).debug("getNumberWorkingDays   start: " + dateFormat.format(start) + "   end: "  + dateFormat.format(end) + "    count: " + count); 			
		}
		return count;
	}
	
	
	
	
	
	/*
	 * calculate the working hours of gigven period
	 * Is the end not given then the last day of month of start date will be used.
	 */
	public static float workingHours(int userID, Date startDate, Date endDate) {
		Date endPeriod = endDate;
		if (endPeriod == null) {
			endPeriod = DateUtil.firstDayOfMonth(startDate);
		}
		
		float hours = 0;
		ContractHandling contractAdministration = new ContractHandling();
		List<Contract> listContracts = contractAdministration.readContractsOfPeriod(userID, startDate, endPeriod);
		for (Contract elemContract : listContracts) {
			Date start = startDate;
			if (elemContract.getStartDate().compareTo(startDate) > 0) {
				/* contract start date is > startDate */
				start = elemContract.getStartDate();
			}
			Date end = endPeriod;
			if (elemContract.getEndDate().compareTo(endPeriod) < 0) {
				/* contract end date is < endPeriod */
				end = elemContract.getEndDate();
				
			}
			int workingDays = getNumberWorkingDays(start, end);
			hours += Math.floorDiv(elemContract.getHoursToWork() / 5 * workingDays * 100, 1) / 100;
		}
		return hours;
	}
	
	
	
	
	/*
	 * calculate how many month between start and end 
	 */
	public static int getMonthBetween(Date startMonth, Date endMonth) {
		Date start = firstDayOfMonth(startMonth);
		Date end = lastDayOfMonth(endMonth);
		
		LoggerFactory.getLogger(UserImpl.class).debug("getMonthBetween  start: " + dateFormat.format(start) + "  end: " + dateFormat.format(end)); 
		Period diff = Period.between(firstDayOfMonth(startMonth).toLocalDate(), lastDayOfMonth(endMonth).toLocalDate() );
		return diff.getMonths();
	}
	
	
	
	
	
	
	/*
	 * calculate easter of a year
	 */
	
	private static int a(int year) {
	    return Math.floorMod(year, 19);
	}
	
	
	
	private static int k(int year) {
	    return Math.floorMod(year, 100);
	}
	
	
	
	private static int m(int year) {
	    return 15 + (Math.floorDiv((3*k(year) + 3), 4) - Math.floorDiv((8*k(year) + 13),25) );
	} 
	
	
	
	private static int d(int year) {
	    return Math.floorMod(19*a(year) + m(year), 30);
	}
	
	
	
	private static int s(int year) {
		return Math.floorDiv(2 - (3*k(year) + 3), 4);
	}
	

	
	
	private static int r(int year) {
		return Math.floorDiv(d(year), 29) + (Math.floorDiv(d(year), 28) - Math.floorDiv(d(year), 29))*Math.floorDiv(a(year), 11);
	}
	
	
	
	
	private static int sz(int year) {
		return (7 - Math.floorMod(year + Math.floorDiv(year, 4) + s(year), 7));
	}
	
	
	
	private static int og(int year) {
		return (21 + d(year) - r(year));
	}
	
	
	private static int oe(int year) {
		return (7 - Math.floorMod((og(year) - sz(year)), 7));
	}
	
	
	private static int os(int year) {
		return (og(year) + oe(year));
	}
	


	public static int getDayNumberofWeek(Date date) {
	    Calendar cal = Calendar.getInstance();
	    cal.setTime(date);
	    return cal.get(Calendar.DAY_OF_WEEK);
	}
	
	
	
	public static Date getEastern(int year) {
		Date eastern = DateUtil.addDays(DateUtil.getDate(year, 3, 1), os(year) -1);
		
		return eastern;
	}
	
	
	
}
