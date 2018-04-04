package com.DAO;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.support.ResourceTransactionManager;

import com.model.Item;
import com.model.Result;
import com.model.ToolClass;
import com.model.ToolClassType;
import com.model.ToolGroup;

@Repository
public class ToolsDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	public List<ToolClassType> findToolClassTypes() {
		String sql = "SELECT    DRAWING,    ISNULL(NAME01, NAME) NAME," + "    TOOLCLASSTYPE FROM  TDM_TOOLCLASSTYPE"
				+ "  WHERE  TOOLCLASSTYPE  = TOOLCLASSTYPE  AND (ISNULL(COMPORTOOL, 3)"
				+ "  in ( 1  , 3  ))  AND (ACTIV  = 1) ORDER BY TOOLCLASSTYPE,   NAME01,   NAME  ";
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
		String sql = "SELECT    Drawing,    ISNULL(NAME01, NAME) NAME,    "
				+ "TOOLCLASSID FROM  TDM_TOOLCLASS  WHERE  TOOLCLASSTYPE  = N'" + type + "' "
				+ " AND (ISNULL(COMPORTOOL, 3)  in ( 1  , 3  ))  AND (ACTIV  = 1) ORDER BY POS,"
				+ "   TOOLCLASSID,   NAME01,   NAME  ";
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
		String sql = "SELECT    Drawing,    ISNULL(NAME01, NAME) NAME,    "
				+ "TOOLGROUPID FROM  TDM_TOOLGROUP  WHERE  TOOLCLASSID  = N'" + tClass + "'  "
				+ "AND (ISNULL(COMPORTOOL, 3)  in ( 1  , 3  ))  AND (ACTIV  = 1) ORDER BY POS,   "
				+ "TOOLGROUPID,   NAME01,   NAME  ";
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

	public List<Item> findItems(String type, String tClass, String group) {
		String classSQL = tClass==null? "" : "AND TDM_COMP.TOOLCLASSID  = N'" + tClass + "' ";
		String groupSQL = group==null? "" : " AND TDM_COMP.TOOLGROUPID  = N'" + group + "' ";

		String sql = "SELECT DISTINCT    TDM_COMP.COMPID,    ISNULL(TDM_COMP.NAME01, TDM_COMP.NAME) NAME,    ISNULL(TDM_COMP.NAME201, TDM_COMP.NAME2) NAME2,    TDM_COMP.TOOLCLASSID,    ISNULL(TDM_TOOLCLASS.NAME01, TDM_TOOLCLASS.NAME) TOOLCLASSNAME,    TDM_COMP.TOOLGROUPID,    ISNULL(TDM_TOOLGROUP.NAME01, TDM_TOOLGROUP.NAME) TOOLGROUPNAME,    TDM_COMP.ADAPTERID,    ISNULL(TDM_ADAPTER.NAME01, TDM_ADAPTER.NAME) ADAPTERNAME,    TDM_COMP.CUTGRADEID,    ISNULL(TDM_CUTGRADE.NAME01, "
				+ "TDM_CUTGRADE.NAME) CUTGRADENAME,    TDM_COMP.COUNTCUTS,    TDM_COMP.COUNTSTAGE,    TDM_COMP.DINCLASSID,    ISNULL(TDM_DINCLASS.NAME01, TDM_DINCLASS.NAME) DINCLASSNAME,    TDM_COMP.WEIGHT,    TDM_COMP.STOCKCLASS, "
				+ "TDM_COMP.STATEID1,    TDM_COMP.STATEID2,    TDM_COMP.CADID,    TDM_COMP.TEMPLATE,    TDM_COMP.STOCKPLACE,    TDM_COMP.METRICORINCH,    TDM_COMP.INSERTSIZE1,    TDM_COMP.INSERTSIZE2,    TDM_COMP.INSERTSIZE3,    TDM_COMP.INSERTSIZE4, "
				+ "TDM_COMP.COUNTWSP1,    TDM_COMP.COUNTWSP2,    TDM_COMP.COUNTWSP3,    TDM_COMP.COUNTWSP4,    TDM_COMP.CARTRIDGE1,    TDM_COMP.CARTRIDGE2,    TDM_COMP.COUNTCARTRIDGE1,    TDM_COMP.COUNTCARTRIDGE2,    TDM_COMPCONNECTORIN.CONNECTORID CONNECTORIDIN, "
				+ "TDM_COMPCONNECTORIN.CONNECTORSIZE CONNECTORSIZEIN,    TDM_COMPCONNECTORIN.CONNECTORSIZETO CONNECTORSIZETOIN,    TDM_COMPCONNECTOROUT.CONNECTORID CONNECTORID,    TDM_COMPCONNECTOROUT.CONNECTORSIZE CONNECTORSIZEOUT,    TDM_COMPVALUES1.VALNUM VALVALNUM1,    "
				+ "TDM_COMPVALUES2.VALNUM VALVALNUM2,    TDM_COMPVALUES3.VALNUM VALVALNUM3 FROM  TDM_COMPCONNECTOR TDM_COMPCONNECTORIN  RIGHT OUTER JOIN  TDM_COMP  ON  TDM_COMPCONNECTORIN.COMPID  = TDM_COMP.COMPID   AND TDM_COMPCONNECTORIN.COMPCONNECTORPOS  = 1   AND TDM_COMPCONNECTORIN.COMPCONNECTORTYPE  = 2   "
				+ "LEFT OUTER JOIN  TDM_COMPCONNECTOR TDM_COMPCONNECTOROUT  ON  TDM_COMPCONNECTOROUT.COMPID  = TDM_COMP.COMPID   AND TDM_COMPCONNECTOROUT.COMPCONNECTORPOS  = 1   AND TDM_COMPCONNECTOROUT.COMPCONNECTORTYPE  = 1   LEFT OUTER JOIN  TDM_COMPVALUESEXV TDM_COMPVALUES1  ON  TDM_COMPVALUES1.COMPID  = TDM_COMP.COMPID   "
				+ "AND TDM_COMPVALUES1.FUNCTYPEID  = N'XS'   LEFT OUTER JOIN  TDM_COMPVALUESEXV TDM_COMPVALUES2  ON  TDM_COMPVALUES2.COMPID  = TDM_COMP.COMPID   AND TDM_COMPVALUES2.FUNCTYPEID  = N'YS'   LEFT OUTER JOIN  TDM_COMPVALUESEXV TDM_COMPVALUES3  ON  TDM_COMPVALUES3.COMPID  = TDM_COMP.COMPID   AND TDM_COMPVALUES3.FUNCTYPEID  = N'DC'  "
				+ " LEFT OUTER JOIN  TDM_ADAPTER  ON  TDM_COMP.ADAPTERID  = TDM_ADAPTER.ADAPTERID   LEFT OUTER JOIN  TDM_CUTGRADE  ON  TDM_COMP.CUTGRADEID  = TDM_CUTGRADE.CUTGRADEID   LEFT OUTER JOIN  TDM_DINCLASS  ON  TDM_COMP.DINCLASSID  = TDM_DINCLASS.DINCLASSID ,   TDM_TOOLCLASS,   TDM_TOOLGROUP  WHERE  TDM_COMP.TOOLCLASSID  = TDM_TOOLCLASS.TOOLCLASSID "
				+ " AND TDM_COMP.TOOLCLASSID  = TDM_TOOLGROUP.TOOLCLASSID  AND TDM_COMP.TOOLGROUPID  = TDM_TOOLGROUP.TOOLGROUPID  "
				+ classSQL + groupSQL + " AND TDM_TOOLCLASS.TOOLCLASSTYPE  = " + type + " ORDER BY TDM_COMP.COMPID  ";

		System.out.println(sql);
		
		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		Item item = null;
		List<Item> items = new ArrayList<Item>();
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			item = new Item();

			item.setCOMPID(getStringFromLine(line.get("COMPID")));
			item.setNAME(getStringFromLine(line.get("NAME")));
			item.setNAME2(getStringFromLine(line.get("NAME2")));
			item.setTOOLCLASSID(getStringFromLine(line.get("TOOLCLASSID")));
			item.setTOOLCLASSNAME(getStringFromLine(line.get("TOOLCLASSNAME")));
			item.setTOOLGROUPID(getStringFromLine(line.get("TOOLGROUPID")));
			item.setTOOLGROUPNAME(getStringFromLine(line.get("TOOLGROUPNAME")));
			item.setADAPTERID(getStringFromLine(line.get("ADAPTERID")));
			item.setADAPTERNAME(getStringFromLine(line.get("ADAPTERNAME")));
			item.setCUTGRADEID(getStringFromLine(line.get("CUTGRADEID")));
			item.setCUTGRADENAME(getStringFromLine(line.get("CUTGRADENAME")));
			item.setCOUNTCUTS(getStringFromLine(line.get("COUNTCUTS")));
			item.setCOUNTSTAGE(getStringFromLine(line.get("COUNTSTAGE")));
			item.setDINCLASSID(getStringFromLine(line.get("DINCLASSID")));
			item.setDINCLASSNAME(getStringFromLine(line.get("DINCLASSNAME")));
			item.setWEIGHT(getStringFromLine(line.get("WEIGHT")));
			item.setSTOCKCLASS(getStringFromLine(line.get("STOCKCLASS")));
			item.setSTATEID1(getStringFromLine(line.get("STATEID1")));
			item.setSTATEID2(getStringFromLine(line.get("STATEID2")));
			item.setCADID(getStringFromLine(line.get("CADID")));
			item.setTEMPLATE(getStringFromLine(line.get("TEMPLATE")));
			item.setSTOCKPLACE(getStringFromLine(line.get("STOCKPLACE")));
			item.setMETRICORINCH(getStringFromLine(line.get("METRICORINCH")));
			item.setINSERTSIZE1(getStringFromLine(line.get("INSERTSIZE1")));
			item.setINSERTSIZE2(getStringFromLine(line.get("INSERTSIZE2")));
			item.setINSERTSIZE3(getStringFromLine(line.get("INSERTSIZE3")));
			item.setINSERTSIZE4(getStringFromLine(line.get("INSERTSIZE4")));
			item.setCOUNTWSP1(getStringFromLine(line.get("COUNTWSP1")));
			item.setCOUNTWSP2(getStringFromLine(line.get("COUNTWSP2")));
			item.setCOUNTWSP3(getStringFromLine(line.get("COUNTWSP3")));
			item.setCOUNTWSP4(getStringFromLine(line.get("COUNTWSP4")));
			item.setCARTRIDGE1(getStringFromLine(line.get("CARTRIDGE1")));
			item.setCARTRIDGE2(getStringFromLine(line.get("CARTRIDGE2")));
			item.setCOUNTCARTRIDGE1(getStringFromLine(line.get("COUNTCARTRIDGE1")));
			item.setCOUNTCARTRIDGE2(getStringFromLine(line.get("COUNTCARTRIDGE2")));
			item.setCONNECTORIDIN(getStringFromLine(line.get("CONNECTORIDIN")));
			item.setCONNECTORSIZEIN(getStringFromLine(line.get("CONNECTORSIZEIN")));
			item.setCONNECTORSIZETOIN(getStringFromLine(line.get("CONNECTORSIZETOIN")));
			item.setCONNECTORID(getStringFromLine(line.get("CONNECTORID")));
			item.setCONNECTORSIZEOUT(getStringFromLine(line.get("CONNECTORSIZEOUT")));
			item.setVALVALNUM1(getStringFromLine(line.get("VALVALNUM1")));
			item.setVALVALNUM2(getStringFromLine(line.get("VALVALNUM2")));
			item.setVALVALNUM3(getStringFromLine(line.get("VALVALNUM3")));

			items.add(item);
		}
		return items;
	}

	private String getStringFromLine(Object o) {
		if (o == null)
			return null;

		return o.toString();
	}

	public List<Result> findResult(String COMPID, String tClass) {
		String sql = "SELECT    ISNULL(TDM_COMPVALUES.EMPTYFLAG, 0),    TDM_TOOLCLASSFIELDS.FIELDTYPEEX,    "
				+ "MIN(TMS_FUNCTYPEVALUES.FUNCTYPEID) F2Flag,    ISNULL(TDM_TOOLCLASSFIELDS.NAME01, TDM_TOOLCLASSFIELDS.NAME) TOOLCLASSFIELDSNAME,   "
				+ " ISNULL(TDM_COMPVALUES.VAL, CONVERT(VARCHAR(23), ISNULL(TDM_COMPVALUES.VALNUM, null))) VALVALNUM,   "
				+ " ISNULL(ISNULL(ISNULL(TMS_UNIT.NAME01, TMS_UNIT.NAME), FT_UNIT.NAME01), FT_UNIT.NAME) UNITNAME,   "
				+ " ISNULL(TDM_TOOLCLASSFIELDS.NAME201, TDM_TOOLCLASSFIELDS.NAME2) TOOLCLASSFIELDSNAME2,    TDM_TOOLCLASSFIELDS.TOOLCLASSFIELDSPOS,  "
				+ "  ISNULL(TDM_TOOLCLASSFIELDS.FIELDTYPEID, TMS_FUNCTYPE.FIELDTYPEID) FIELDTYPEID,    TDM_TOOLCLASSFIELDS.FUNCTYPEID,    TDM_COMPVALUES.VAL, "
				+ "   TDM_COMPVALUES.VALNUM,    TDM_TOOLCLASSFIELDS.POS FROM  TMS_FUNCTYPE  LEFT OUTER JOIN  TMS_UNIT FT_UNIT  ON  TMS_FUNCTYPE.UNITNR  = FT_UNIT.UNITNR , "
				+ "  TDM_TOOLCLASSFIELDS  LEFT OUTER JOIN  TMS_FUNCTYPEVALUES  ON  TDM_TOOLCLASSFIELDS.TOOLCLASSID  = ISNULL(TMS_FUNCTYPEVALUES.CLASSID, TDM_TOOLCLASSFIELDS.TOOLCLASSID) "
				+ "  AND TDM_TOOLCLASSFIELDS.FUNCTYPEID  = TMS_FUNCTYPEVALUES.FUNCTYPEID   LEFT OUTER JOIN  TMS_UNIT  ON  TDM_TOOLCLASSFIELDS.UNITNR  = TMS_UNIT.UNITNR   LEFT OUTER JOIN  TDM_COMPVALUES  "
				+ "ON  TDM_TOOLCLASSFIELDS.TOOLCLASSFIELDSPOS  = TDM_COMPVALUES.TOOLCLASSFIELDSPOS   AND TDM_COMPVALUES.COMPID  = N'"
				+ COMPID + "'   " + "WHERE  TDM_TOOLCLASSFIELDS.TOOLCLASSID  = N'" + tClass
				+ "'  AND TDM_TOOLCLASSFIELDS.MOD  = TMS_FUNCTYPE.MOD  AND TDM_TOOLCLASSFIELDS.FUNCTYPEID  = TMS_FUNCTYPE.FUNCTYPEID GROUP BY TDM_TOOLCLASSFIELDS.POS,  "
				+ " TDM_TOOLCLASSFIELDS.TOOLCLASSFIELDSPOS,   TDM_COMPVALUES.EMPTYFLAG,   TDM_TOOLCLASSFIELDS.FIELDTYPEEX,   TDM_TOOLCLASSFIELDS.NAME,   TDM_TOOLCLASSFIELDS.NAME01,   TMS_UNIT.NAME,   TMS_UNIT.NAME01,   TDM_TOOLCLASSFIELDS.NAME2,"
				+ "   TDM_TOOLCLASSFIELDS.NAME201,   TDM_COMPVALUES.VAL,   TDM_COMPVALUES.VALNUM,   TDM_TOOLCLASSFIELDS.UNITNR,   ISNULL(TDM_TOOLCLASSFIELDS.FIELDTYPEID, TMS_FUNCTYPE.FIELDTYPEID),   TDM_TOOLCLASSFIELDS.FUNCTYPEID,   FT_UNIT.NAME01,   FT_UNIT.NAME,"
				+ "    ISNULL(TDM_TOOLCLASSFIELDS.FIELDTYPEID, TMS_FUNCTYPE.FIELDTYPEID)  ORDER BY ISNULL(TDM_TOOLCLASSFIELDS.POS, 9999),   TDM_TOOLCLASSFIELDS.TOOLCLASSFIELDSPOS  ";

		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		Result result = null;
		List<Result> results = new ArrayList<Result>();
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			result = new Result();
			result.setTOOLCLASSFIELDSNAME(getStringFromLine(line.get("TOOLCLASSFIELDSNAME")));
			result.setVALVALNUM(getStringFromLine(line.get("VALVALNUM")));
			result.setUNITNAME(getStringFromLine(line.get("UNITNAME")));
			result.setTOOLCLASSFIELDSNAME2(getStringFromLine(line.get("TOOLCLASSFIELDSNAME2")));
			result.setTOOLCLASSFIELDSPOS(getStringFromLine(line.get("TOOLCLASSFIELDSPOS")));

			results.add(result);
		}

		return results;
	}

}
