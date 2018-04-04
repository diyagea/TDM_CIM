package com.model;

public class Tool {
	private String TOOLID;
	private String PhycialToolID;
	private String CurrentToolLife;
	private String WarningToolLife;
	private boolean bad;
	
	public String getTOOLID() {
		return TOOLID;
	}
	public void setTOOLID(String tOOLID) {
		TOOLID = tOOLID;
	}
	public String getPhycialToolID() {
		return PhycialToolID;
	}
	public void setPhycialToolID(String phycialToolID) {
		PhycialToolID = phycialToolID;
	}
	public String getCurrentToolLife() {
		return CurrentToolLife;
	}
	public void setCurrentToolLife(String currentToolLife) {
		CurrentToolLife = currentToolLife;
	}
	public String getWarningToolLife() {
		return WarningToolLife;
	}
	public void setWarningToolLife(String warningToolLife) {
		WarningToolLife = warningToolLife;
	}
	public boolean getBad() {
		return bad;
	}
	public void setBad(boolean bad) {
		this.bad = bad;
	}
	
	
}
