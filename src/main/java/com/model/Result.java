package com.model;

import java.io.Serializable;

public class Result implements Serializable{
	private String TOOLCLASSFIELDSNAME;// 属性
	private String VALVALNUM;// 值
	private String UNITNAME;// 单位
	private String TOOLCLASSFIELDSNAME2;// 描述
	private String TOOLCLASSFIELDSPOS;// 位置

	public String getTOOLCLASSFIELDSNAME() {
		return TOOLCLASSFIELDSNAME;
	}

	public void setTOOLCLASSFIELDSNAME(String tOOLCLASSFIELDSNAME) {
		TOOLCLASSFIELDSNAME = tOOLCLASSFIELDSNAME;
	}

	public String getVALVALNUM() {
		return VALVALNUM;
	}

	public void setVALVALNUM(String vALVALNUM) {
		VALVALNUM = vALVALNUM;
	}

	public String getUNITNAME() {
		return UNITNAME;
	}

	public void setUNITNAME(String uNITNAME) {
		UNITNAME = uNITNAME;
	}

	public String getTOOLCLASSFIELDSNAME2() {
		return TOOLCLASSFIELDSNAME2;
	}

	public void setTOOLCLASSFIELDSNAME2(String tOOLCLASSFIELDSNAME2) {
		TOOLCLASSFIELDSNAME2 = tOOLCLASSFIELDSNAME2;
	}

	public String getTOOLCLASSFIELDSPOS() {
		return TOOLCLASSFIELDSPOS;
	}

	public void setTOOLCLASSFIELDSPOS(String tOOLCLASSFIELDSPOS) {
		TOOLCLASSFIELDSPOS = tOOLCLASSFIELDSPOS;
	}

	@Override
	public String toString() {
		return "Result [TOOLCLASSFIELDSNAME=" + TOOLCLASSFIELDSNAME + ", VALVALNUM=" + VALVALNUM + ", UNITNAME="
				+ UNITNAME + ", TOOLCLASSFIELDSNAME2=" + TOOLCLASSFIELDSNAME2 + ", TOOLCLASSFIELDSPOS="
				+ TOOLCLASSFIELDSPOS + "]";
	}

}
