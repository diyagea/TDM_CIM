package com.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.DAO.AssembleToolsDAO_oracle;
import com.DAO.CutListDAO_oracle;
import com.model.Param;
import com.model.Result;
import com.util.CSVUtils;

@Controller("chooseCutList")
public class ChooseCutList {

	@Autowired
	private CutListDAO_oracle cutListDAO;
	@Autowired
	private AssembleToolsDAO_oracle assembleToolsDAO;
	
	@RequestMapping(value = "showCutList", method = RequestMethod.GET)
	public String showCutList(Model model) {
		model.addAttribute("cutList", cutListDAO.findCutList());
		
		return "showCutList";
	}

	@RequestMapping(value = "findCutList", method = RequestMethod.GET)
	@SuppressWarnings("rawtypes")
	public String findResult(String listID, HttpSession session) {

		Map listDetail = cutListDAO.findCutListDetail(listID);
		
		List<Map<String, String>> cutList = cutListDAO.findCutList(listID);
		
		Map<String, List> params = new HashMap<String, List>();
		for(Map<String,String> cut : cutList){
			List<Param> ps = assembleToolsDAO.findParam(cut.get("ID"));
			params.put(cut.get("ID"), ps);
		}
		session.setAttribute("listDetail", listDetail);
		session.setAttribute("cutList", cutList);
		session.setAttribute("params", params);
		return "showCutListDetail";
	}
	
	@RequestMapping(value = "findCutResult", method = RequestMethod.GET)
	public String findCutResult(HttpSession session){
		List<Map<String,String>> cuts = (List<Map<String,String>>) session.getAttribute("cutList");
		//TODO
		
		return "showCutResults";
	}
	
	@RequestMapping(value = "downloadCutLIst", method = RequestMethod.GET)
	public void downloadCutList(String paramStr, @Value("${cutListFile.savePath}")String filePath, @Value("${cutListFile.saveName}")String fileName, HttpServletResponse response, HttpSession session) throws Exception{
		Map<String, List<Param>> params = (Map<String, List<Param>>) session.getAttribute("params");
		String[] cuts = paramStr.split("=");
		List<Result> results = new ArrayList<Result>();
		List<Map<String,String>> datas = new ArrayList<Map<String,String>>();
		for(String cut : cuts){
			String[] s = cut.split("\\|");
			Map<String, String> cutMap = assembleToolsDAO.findCutDetail(s[0]);
			List<Result> reustls = assembleToolsDAO.findResult(cutMap.get("TOOLID"), cutMap.get("TOOLCLASSID"));
			List<Param> ps = params.get(s[0]);
			Param p = null;
			try {
				p = ps.get(Integer.parseInt(s[1]));
			} catch (Exception e) {
			}
			//data列表
			datas.add(initDataMap(cutMap, reustls, p));
		}
		
		System.out.println(filePath + fileName);
		try{
			CSVUtils.createCutListFile(datas, filePath, fileName);
			response.getWriter().append("1");
		}catch(Exception e){
			response.getWriter().append("-1");
		}
		
		
	}
	
	
	private Map<String, String> initDataMap(Map<String, String> cutMap, List<Result> results, Param p){
		String compID = cutMap.get("TOOLID");
		String tClass = cutMap.get("TOOLCLASSID");
		String groupID = cutMap.get("TOOLGROUPID");
		String countCuts = cutMap.get("COUNTCUTS");
		Map<String, String> data = new HashMap<String, String>();
		
		data.put("Name", compID);
		data.put("countCuts", countCuts);
		
		for (Result r : results) {
			if (r.getTOOLCLASSFIELDSNAME().equals("Dc")) {
				data.put("Dc", r.getVALVALNUM());
			} else if (r.getTOOLCLASSFIELDSNAME().equals("RE1")) {
				data.put("RE1", r.getVALVALNUM());
			} else if (r.getTOOLCLASSFIELDSNAME().equals("Ltot")) {
				data.put("Ltot", r.getVALVALNUM());
			} else if (r.getTOOLCLASSFIELDSNAME().equals("Ls")) {
				data.put("Ls", r.getVALVALNUM());
			} else if (r.getTOOLCLASSFIELDSNAME().equals("Lpr")) {
				data.put("Lpr", r.getVALVALNUM());
			} else if (r.getTOOLCLASSFIELDSNAME().equals("Xs")) {
				data.put("Xs", r.getVALVALNUM());
			} else if(r.getTOOLCLASSFIELDSNAME().equals("DHB")){
				data.put("DHB", r.getVALVALNUM());
			} else if(r.getTOOLCLASSFIELDSNAME().equals("L4")){
				data.put("L4", r.getVALVALNUM());
			} else if(r.getTOOLCLASSFIELDSNAME().equals("KF")){
				data.put("KF", r.getVALVALNUM());
			}
		}
		
		if (p != null) {
//			data.put("Spin", p.getFEEDRATE());
//			data.put("Feed", p.getREVOLUTIONS());
			data.put("Feed", p.getFEEDRATE());
			data.put("Spin", p.getREVOLUTIONS());
			data.put("Vc", p.getCUTSPEED());
			data.put("Fz", p.getFEEDPTOOTH());
		}
		String[] arr1 = new String[] { "M01", "M02", "M03", "M04" };
		String[] arr2 = new String[] { "M05", "M06" };
		String[] arr3 = new String[] { "D02", "D03", "D04", "D06" };
		String[] arr4 = new String[] { "D09", "D10" };
		String[] arr5 = new String[] { "D11", "D12", "D13", "D14" };
		String[] arr6 = new String[] { "D01" };
		String[] arr7 = new String[] { "M07", "M08" };
		List t1 = Arrays.asList(arr1);
		List t2 = Arrays.asList(arr2);
		List t3 = Arrays.asList(arr3);
		List t4 = Arrays.asList(arr4);
		List t5 = Arrays.asList(arr5);
		List t6 = Arrays.asList(arr6);
		List t7 = Arrays.asList(arr7);
		
		String[] special_M01 = {"03", "04"};
		String[] special_M02 = {"04"};
		String[] special_M05 = {"07", "11"};
		String[] special_M052 = {"09"};
		String[] special_M06 = {"03", "09"};
		List g1 = Arrays.asList(special_M01);
		List g2 = Arrays.asList(special_M02);
		List g3 = Arrays.asList(special_M05);
		List g4 = Arrays.asList(special_M052);
		List g5 = Arrays.asList(special_M06);
		
		data.put("Name", compID);
		
		if (t1.contains(tClass)) {
			if(tClass.equals("M01") && g1.contains(groupID)){
				data.put("isSpecial", "true");
				data.put("specType", "DoveMillingFlat");
				data.put("Tech", "210105");
				data.put("Type", "210201");
			}else if(tClass.equals("M02") && g2.contains(groupID)){
				data.put("isSpecial", "true");
				data.put("specType", "DoveMillingFlat");
				data.put("Tech", "210105");
				data.put("Type", "210201");
			}else{
				data.put("Tech", "210101");
				data.put("Type", "210201");
			}
			
		} else if (t2.contains(tClass)) {
			if(tClass.equals("M05")){
				if(g3.contains(groupID)){
					data.put("isSpecial", "true");
					data.put("specType", "Lollipop");
					data.put("Tech", "210103");
					data.put("Type", "210202");
				}else if(g4.contains(groupID)){
					data.put("isSpecial", "true");
					data.put("specType", "SlotMillingFullRadius");
					data.put("Tech", "210104");
					data.put("Type", "210208");
				}else{
					data.put("Tech", "210101");
					data.put("Type", "210202");
				}
			}else if(tClass.equals("M06") && g5.contains(groupID)){
				data.put("isSpecial", "true");
				data.put("specType", "SlotMillingFullRadius");
				data.put("Tech", "210104");
				data.put("Type", "210208");
			}else{
				data.put("Tech", "210101");
				data.put("Type", "210202");
			}
			
		} else if (t3.contains(tClass)) {
			data.put("Tech", "210102");
			data.put("Type", "210204");
		} else if (t4.contains(tClass)) {
			data.put("Tech", "210102");
			data.put("Type", "210205");
		} else if (t5.contains(tClass)) {
			data.put("Tech", "210102");
			data.put("Type", "210206");
		} else if (t6.contains(tClass)) {
			data.put("Tech", "210102");
			data.put("Type", "210207");
		} else if (t7.contains(tClass)) {//特殊刀具 07 08
			data.put("isSpecial", "true");
			data.put("specType", "SlotMillingFlat");
			data.put("Tech", "210104");
			data.put("Type", "210201");
		} 
		
		return data;
	}

}
