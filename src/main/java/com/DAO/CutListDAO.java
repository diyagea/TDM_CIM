package com.DAO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.model.Item;
import com.model.Result;
import com.model.ToolClass;
import com.model.ToolClassType;
import com.model.ToolGroup;

@Repository
public class CutListDAO {

	@Autowired
	private JdbcTemplate jdbcTemplate;

	private String getStringNotNull(Object o) {
		if (o == null)
			return null;
		return o.toString();
	}

	
	@SuppressWarnings("rawtypes")
	public List<Map<String,String>> findCutList() {
		String sql = "SELECT    TDM_LIST.NCPROGRAM,    TDM_LIST.LISTID,    ISNULL(TDM_LIST.PARTNAME01, TDM_LIST.PARTNAME) PARTNAME FROM  TDM_LIST  WHERE  TDM_LIST.NCPROGRAM  >= N' ' ORDER BY 1   ";
		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		List<Map<String,String>> lists = new ArrayList<Map<String,String>>();
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			Map<String, String> m = new HashMap<String,String>();
			
			m.put("NCPROGRAM", getStringNotNull(line.get("NCPROGRAM")));
			m.put("LISTID", getStringNotNull(line.get("LISTID")));
			m.put("PARTNAME", getStringNotNull(line.get("PARTNAME")));

			lists.add(m);
		}
		return lists;
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map findCutListDetail(String listID) {
		String sql = "SELECT    TDM_LIST.LISTID,    TDM_LIST.NCPROGRAM,    ISNULL(TDM_LIST.PARTNAME01, TDM_LIST.PARTNAME) PARTNAME,    TDM_LIST.WORKPIECEDRAWING,    TDM_LIST.JOBPLAN,    TDM_LIST.WORKPROCESS,    TDM_LIST.MATERIALID,    ISNULL(TDM_LIST.MATERIALNAME01, TDM_LIST.MATERIALNAME) MATERIALNAME,    TDM_LIST.MACHINEID,    ISNULL(TDM_LIST.MACHINENAME01, TDM_LIST.MACHINENAME) MACHINENAME,    TDM_LIST.MACHINEGROUPID,    ISNULL(TDM_LIST.MACHINEGROUPNAME01, TDM_LIST.MACHINEGROUPNAME) MACHINEGROUPNAME,    TDM_LIST.FIXTURE,    ISNULL(TDM_LIST.NOTE01, TDM_LIST.NOTE) NOTE,    TDM_LIST.WORKPIECECLASSID,    TDM_LIST.WORKPIECECLASSID TMP_WORKPIECECLASSID,    ISNULL(TDM_LIST.WORKPIECECLASSNAME01, TDM_LIST.WORKPIECECLASSNAME) WORKPIECECLASSNAME,    ISNULL(TDM_LIST.WORKPIECECLASSNAME01, TDM_LIST.WORKPIECECLASSNAME) TMP_WORKPIECECLASSNAME,    TDM_LIST.STATEID1,    ISNULL(TMS_STATE1.NAME01, TMS_STATE1.NAME) STATENAME,    TDM_LIST.STATEID2,    ISNULL(TMS_STATE2.NAME01, TMS_STATE2.NAME) STATENAME2,    TDM_LIST.LISTTYPE,    TDM_LIST.USERNAME,       CASE TDM_LIST.INFOFLAG     WHEN N'1' THEN N'hook'     ELSE null    END,       CASE     WHEN TDM_LIST.LISTLISTCOUNT = N'0' THEN null     ELSE N'hook'    END,       CASE     WHEN TDM_LIST.LISTGRAPHCOUNT = N'0' THEN null     ELSE N'hook'    END,    TDM_LIST.LISTLISTCOUNT,    TDM_LIST.LISTGRAPHCOUNT,       CASE     WHEN TDM_LIST.CHANGEINFOCOUNT = N'0' THEN null     ELSE N'hook'    END,    TDM_LIST.CHANGEINFOCOUNT,    TDM_LIST.LISTTYPE,    TDM_LIST.LISTTYPE,       CASE TDM_LIST.VALUESFLAG     WHEN N'1' THEN N'hook'     ELSE null    END,    TDM_LIST.REFARCID FROM  TDM_LISTEXV TDM_LIST  LEFT OUTER JOIN  TMS_STATE TMS_STATE1  ON  TDM_LIST.STATEID1  = TMS_STATE1.STATEID   AND TMS_STATE1.TNAME  = N'TDM_LIST'   LEFT OUTER JOIN  TMS_STATE TMS_STATE2  ON  TDM_LIST.STATEID2  = TMS_STATE2.STATEID   AND TMS_STATE2.TNAME  = N'TDM_LIST'   WHERE  TDM_LIST.LISTID  like N'"+listID+"%' ORDER BY 1  ";
		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		Map m = new HashMap();
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			m.put("LISTID", getStringNotNull(line.get("LISTID")));
			m.put("NCPROGRAM", getStringNotNull(line.get("NCPROGRAM")));
			m.put("PARTNAME", getStringNotNull(line.get("PARTNAME")));
			m.put("WORKPIECEDRAWING", getStringNotNull(line.get("WORKPIECEDRAWING")));
			m.put("JOBPLAN", getStringNotNull(line.get("JOBPLAN")));
			m.put("WORKPROCESS", getStringNotNull(line.get("WORKPROCESS")));
			m.put("MACHINENAME", getStringNotNull(line.get("MACHINENAME")));
			m.put("MACHINEGROUPNAME", getStringNotNull(line.get("MACHINEGROUPNAME")));
		}
		return m;
	}
	public List<Map<String, String>> findCutList(String listID){
		String sql = "SELECT    TDM_LISTLIST.LISTID,    TDM_LISTLIST.LISTLISTPOS,       CASE DBO.TMS_INFO_FC(N'TDM_LISTLIST', TDM_LISTLIST.LISTID, TDM_LISTLIST.LISTLISTPOS, null, null, null, N'01')     WHEN 0 THEN N'noinfo'     ELSE N'info'    END bbListInfo,       CASE DBO.TMS_INFO_FC(CASE TDM_LISTLIST.IDTYPE       WHEN 1 THEN N'TDM_COMP'       WHEN 2 THEN N'TDM_TOOL'       WHEN 3 THEN N'PMU_MEASURE'       WHEN 4 THEN N'FIX_FIXTURE'       WHEN 5 THEN N'TDM_LIST'       ELSE null      END, TDM_LISTLIST.ID, null, null, null, null, N'01')     WHEN 0 THEN       CASE TDM_LISTLIST.IDTYPE        WHEN 1 THEN N'noinfocomp'        WHEN 2 THEN N'noinfotool'        WHEN 3 THEN N'noinfomeas'        WHEN 4 THEN N'noinfofixt'        WHEN 5 THEN N'noinfolist'        ELSE null       END     ELSE       CASE TDM_LISTLIST.IDTYPE        WHEN 1 THEN N'infocomp'        WHEN 2 THEN N'infotool'        WHEN 3 THEN N'infomeas'        WHEN 4 THEN N'infofixt'        WHEN 5 THEN N'infolist'        ELSE null       END    END bbInfo,    TDM_LISTLIST.IDTYPE,    TDM_LISTLIST.ID,    DBO.tms_idv_f_getname(TDM_LISTLIST.IDTYPE, TDM_LISTLIST.ID, N'01') NAME,    DBO.tms_idv_f_getname2(TDM_LISTLIST.IDTYPE, TDM_LISTLIST.ID, N'01') NAME2,    TDM_LISTLIST.TOOLNUMBER,    TDM_LISTLIST.DUPLONUMBER,    TDM_LISTLIST.TECHNOPOS,    TDM_LISTLIST.SUBROUTINE,    TDM_LISTLIST.MAGAZINE,    TDM_LISTLIST.CUTTIME,    TDM_LISTLIST.QUANTITY,    TDM_LISTLIST.VAL1,    TDM_LISTLIST.VAL2,    TDM_LISTLIST.VAL3,    TDM_LISTLIST.VAL4,    TDM_LISTLIST.VAL5,    TDM_LISTLIST.VAL6,    TDM_LISTLIST.VAL7,    TDM_LISTLIST.VAL8,    TDM_LISTLIST.VAL9,    TDM_LISTLIST.VAL10,    TDM_LISTLIST.SPINDLE,    DBO.tms_idv_f_getcadid(TDM_LISTLIST.IDTYPE, TDM_LISTLIST.ID) CADID,       CASE TDM_LISTLIST.IDTYPE     WHEN 1 THEN N'TDM_COMPDXF'     WHEN 2 THEN N'TDM_TOOLDXF'     WHEN 3 THEN N'PMU_MEASUREDXF'     WHEN 4 THEN N'FIX_FIXTUREDXF'     WHEN 5 THEN N'TDM_LISTDXF'     ELSE null    END ENVVAR,    TDM_TOOLMTV.TOOLTYPE TOOLTYPE,    N'0',       CASE DBO.TDM_LISTLISTCORRECTION_FC(TDM_LISTLIST.LISTID, TDM_LISTLIST.LISTLISTPOS)     WHEN 0 THEN N'0'     ELSE N'1'    END,    TDM_LISTLIST.CUTPOSITION,    TDM_TOOLMTV.MTTYPE,    TDM_TOOLMTV.MTID,    ISNULL(TDM_TOOLMTV.MTNAME01, TDM_TOOLMTV.MTNAME) MTNAME,    ISNULL(TDM_TOOLMTV.MTNAME201, TDM_TOOLMTV.MTNAME2) MTNAME2 FROM  TDM_LISTLIST  LEFT OUTER JOIN  TDM_TOOLMTV  ON  TDM_LISTLIST.ID  = TDM_TOOLMTV.TOOLID   WHERE  TDM_LISTLIST.LISTID  = N'"+listID+"' ORDER BY 1,   2  ";
		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		List<Map<String,String>> lists = new ArrayList<Map<String,String>>();
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			Map<String, String> m = new HashMap<String,String>();
			
			m.put("ID", getStringNotNull(line.get("ID")));
			m.put("NAME", getStringNotNull(line.get("NAME")));
			m.put("NAME2", getStringNotNull(line.get("NAME2")));
			m.put("TOOLNUMBER", getStringNotNull(line.get("TOOLNUMBER")));

			lists.add(m);
		}
		return lists;
	
	}
	

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public Map findToolDetail(String itemID) {
		String sql = "SELECT    TDM_TOOL.TOOLID,    TDM_TOOL.ISMULTITOOL,    TDM_TOOL.ISMULTITOOL,    ISNULL(TDM_TOOL.NAME01, TDM_TOOL.NAME) TOOLNAME,    ISNULL(TDM_TOOL.NAME201, TDM_TOOL.NAME2) TOOLNAME2,    TDM_TOOL.TOOLCLASSID,    TDM_TOOL.TOOLCLASSID as TMP_TOOLCLASSID,    ISNULL(TDM_TOOL.TOOLCLASSNAME01, TDM_TOOL.TOOLCLASSNAME) TOOLCLASSNAME,    ISNULL(TDM_TOOL.TOOLCLASSNAME01, TDM_TOOL.TOOLCLASSNAME) TMP_TOOLCLASSNAME,    TDM_TOOL.TOOLGROUPID,    ISNULL(TDM_TOOL.TOOLGROUPNAME01, TDM_TOOL.TOOLGROUPNAME) TOOLGROUPNAME,    TDM_TOOL.ADAPTERID,    ISNULL(TDM_TOOL.ADAPTERNAME01, TDM_TOOL.ADAPTERNAME) ADAPTERNAME,    TDM_TOOL.CUTGRADEID,    ISNULL(TDM_TOOL.CUTGRADENAME01, TDM_TOOL.CUTGRADENAME) CUTGRADENAME,    TDM_TOOL.WEIGHT,    TDM_TOOL.STOCKPLACE,    TDM_TOOL.DINCLASSID,    ISNULL(TDM_TOOL.DINCLASSNAME01, TDM_TOOL.DINCLASSNAME) DINCLASSNAME,    TDM_TOOL.TOOLNUMBER,    TDM_TOOL.COUNTCUTS,    TDM_TOOL.COUNTSTAGE,    TDM_TOOL.CUTPOSITION,    TDM_TOOL.STATEID1,    ISNULL(TMS_STATE1.NAME01, TMS_STATE1.NAME) STATENAME,    TDM_TOOL.STATEID2,    ISNULL(TMS_STATE2.NAME01, TMS_STATE2.NAME) STATENAME2,    TDM_TOOL.TOOLTYPE,    TDM_TOOL.METRICORINCH,    TDM_TOOL.CADID,    TDM_TOOL.CADID,       CASE TDM_TOOL.INFOFLAG     WHEN 1 THEN N'hook'     ELSE null    END,       CASE TDM_TOOL.VALUESFLAG     WHEN 1 THEN N'hook'     ELSE null    END,       CASE TDM_TOOL.TPSVALUESFLAG     WHEN 1 THEN N'hook'     ELSE null    END,       CASE TDM_TOOL.HISVALUESFLAG     WHEN 1 THEN N'hook'     ELSE null    END,       CASE TDM_TOOL.GRDVALUESFLAG     WHEN 1 THEN N'hook'     ELSE null    END,       CASE     WHEN TDM_TOOL.COLLISIONCOUNT = 0 THEN null     ELSE N'hook'    END,       CASE     WHEN TDM_TOOL.LISTCOUNT = 0 THEN null     ELSE N'hook'    END,       CASE     WHEN TDM_TOOL.TECHNOCOUNT = 0 THEN null     ELSE N'hook'    END,       CASE     WHEN TDM_TOOL.INVCOUNT = 0 THEN null     ELSE N'hook'    END,       CASE     WHEN TDM_TOOL.CHANGEINFOCOUNT = 0 THEN null     ELSE N'hook'    END,    TDM_TOOL.COLLISIONCOUNT,    TDM_TOOL.LISTCOUNT,    TDM_TOOL.TECHNOCOUNT,    TDM_TOOL.INVCOUNT,    TDM_TOOL.CHANGEINFOCOUNT,    TDM_TOOL.ACCESSCODE FROM  TDM_TOOLEXV TDM_TOOL  LEFT OUTER JOIN  TMS_STATE TMS_STATE1  ON  TDM_TOOL.STATEID1  = TMS_STATE1.STATEID   AND TMS_STATE1.TNAME  = N'TDM_TOOL'   LEFT OUTER JOIN  TMS_STATE TMS_STATE2  ON  TDM_TOOL.STATEID2  = TMS_STATE2.STATEID   AND TMS_STATE2.TNAME  = N'TDM_TOOL'   WHERE  TDM_TOOL.TOOLID  like N'"+itemID+"%' ORDER BY 1  ";
		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		ToolGroup group = null;
		Map m = new HashMap();
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			m.put("TOOLID", getStringNotNull(line.get("TOOLID")));
			m.put("TOOLNAME", getStringNotNull(line.get("TOOLNAME")));
			m.put("TOOLNUMBER", getStringNotNull(line.get("TOOLNUMBER")));
			m.put("TOOLCLASSID", getStringNotNull(line.get("TOOLCLASSID")));
			m.put("TOOLCLASSNAME", getStringNotNull(line.get("TOOLCLASSNAME")));
			m.put("TOOLGROUPID", getStringNotNull(line.get("TOOLGROUPID")));
			m.put("TOOLGROUPNAME", getStringNotNull(line.get("TOOLGROUPNAME")));
		}
		return m;
	}
	
	@SuppressWarnings("unchecked")
	public Map createCutList(List<String> toolIDs, String filenameUPCase) throws Exception{
		/*String sql = "SELECT MAX(TDM_LIST.LISTID)CUTLISTID FROM  TMS.TDM_LIST ";
		List list = jdbcTemplate.queryForList(sql);
		Iterator iterator = list.iterator();
		String cutListID = "";
		while (iterator.hasNext()) {
			Map line = (Map) iterator.next();
			cutListID = getStringNotNull(line.get("CUTLISTID"));
		}
		int id = Integer.parseInt(cutListID.substring(5));
		id++;
		cutListID = "CLGR-" + id;
		String ncProg = "NC-"+ id;
		*/
		String cutListID = filenameUPCase;
		String ncProg = filenameUPCase;
		String timestamp = System.currentTimeMillis() + "";
		timestamp = timestamp.substring(0, 10);
		//创建新刀单
		String createCutListSql = "INSERT INTO  TDM_LIST      ( TIMESTAMP ,    LISTID ,    NCPROGRAM ,    PARTNAME ,    PARTNAME01 ,    WORKPIECEDRAWING ,    JOBPLAN ,    WORKPROCESS ,    MATERIALID ,    MACHINEID ,    MACHINEGROUPID ,   FIXTURE ,    NOTE ,    NOTE01 ,    WORKPIECECLASSID ,    STATEID1 ,    STATEID2 ,    LISTTYPE ,    USERNAME ,    ACCESSCODE )  "
				+ "  VALUES  ( "+timestamp+" ,    N'"+cutListID+"' ,    N'"+ncProg+"' ,    null ,    null ,    null ,    null ,    null ,  null ,    null ,    null ,    null ,    null ,    null ,    null ,    null ,    null ,    2 ,    null ,    null )   ";
		jdbcTemplate.execute(createCutListSql);
		
		//插入刀具
		List<Map> results = new ArrayList<Map>();
		int i = 0;
		for(String toolID : toolIDs){
			String timestamp2 = System.currentTimeMillis() + "";
			timestamp2 = timestamp2.substring(0, 10);
			Map<String,String> cutDetail = findToolDetail(toolID);
			String insertSql = "INSERT INTO  TDM_LISTLIST      ( TIMESTAMP ,    LISTID ,    LISTLISTPOS ,    ID ,    IDTYPE ,    TOOLNUMBER ,    DUPLONUMBER ,    TECHNOPOS ,    SUBROUTINE ,    MAGAZINE ,    CUTTIME ,    QUANTITY ,    VAL1 ,    VAL2 ,    VAL3 ,    VAL4 ,    VAL5 ,    VAL6 ,    VAL7 ,    VAL8 ,    VAL9 ,    VAL10 ,    SPINDLE ,    CUTPOSITION )    VALUES  "
					+ " ( "+timestamp2+" ,    N'"+cutListID+"' ,    "+i+" ,    N'"+toolID+"' ,    2 ,    "+cutDetail.get("TOOLNUMBER")+" ,    null ,    null ,    null ,    null ,    null ,    1 ,    null ,    null ,    null ,    null ,    null ,    null ,    null ,    null ,    null ,    null ,    null ,    null )   ";
			try{
				jdbcTemplate.execute(insertSql);
				cutDetail.put("isDone", "1");
			}catch(Exception e){
				System.out.println("插入失败: "+e);
				cutDetail.put("isDone", "0");
			}
			results.add(cutDetail);
			i++;
		}
		Map m = new HashMap();
		m.put("results", results);
		m.put("cutListID", cutListID);
		m.put("ncProg", ncProg);
		return m;
	}

}
