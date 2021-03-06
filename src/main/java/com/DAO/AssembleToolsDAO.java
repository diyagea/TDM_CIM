package com.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.model.Item2;
import com.model.Param;
import com.model.Result;
import com.model.ToolClass;
import com.model.ToolClassType;
import com.model.ToolGroup;

@Repository
public class AssembleToolsDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ToolClassType> findToolClassTypes() {
		String sql = "SELECT    DRAWING,    ISNULL(NAME01, NAME) NAME,    TOOLCLASSTYPE FROM  TDM_TOOLCLASSTYPE  WHERE  TOOLCLASSTYPE  = TOOLCLASSTYPE  AND (ISNULL(COMPORTOOL, 3)  in ( 2  , 3  ))  AND (ACTIV  = 1) ORDER BY TOOLCLASSTYPE,   NAME01,   NAME  ";
		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		ToolClassType type = null;
		List<ToolClassType> types = new ArrayList<ToolClassType>();
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			type = new ToolClassType();
			type.setDrawing(line.get("DRAWING").toString());
			type.setName(line.get("NAME").toString());
			type.setToolClassType(line.get("TOOLCLASSTYPE").toString());

			types.add(type);
		}
		return types;
	}

	public List<ToolClass> findToolClasses(String type) {
		String sql = "SELECT    Drawing,    ISNULL(NAME01, NAME) NAME,    TOOLCLASSID FROM  TDM_TOOLCLASS  WHERE  TOOLCLASSTYPE  = N'"
				+ type
				+ "' AND (ISNULL(COMPORTOOL, 3)  in ( 2  , 3  ))  AND (ACTIV  = 1) ORDER BY POS,   TOOLCLASSID,   NAME01,   NAME  ";
		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		ToolClass tClass = null;
		List<ToolClass> classes = new ArrayList<ToolClass>();
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			tClass = new ToolClass();
			tClass.setDrawing(line.get("DRAWING").toString());
			tClass.setName(line.get("NAME").toString());
			tClass.setToolClassID(line.get("TOOLCLASSID").toString());
			classes.add(tClass);
		}
		return classes;

	}

	public List<ToolGroup> findToolGroups(String tClass) {
		String sql = "SELECT    Drawing,    ISNULL(NAME01, NAME) NAME,    TOOLGROUPID FROM  TDM_TOOLGROUP  WHERE  TOOLCLASSID  = N'"
				+ tClass
				+ "' AND (ISNULL(COMPORTOOL, 3)  in ( 2  , 3  ))  AND (ACTIV  = 1) ORDER BY POS,   TOOLGROUPID,   NAME01,   NAME  ";
		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		ToolGroup group = null;
		List<ToolGroup> groups = new ArrayList<ToolGroup>();
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			group = new ToolGroup();
			group.setDrawing(line.get("DRAWING").toString());
			group.setName(line.get("NAME").toString());
			group.setToolGroupID(line.get("TOOLGROUPID").toString());
			groups.add(group);
		}
		return groups;
	}
	
	/**
	 * 查询刀具列表
	 * @param type
	 * @param tClass
	 * @param group
	 * @param DC
	 * @param XS
	 * @return
	 */
	public List<Item2> findItems(String type, String tClass, String group, String DC, String XS) {
		String classSQL = tClass == null ? "" : "AND TDM_TOOL.TOOLCLASSID  = N'" + tClass + "' ";
		String groupSQL = group == null ? "" : " AND TDM_TOOL.TOOLGROUPID  = N'" + group + "' ";
		String dcSQL = (DC == null || DC.trim().equals("")) ? "" : "  AND TDM_TOOLVALUESEXV1.VALNUM = " + DC + " ";
		String xsSQL = (XS == null || XS.trim().equals("")) ? "" : " AND TDM_TOOLVALUESEXV2.VALNUM  = " + XS + "  ";

		String sql = "SELECT DISTINCT    TDM_TOOL.TOOLID,    ISNULL(TDM_TOOL.NAME01, TDM_TOOL.NAME) NAME,    ISNULL(TDM_TOOL.NAME201, "
				+ "TDM_TOOL.NAME2) NAME2,    TDM_TOOL.TOOLCLASSID,    ISNULL(TDM_TOOLCLASS.NAME01, TDM_TOOLCLASS.NAME) TOOLCLASSNAME, "
				+ "   TDM_TOOL.TOOLGROUPID,    ISNULL(TDM_TOOLGROUP.NAME01, TDM_TOOLGROUP.NAME) TOOLGROUPNAME,    TDM_TOOL.ADAPTERID, "
				+ "   ISNULL(TDM_ADAPTER.NAME01, TDM_ADAPTER.NAME) ADAPTERNAME,    TDM_TOOL.CUTGRADEID,    ISNULL(TDM_CUTGRADE.NAME01,"
				+ " TDM_CUTGRADE.NAME) CUTGRADENAME,    TDM_TOOL.WEIGHT,    TDM_TOOL.STOCKPLACE,    TDM_TOOL.DINCLASSID,  "
				+ "  ISNULL(TDM_DINCLASS.NAME01, TDM_DINCLASS.NAME) DINCLASSNAME,    TDM_TOOL.TOOLNUMBER,    TDM_TOOL.COUNTCUTS,  "
				+ "  TDM_TOOL.COUNTSTAGE,    TDM_TOOL.CUTPOSITION,    TDM_TOOL.STATEID1,    TDM_TOOL.STATEID2,    TDM_TOOL.CADID, "
				+ "   TDM_TOOL.CADPATH,    TDM_TOOL.TOOLTYPE,    TDM_TOOL.METRICORINCH,    TDM_TOOLVALUES1.VALNUM VALVALNUM1,  "
				+ "  TDM_TOOLVALUES2.VALNUM VALVALNUM2,    TDM_TOOLVALUES3.VALNUM VALVALNUM3 FROM  TDM_TOOLVALUESEXV TDM_TOOLVALUES1 "
				+ " RIGHT OUTER JOIN  TDM_TOOLMTV TDM_TOOL  ON  TDM_TOOLVALUES1.TOOLID  = TDM_TOOL.TOOLID "
				+ "  AND TDM_TOOLVALUES1.FUNCTYPEID  = N'XS'   LEFT OUTER JOIN  TDM_TOOLVALUESEXV TDM_TOOLVALUES2  ON "
				+ " TDM_TOOLVALUES2.TOOLID  = TDM_TOOL.TOOLID   AND TDM_TOOLVALUES2.FUNCTYPEID  = N'YS'   LEFT OUTER JOIN "
				+ " TDM_TOOLVALUESEXV TDM_TOOLVALUES3  ON  TDM_TOOLVALUES3.TOOLID  = TDM_TOOL.TOOLID  "
				+ " AND TDM_TOOLVALUES3.FUNCTYPEID  = N'DC'   LEFT OUTER JOIN  TDM_ADAPTER  ON  TDM_TOOL.ADAPTERID  = TDM_ADAPTER.ADAPTERID "
				+ "  LEFT OUTER JOIN  TDM_CUTGRADE  ON  TDM_TOOL.CUTGRADEID  = TDM_CUTGRADE.CUTGRADEID   LEFT OUTER JOIN  TDM_DINCLASS  ON "
				+ " TDM_TOOL.DINCLASSID  = TDM_DINCLASS.DINCLASSID   LEFT OUTER JOIN  TDM_TOOLCLASS  ON  "
				+ " TDM_TOOL.TOOLCLASSID  = TDM_TOOLCLASS.TOOLCLASSID   LEFT OUTER JOIN  TDM_TOOLGROUP  ON "
				+ " TDM_TOOL.TOOLCLASSID  = TDM_TOOLGROUP.TOOLCLASSID   AND TDM_TOOL.TOOLGROUPID  = TDM_TOOLGROUP.TOOLGROUPID , "
				+ "  TDM_TOOLVALUES TDM_TOOLVALUESEXV1,   TDM_TOOLVALUES TDM_TOOLVALUESEXV2  WHERE "
				+ " TDM_TOOLVALUESEXV1.TOOLCLASSFIELDSPOS  = N'1'  AND TDM_TOOLVALUESEXV1.TOOLID  = TDM_TOOL.TOOLID "
				+ " AND TDM_TOOLVALUESEXV2.TOOLCLASSFIELDSPOS  = N'2'  AND TDM_TOOLVALUESEXV2.TOOLID  = TDM_TOOL.TOOLID "
				+ classSQL + groupSQL + " AND TDM_TOOLCLASS.TOOLCLASSTYPE  = " + type + dcSQL + xsSQL
				+ " ORDER BY TDM_TOOL.TOOLID ";

		System.out.println(sql);

		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		Item2 item = null;
		List<Item2> items = new ArrayList<Item2>();
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			item = new Item2();
			item.setTOOLID(getStringNotNull(line.get("TOOLID")));
			item.setNAME(getStringNotNull(line.get("NAME")));
			item.setNAME2(getStringNotNull(line.get("NAME2")));
			item.setTOOLCLASSID(getStringNotNull(line.get("TOOLCLASSID")));
			item.setTOOLCLASSNAME(getStringNotNull(line.get("TOOLCLASSNAME")));
			item.setTOOLGROUPID(getStringNotNull(line.get("TOOLGROUPID")));
			item.setTOOLGROUPNAME(getStringNotNull(line.get("TOOLGROUPNAME")));
			item.setADAPTERID(getStringNotNull(line.get("ADAPTERID")));
			item.setADAPTERNAME(getStringNotNull(line.get("ADAPTERNAME")));
			item.setCUTGRADEID(getStringNotNull(line.get("CUTGRADEID")));
			item.setCUTGRADENAME(getStringNotNull(line.get("CUTGRADENAME")));
			item.setSTOCKPLACE(getStringNotNull(line.get("STOCKPLACE")));
			item.setDINCLASSID(getStringNotNull(line.get("DINCLASSID")));
			item.setDINCLASSNAME(getStringNotNull(line.get("DINCLASSNAME")));
			item.setWEIGHT(getStringNotNull(line.get("WEIGHT")));
			item.setCOUNTCUTS(getStringNotNull(line.get("COUNTCUTS")));//齿数
			item.setCOUNTSTAGE(getStringNotNull(line.get("COUNTSTAGE")));
			item.setCUTPOSITION(getStringNotNull(line.get("CUTPOSITION")));
			item.setSTATEID1(getStringNotNull(line.get("STATEID1")));
			item.setSTATEID2(getStringNotNull(line.get("STATEID2")));
			item.setCADID(getStringNotNull(line.get("CADID")));
			item.setCADPATH(getStringNotNull(line.get("CADPATH")));
			item.setTOOLTYPE(getStringNotNull(line.get("TOOLTYPE")));
			item.setMETRICORINCH(getStringNotNull(line.get("METRICORINCH")));
			item.setVALVALNUM1(getStringNotNull(line.get("VALVALNUM1")));
			item.setVALVALNUM2(getStringNotNull(line.get("VALVALNUM2")));
			item.setVALVALNUM3(getStringNotNull(line.get("VALVALNUM3")));

			items.add(item);
		}
		return items;
	}

	private String getStringNotNull(Object o) {
		if (o == null)
			return null;

		return o.toString();
	}

	
	public Map<String, String> findCutDetail(String toolID){

		String sql = "SELECT    TDM_TOOL.TOOLID,    TDM_TOOL.ISMULTITOOL,    TDM_TOOL.ISMULTITOOL,    ISNULL(TDM_TOOL.NAME01, TDM_TOOL.NAME) TOOLNAME,    ISNULL(TDM_TOOL.NAME201, TDM_TOOL.NAME2) TOOLNAME2,    TDM_TOOL.TOOLCLASSID,    TDM_TOOL.TOOLCLASSID as TMP_TOOLCLASSID,    ISNULL(TDM_TOOL.TOOLCLASSNAME01, TDM_TOOL.TOOLCLASSNAME) TOOLCLASSNAME,    ISNULL(TDM_TOOL.TOOLCLASSNAME01, TDM_TOOL.TOOLCLASSNAME) TMP_TOOLCLASSNAME,    TDM_TOOL.TOOLGROUPID,    ISNULL(TDM_TOOL.TOOLGROUPNAME01, TDM_TOOL.TOOLGROUPNAME) TOOLGROUPNAME,    TDM_TOOL.ADAPTERID,    ISNULL(TDM_TOOL.ADAPTERNAME01, TDM_TOOL.ADAPTERNAME) ADAPTERNAME,    TDM_TOOL.CUTGRADEID,    ISNULL(TDM_TOOL.CUTGRADENAME01, TDM_TOOL.CUTGRADENAME) CUTGRADENAME,    TDM_TOOL.WEIGHT,    TDM_TOOL.STOCKPLACE,    TDM_TOOL.DINCLASSID,    ISNULL(TDM_TOOL.DINCLASSNAME01, TDM_TOOL.DINCLASSNAME) DINCLASSNAME,    TDM_TOOL.TOOLNUMBER,    TDM_TOOL.COUNTCUTS,    TDM_TOOL.COUNTSTAGE,    TDM_TOOL.CUTPOSITION,    TDM_TOOL.STATEID1,    ISNULL(TMS_STATE1.NAME01, TMS_STATE1.NAME) STATENAME,    TDM_TOOL.STATEID2,    ISNULL(TMS_STATE2.NAME01, TMS_STATE2.NAME) STATENAME2,    TDM_TOOL.TOOLTYPE,    TDM_TOOL.METRICORINCH,    TDM_TOOL.CADID,    TDM_TOOL.CADID,       CASE TDM_TOOL.INFOFLAG     WHEN 1 THEN N'hook'     ELSE null    END,       CASE TDM_TOOL.VALUESFLAG     WHEN 1 THEN N'hook'     ELSE null    END,       CASE TDM_TOOL.TPSVALUESFLAG     WHEN 1 THEN N'hook'     ELSE null    END,       CASE TDM_TOOL.HISVALUESFLAG     WHEN 1 THEN N'hook'     ELSE null    END,       CASE TDM_TOOL.GRDVALUESFLAG     WHEN 1 THEN N'hook'     ELSE null    END,       CASE     WHEN TDM_TOOL.COLLISIONCOUNT = 0 THEN null     ELSE N'hook'    END,       CASE     WHEN TDM_TOOL.LISTCOUNT = 0 THEN null     ELSE N'hook'    END,       CASE     WHEN TDM_TOOL.TECHNOCOUNT = 0 THEN null     ELSE N'hook'    END,       CASE     WHEN TDM_TOOL.INVCOUNT = 0 THEN null     ELSE N'hook'    END,       CASE     WHEN TDM_TOOL.CHANGEINFOCOUNT = 0 THEN null     ELSE N'hook'    END,    TDM_TOOL.COLLISIONCOUNT,    TDM_TOOL.LISTCOUNT,    TDM_TOOL.TECHNOCOUNT,    TDM_TOOL.INVCOUNT,    TDM_TOOL.CHANGEINFOCOUNT,    TDM_TOOL.ACCESSCODE FROM  TDM_TOOLEXV TDM_TOOL  LEFT OUTER JOIN  TMS_STATE TMS_STATE1  ON  TDM_TOOL.STATEID1  = TMS_STATE1.STATEID   AND TMS_STATE1.TNAME  = N'TDM_TOOL'   LEFT OUTER JOIN  TMS_STATE TMS_STATE2  ON  TDM_TOOL.STATEID2  = TMS_STATE2.STATEID   AND TMS_STATE2.TNAME  = N'TDM_TOOL'   WHERE  TDM_TOOL.TOOLID  like N'"+toolID+"%' ORDER BY 1  ";

		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		Map<String,String> cutDetail = new HashMap<String,String>();
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			cutDetail.put("TOOLID", getStringNotNull(line.get("TOOLID")));
			cutDetail.put("TOOLNAME", getStringNotNull(line.get("TOOLNAME")));
			cutDetail.put("TOOLNAME2", getStringNotNull(line.get("TOOLNAME2")));
			cutDetail.put("TOOLCLASSID", getStringNotNull(line.get("TOOLCLASSID")));
			cutDetail.put("TOOLCLASSNAME", getStringNotNull(line.get("TOOLCLASSNAME")));
			cutDetail.put("TOOLGROUPID", getStringNotNull(line.get("TOOLGROUPID")));
			cutDetail.put("TOOLGROUPNAME", getStringNotNull(line.get("TOOLGROUPNAME")));
			cutDetail.put("COUNTCUTS", getStringNotNull(line.get("COUNTCUTS")));
		}

		return cutDetail;
		
	}
	
	/**
	 * 获取主数据
	 * @param TOOLID
	 * @param tClass
	 * @return
	 */
	public List<Result> findResult(String TOOLID, String tClass) {

		String sql = "SELECT    ISNULL(TDM_TOOLVALUES.EMPTYFLAG, 0),    TDM_TOOLCLASSFIELDS.FIELDTYPEEX,   "
				+ " MIN(TMS_FUNCTYPEVALUES.FUNCTYPEID) F2Flag,    ISNULL(TDM_TOOLCLASSFIELDS.NAME01, "
				+ "TDM_TOOLCLASSFIELDS.NAME) TOOLCLASSFIELDSNAME,    ISNULL(TDM_TOOLVALUES.VAL, CONVERT(VARCHAR(23),"
				+ " ISNULL(TDM_TOOLVALUES.VALNUM, null))) VALVALNUM,    ISNULL(ISNULL(ISNULL(TMS_UNIT.NAME01, TMS_UNIT.NAME),"
				+ " FT_UNIT.NAME01), FT_UNIT.NAME) UNITNAME,    ISNULL(TDM_TOOLCLASSFIELDS.NAME201, TDM_TOOLCLASSFIELDS.NAME2) TOOLCLASSFIELDSNAME2, "
				+ "   TDM_TOOLCLASSFIELDS.TOOLCLASSFIELDSPOS,    TDM_TOOLVALUES.VAL,    TDM_TOOLVALUES.VALNUM,    ISNULL(TDM_TOOLCLASSFIELDS.UNITNR, "
				+ "FT_UNIT.UNITNR) UNITNR,    ISNULL(TDM_TOOLCLASSFIELDS.FIELDTYPEID, TMS_FUNCTYPE.FIELDTYPEID) FIELDTYPEID,  "
				+ "  TDM_TOOLCLASSFIELDS.FUNCTYPEID,    TDM_TOOLCLASSFIELDS.INDEXID,    N'0' ModifiedFlag,    TDM_TOOLCLASSFIELDS.POS FROM "
				+ " TMS_FUNCTYPE  LEFT OUTER JOIN  TMS_UNIT FT_UNIT  ON  TMS_FUNCTYPE.UNITNR  = FT_UNIT.UNITNR ,   TDM_TOOLCLASSFIELDS "
				+ " LEFT OUTER JOIN  TMS_FUNCTYPEVALUES  ON  TDM_TOOLCLASSFIELDS.TOOLCLASSID  = ISNULL(TMS_FUNCTYPEVALUES.CLASSID, "
				+ "TDM_TOOLCLASSFIELDS.TOOLCLASSID)   AND TDM_TOOLCLASSFIELDS.FUNCTYPEID  = TMS_FUNCTYPEVALUES.FUNCTYPEID   LEFT OUTER JOIN "
				+ " TMS_UNIT  ON  TDM_TOOLCLASSFIELDS.UNITNR  = TMS_UNIT.UNITNR   LEFT OUTER JOIN  TDM_TOOLVALUES  ON "
				+ " TDM_TOOLCLASSFIELDS.TOOLCLASSFIELDSPOS  = TDM_TOOLVALUES.TOOLCLASSFIELDSPOS   AND TDM_TOOLCLASSFIELDS.INDEXID  = TDM_TOOLVALUES.INDEXID "
				+ "  AND TDM_TOOLVALUES.TOOLID  = N'"+TOOLID+"'   WHERE  TDM_TOOLCLASSFIELDS.TOOLCLASSID  = N'"+tClass+"' "
				+ " AND ISNULL(TDM_TOOLCLASSFIELDS.COMPORTOOL, 3)  in ( 2  , 3  )  AND TDM_TOOLCLASSFIELDS.MOD  = TMS_FUNCTYPE.MOD "
				+ " AND TDM_TOOLCLASSFIELDS.FUNCTYPEID  = TMS_FUNCTYPE.FUNCTYPEID GROUP BY TDM_TOOLCLASSFIELDS.POS, "
				+ "  TDM_TOOLCLASSFIELDS.TOOLCLASSFIELDSPOS,   TDM_TOOLVALUES.EMPTYFLAG,   TDM_TOOLCLASSFIELDS.FIELDTYPEEX, "
				+ "  TDM_TOOLCLASSFIELDS.NAME,   TDM_TOOLCLASSFIELDS.NAME01,   TMS_UNIT.NAME,   TMS_UNIT.NAME01,  "
				+ " TDM_TOOLCLASSFIELDS.NAME2,   TDM_TOOLCLASSFIELDS.NAME201,   TDM_TOOLVALUES.VAL,   TDM_TOOLVALUES.VALNUM,  "
				+ " ISNULL(TDM_TOOLCLASSFIELDS.UNITNR, FT_UNIT.UNITNR),   ISNULL(TDM_TOOLCLASSFIELDS.FIELDTYPEID, TMS_FUNCTYPE.FIELDTYPEID), "
				+ "  TDM_TOOLCLASSFIELDS.FUNCTYPEID,   TDM_TOOLCLASSFIELDS.INDEXID,   FT_UNIT.NAME01,   FT_UNIT.NAME,"
				+ "   ISNULL(TDM_TOOLCLASSFIELDS.UNITNR, FT_UNIT.UNITNR),    ISNULL(TDM_TOOLCLASSFIELDS.FIELDTYPEID, TMS_FUNCTYPE.FIELDTYPEID) "
				+ " ORDER BY ISNULL(TDM_TOOLCLASSFIELDS.POS, 9999),   TDM_TOOLCLASSFIELDS.TOOLCLASSFIELDSPOS  ";

		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		Result result = null;
		List<Result> results = new ArrayList<Result>();
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			result = new Result();
			result.setTOOLCLASSFIELDSNAME(getStringNotNull(line.get("TOOLCLASSFIELDSNAME")));
			result.setVALVALNUM(getStringNotNull(line.get("VALVALNUM")));
			result.setUNITNAME(getStringNotNull(line.get("UNITNAME")));
			result.setTOOLCLASSFIELDSNAME2(getStringNotNull(line.get("TOOLCLASSFIELDSNAME2")));
			result.setTOOLCLASSFIELDSPOS(getStringNotNull(line.get("TOOLCLASSFIELDSPOS")));

			results.add(result);
		}

		return results;
	}
	
	/**
	 * 获取进给参数
	 * @param TOOLID
	 * @return
	 */
	public List<Param> findParam(String TOOLID){
		String sql = "SELECT DISTINCT    TDM_TOOLTECHNO.TOOLID,       CASE     WHEN TMS_INFO.ID IS null THEN N'noinfo'    "
				+ " ELSE N'info'    END bbInfo,    TDM_TOOLTECHNO.TOOLTECHNOPOS,    TDM_TOOLTECHNO.TECHNOCLASSID,  "
				+ "  ISNULL(TDM_TECHNOCLASS.NAME01, TDM_TECHNOCLASS.NAME) TDM_TECHNOCLASSNAME,    TDM_TOOLTECHNO.TECHNOGROUPID,  "
				+ "  ISNULL(TDM_TECHNOGROUP.NAME01, TDM_TECHNOGROUP.NAME) TDM_TECHNOGROUPNAME,    TDM_TOOLTECHNO.MATERIALID,  "
				+ "  ISNULL(TDM_MATERIAL.NAME01, TDM_MATERIAL.NAME) TDM_MATERIALNAME,    TDM_TOOLTECHNO.MATERIALGROUPID,   "
				+ " ISNULL(TDM_MATERIALGROUP.NAME01, TDM_MATERIALGROUP.NAME) TDM_MATERIALGROUPNAME,    TDM_TOOLTECHNO.CUTGRADEID,   "
				+ " ISNULL(TDM_CUTGRADE.NAME01, TDM_CUTGRADE.NAME) TDM_CUTGRADENAME,    TDM_TOOLTECHNO.CUTGRADEGROUPID,   "
				+ " ISNULL(TDM_CUTGRADEGROUP.NAME01, TDM_CUTGRADEGROUP.NAME) TDM_CUTGRADEGROUPNAME,    TDM_TOOLTECHNO.MACHINEID,  "
				+ "  ISNULL(TDM_MACHINE.NAME01, TDM_MACHINE.NAME) TDM_MACHINENAME,    TDM_TOOLTECHNO.MACHINEGROUPID,   "
				+ " ISNULL(TDM_MACHINEGROUP.NAME01, TDM_MACHINEGROUP.NAME) TDM_MACHINEGROUPNAME,    ISNULL(TDM_TOOLTECHNO.NOTE01,"
				+ " TDM_TOOLTECHNO.NOTE) NOTE,    TDM_TOOLTECHNOLIST.TOOLTECHNOLISTPOS,    TDM_TOOLTECHNOLIST.REVOLUTIONS,  "
				+ "  TDM_TOOLTECHNOLIST.CUTSPEED,    TDM_TOOLTECHNOLIST.FEEDRATE,    TDM_TOOLTECHNOLIST.FEEDPTOOTH,   "
				+ " TDM_TOOLTECHNOLIST.FEEDPREV,    TDM_TOOLTECHNOLIST.DIAMETER,    TDM_TOOLTECHNOLIST.CUTVOLUMEPTIME,  "
				+ "  TDM_TOOLTECHNOLIST.CUTDEPTH,    TDM_TOOLTECHNOLIST.CUTWIDTH,    TDM_TOOLTECHNOLIST.TOOLLIFE,  "
				+ "  TDM_TOOLTECHNOLIST.WARNINGLIFE,    TDM_TOOLTECHNOLIST.TOOLLIFECOUNT,    TDM_TOOLTECHNOLIST.TOOLLIFEWAY,  "
				+ "  ISNULL(TDM_TOOLTECHNOLIST.PROCEDURE01, TDM_TOOLTECHNOLIST.[PROCEDURE]) [PROCEDURE],   "
				+ " N'NUMBER(6)' FIELDTYPEID_NUMBER_6,    N'NUMBER(4)' FIELDTYPEID_NUMBER_4,    N'NUMBER(4,4)' FIELDTYPEID_NUMBER_4_4, "
				+ "   N'NUMBER(5,4)' FIELDTYPEID_NUMBER_5_4,    N'NUMBER(6,2)' FIELDTYPEID_NUMBER_6_2,  "
				+ "  N'NUMBER(6,4)' FIELDTYPEID_NUMBER_6_4,    N'CHAR(20)' FIELDTYPEID_CHAR_20,    TDM_TOOLTECHNO.MACHINELISTPOS,  "
				+ "  ISNULL(TDM_MACHINELIST.NAME01, TDM_MACHINELIST.NAME) TDM_MACHINELISTNAME,    TDM_TECHNOCLASS.TECHNOTYPE, "
				+ "   TDM_TECHNOCLASSTYPE.CALCULATIONTYPE FROM  TMS_INFO  RIGHT OUTER JOIN  TDM_TOOLTECHNO  ON "
				+ " TMS_INFO.ID  = TDM_TOOLTECHNO.TOOLID   AND TMS_INFO.ID2  = TDM_TOOLTECHNO.TOOLTECHNOPOS  "
				+ " AND TMS_INFO.TNAME  = N'TDM_TOOLTECHNO'   LEFT OUTER JOIN  TDM_TECHNOCLASS  ON "
				+ " TDM_TOOLTECHNO.TECHNOCLASSID  = TDM_TECHNOCLASS.TECHNOCLASSID   LEFT OUTER JOIN  TDM_TECHNOGROUP  ON "
				+ " TDM_TOOLTECHNO.TECHNOCLASSID  = TDM_TECHNOGROUP.TECHNOCLASSID   AND TDM_TOOLTECHNO.TECHNOGROUPID  = TDM_TECHNOGROUP.TECHNOGROUPID  "
				+ " LEFT OUTER JOIN  TDM_MATERIAL  ON  TDM_TOOLTECHNO.MATERIALID  = TDM_MATERIAL.MATERIALID   LEFT OUTER JOIN "
				+ " TDM_MATERIALGROUP  ON  TDM_TOOLTECHNO.MATERIALGROUPID  = TDM_MATERIALGROUP.MATERIALGROUPID   LEFT OUTER JOIN  TDM_CUTGRADE "
				+ " ON  TDM_TOOLTECHNO.CUTGRADEID  = TDM_CUTGRADE.CUTGRADEID   LEFT OUTER JOIN  TDM_CUTGRADEGROUP  ON  TDM_TOOLTECHNO.CUTGRADEGROUPID  = TDM_CUTGRADEGROUP.CUTGRADEGROUPID "
				+ " LEFT OUTER JOIN  TDM_MACHINE  ON  TDM_TOOLTECHNO.MACHINEID  = TDM_MACHINE.MACHINEID   LEFT OUTER JOIN  TDM_MACHINEGROUP "
				+ " ON  TDM_TOOLTECHNO.MACHINEGROUPID  = TDM_MACHINEGROUP.MACHINEGROUPID   LEFT OUTER JOIN  TDM_TOOLTECHNOLIST  ON  TDM_TOOLTECHNO.TOOLID "
				+ " = TDM_TOOLTECHNOLIST.TOOLID   AND TDM_TOOLTECHNO.TOOLTECHNOPOS  = TDM_TOOLTECHNOLIST.TOOLTECHNOPOS   AND 1  = TDM_TOOLTECHNOLIST.TOOLTECHNOLISTPOS "
				+ "  LEFT OUTER JOIN  TDM_MACHINELIST  ON  TDM_TOOLTECHNO.MACHINEID  = TDM_MACHINELIST.MACHINEID   AND TDM_TOOLTECHNO.MACHINELISTPOS  = TDM_MACHINELIST.MACHINELISTPOS "
				+ "  LEFT OUTER JOIN  TDM_TECHNOCLASSTYPE  ON  TDM_TECHNOCLASS.TECHNOCLASSTYPE  = TDM_TECHNOCLASSTYPE.TECHNOCLASSTYPE "
				+ "  WHERE  TDM_TOOLTECHNO.TOOLID  = N'"+TOOLID+"' ORDER BY TDM_TOOLTECHNO.TOOLID,   TDM_TOOLTECHNO.TOOLTECHNOPOS  ";
		
		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		Param param = null;
		List<Param> params = new ArrayList<Param>();
		int n = 0;
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			param = new Param();
			param.setNO("" + n++);
			param.setTECHNOCLASSID(getStringNotNull(line.get("TECHNOCLASSID")));
			param.setTECHNOCLASSNAME(getStringNotNull(line.get("TDM_TECHNOCLASSNAME")));
			param.setTECHNOGROUPID(getStringNotNull(line.get("TECHNOGROUPID")));
			param.setTECHNOGROUPNAME(getStringNotNull(line.get("TDM_TECHNOGROUPNAME")));
			param.setMATERIALID(getStringNotNull(line.get("MATERIALID")));
			param.setMATERIALNAME(getStringNotNull(line.get("TDM_MATERIALNAME")));
			param.setMATERIALGROUPID(getStringNotNull(line.get("MATERIALGROUPID")));
			param.setMATERIALGROUPNAME(getStringNotNull(line.get("TDM_MATERIALGROUPNAME")));
			param.setREVOLUTIONS(getStringNotNull(line.get("REVOLUTIONS")));
			param.setCUTSPEED(getStringNotNull(line.get("CUTSPEED")));
			param.setFEEDRATE(getStringNotNull(line.get("FEEDRATE")));
			param.setFEEDPTOOTH(getStringNotNull(line.get("FEEDPTOOTH")));
			param.setFEEDPREV(getStringNotNull(line.get("FEEDPREV")));
			
			params.add(param);
		}

		return params;
		
	}
	

}
