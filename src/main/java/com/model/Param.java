package com.model;

import java.io.Serializable;

public class Param implements Serializable{
	private String NO;
	private String TECHNOCLASSID;
	private String TECHNOCLASSNAME;
	private String TECHNOGROUPID;
	private String TECHNOGROUPNAME;
	private String MATERIALID;//材质
	private String MATERIALNAME;
	private String MATERIALGROUPID;
	private String MATERIALGROUPNAME;
	private String REVOLUTIONS;//转速n
	private String CUTSPEED;//削切速度vc
	private String FEEDRATE;//进给率vf
	private String FEEDPTOOTH;//每齿进给fz
	private String FEEDPREV;//每一旋转进给fn
	
	public String getTECHNOCLASSID() {
		return TECHNOCLASSID;
	}
	public void setTECHNOCLASSID(String tECHNOCLASSID) {
		TECHNOCLASSID = tECHNOCLASSID;
	}
	public String getTECHNOCLASSNAME() {
		return TECHNOCLASSNAME;
	}
	public void setTECHNOCLASSNAME(String tECHNOCLASSNAME) {
		TECHNOCLASSNAME = tECHNOCLASSNAME;
	}
	public String getTECHNOGROUPID() {
		return TECHNOGROUPID;
	}
	public void setTECHNOGROUPID(String tECHNOGROUPID) {
		TECHNOGROUPID = tECHNOGROUPID;
	}
	public String getTECHNOGROUPNAME() {
		return TECHNOGROUPNAME;
	}
	public void setTECHNOGROUPNAME(String tECHNOGROUPNAME) {
		TECHNOGROUPNAME = tECHNOGROUPNAME;
	}
	public String getMATERIALID() {
		return MATERIALID;
	}
	public void setMATERIALID(String mATERIALID) {
		MATERIALID = mATERIALID;
	}
	public String getMATERIALNAME() {
		return MATERIALNAME;
	}
	public void setMATERIALNAME(String mATERIALNAME) {
		MATERIALNAME = mATERIALNAME;
	}
	public String getMATERIALGROUPID() {
		return MATERIALGROUPID;
	}
	public void setMATERIALGROUPID(String mATERIALGROUPID) {
		MATERIALGROUPID = mATERIALGROUPID;
	}
	public String getMATERIALGROUPNAME() {
		return MATERIALGROUPNAME;
	}
	public void setMATERIALGROUPNAME(String mATERIALGROUPNAME) {
		MATERIALGROUPNAME = mATERIALGROUPNAME;
	}
	public String getREVOLUTIONS() {
		return REVOLUTIONS;
	}
	public void setREVOLUTIONS(String rEVOLUTIONS) {
		REVOLUTIONS = rEVOLUTIONS;
	}
	public String getCUTSPEED() {
		return CUTSPEED;
	}
	public void setCUTSPEED(String cUTSPEED) {
		CUTSPEED = cUTSPEED;
	}
	public String getFEEDRATE() {
		return FEEDRATE;
	}
	public void setFEEDRATE(String fEEDRATE) {
		FEEDRATE = fEEDRATE;
	}
	public String getFEEDPTOOTH() {
		return FEEDPTOOTH;
	}
	public void setFEEDPTOOTH(String fEEDPTOOTH) {
		FEEDPTOOTH = fEEDPTOOTH;
	}
	public String getFEEDPREV() {
		return FEEDPREV;
	}
	public void setFEEDPREV(String fEEDPREV) {
		FEEDPREV = fEEDPREV;
	}
	public String getNO() {
		return NO;
	}
	public void setNO(String nO) {
		NO = nO;
	}
	
}
