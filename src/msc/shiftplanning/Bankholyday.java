package msc.shiftplanning;

import java.sql.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

public class Bankholyday implements Comparable<Bankholyday> {
	
	private static DateFormat dateFormat = new SimpleDateFormat("dd.mm.yyyy");
	
	private String name;
	private Date date;
	
	
	
	public Bankholyday(String name, Date date) {
		this.name = name;
		this.date = date;
	}
	
	
	
	public String getName() {
		return name;
	}
	
	
	
	
	public void setNaame(String name) {
		this.name = name;
	}
	
	
	
	
	public Date getDate() {
		return date;
	}
	
	
	
	
	public void setDate(Date date) {
		this.date = date;
	}
	
	
	
	
	
	@Override
	public String toString() {
	    return name + ":" + dateFormat.format(date);
	}

	@Override
	public int compareTo(Bankholyday o) {
	    return date.compareTo(o.getDate());
	}
	

	

}
