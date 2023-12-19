package msc.shiftplanning;

import java.sql.Date;

public class BankholydayDef {
	
	
	private String name;
	private Date fixDate;
	private int offset;
	private String referenceOffset;
	
	
	
	public String getName() {
		return name;
	}
	
	
	
	public void setName(String name) {
		this.name = name;
	}
	
	
	
	public Date getFixDate() {
		return fixDate;
	}
	
	
	
	public void setFixDate(Date fixDate) {
		this.fixDate = fixDate;
	}
	
	
	
	
	public int getOffset() {
		return offset;
	}
	
	
	
	public void setOffset(int offset) {
		this.offset = offset;
	}
	
	
	
	public String getReferenceOffset() {
		return referenceOffset;
	}
	
	
	
	public void setReferenceOffset(String referenceOffset) {
		this.referenceOffset = referenceOffset;
	}
	
	
	
	

}
