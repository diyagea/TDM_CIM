package com.controller;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.DAO.AssembleToolsDAO_oracle;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.model.Item2;
import com.model.Nodes;
import com.model.Param;
import com.model.Result;
import com.model.ToolClass;
import com.model.ToolClassType;
import com.model.ToolGroup;
import com.util.CSVUtils;

@Controller("chooser")
public class ChooseItem {

	@Autowired
	private AssembleToolsDAO_oracle assembleToolsDAO;

	private static String nodesStr = "";
	
	@RequestMapping(value = "goItemIndex", method = RequestMethod.GET)
	public String goIndex(HttpSession session) {
		if (session.getAttribute("tree") != null) {
			return "showTree";
		}
		if(nodesStr.length()>0){
			session.setAttribute("tree", nodesStr);
			return "showTree";
		}

		// 查询所有Type
		List<ToolClassType> types = assembleToolsDAO.findToolClassTypes();
		// 遍历查询Type下的Class
		for (ToolClassType type : types) {
			List<ToolClass> tempClass = assembleToolsDAO.findToolClasses(type.getToolClassType());
			// 遍历查询Class下的Group
			for (ToolClass tClass : tempClass) {
				// 查到的Group存放在Class中
				List<ToolGroup> tempGroup = assembleToolsDAO.findToolGroups(tClass.getToolClassID());
				tClass.setToolGroup(tempGroup);
			}
			// Class存放在Type中
			type.setToolClass(tempClass);
		}

		List<Nodes> typeNodes = new ArrayList<Nodes>();
		// 构造nodes
		for (ToolClassType type : types) {
			Nodes nodes = new Nodes();
			nodes.setText(type.getName());
			nodes.setHref("choosed?choosed=" + type.getDrawing() + "&type=" + type.getToolClassType() + "&name=" + type.getName());
			// nodes.setTags("" + (type.getToolClass() == null ? 0 :
			// type.getToolClass().size()));
			// 构造子nodes
			List<Nodes> sonNodeList = new ArrayList<Nodes>();
			for (ToolClass tClass : type.getToolClass()) {
				Nodes classNode = new Nodes();
				classNode.setText(tClass.getName());
				classNode.setHref("choosed?choosed=" + tClass.getDrawing() + "&type=" + type.getToolClassType() + "&name=" + tClass.getName());
				// classNode.setTags("" + (tClass.getToolGroup() == null ? 0 :
				// tClass.getToolGroup().size()));
				List<Nodes> gradeSonNodeList = new ArrayList<Nodes>();
				for (ToolGroup group : tClass.getToolGroup()) {
					Nodes groupNode = new Nodes();
					groupNode.setText(group.getName());
					groupNode.setHref("choosed?choosed=" + group.getDrawing() + "&type=" + type.getToolClassType() + "&name=" + group.getName());
					gradeSonNodeList.add(groupNode);
				}
				classNode.setNodes(gradeSonNodeList);
				sonNodeList.add(classNode);

			}
			nodes.setNodes(sonNodeList);
			typeNodes.add(nodes);
		}

		System.out.println(typeNodes);

		Gson gson = new GsonBuilder().disableHtmlEscaping().create();
		nodesStr = gson.toJson(typeNodes);
		System.out.println(nodesStr);
		session.setAttribute("tree", nodesStr);

		return "showTree";
	}

	@RequestMapping(value = "choosed", method = RequestMethod.GET)
	public String choose(String choosed,String name, Model model) {
		model.addAttribute("choosed", choosed);
		model.addAttribute("name", name);
		return "showTree";
	}

	@RequestMapping(value = "search", method = RequestMethod.GET)
	public String search(String choosed, String name, String type, String Dc, String Xs, Model model) {
		List<Item2> items = null;
		if (choosed.endsWith("ing")) {
			items = assembleToolsDAO.findItems(type, null, null, Dc, Xs);
		} else if (!choosed.contains("-")) {
			items = assembleToolsDAO.findItems(type, choosed, null, Dc, Xs);
		} else if (choosed.contains("-")) {
			String tClass = choosed.substring(0, choosed.indexOf("-"));
			String group = choosed.substring(choosed.indexOf("-") + 1);
			items = assembleToolsDAO.findItems(type, tClass, group, Dc, Xs);
		}

		if (items == null)
			return "error";

		model.addAttribute("items", items);

		return "showItems";
	}

	@RequestMapping(value = "findResult", method = RequestMethod.GET)
	public String findResult(String item, HttpSession session) {
		String[] s = item.split("\\|");
		String compID = s[0];
		String tClass = s[1];
		String groupID = s[2];
		String countCuts = s[3];

		List<Result> results = assembleToolsDAO.findResult(compID, tClass);
		List<Param> params = assembleToolsDAO.findParam(compID);

		session.setAttribute("results", results);
		session.setAttribute("params", params);
		session.setAttribute("compID", compID);
		session.setAttribute("countCuts", countCuts);
		session.setAttribute("tClass", tClass);
		session.setAttribute("groupID", groupID);

		return "showResult";
	}

	@SuppressWarnings("rawtypes")
	@RequestMapping(value = "download", method = RequestMethod.GET)
	public void download(String NO, @Value("${file.savePath}")String filePath, @Value("${file.saveName}")String fileName, HttpServletRequest request, HttpServletResponse response, HttpSession session)
			throws Exception {

		@SuppressWarnings("unchecked")
		List<Result> results = (List<Result>) session.getAttribute("results");
		@SuppressWarnings("unchecked")
		List<Param> params = (List<Param>) session.getAttribute("params");
		Param p = null;
		try {
			p = params.get(Integer.parseInt(NO));
		} catch (Exception e) {
		}
		Map<String, String> data = new HashMap<String, String>();

		String countCuts = (String) session.getAttribute("countCuts");
		data.put("countCuts", countCuts);

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
		

		String compID = (String) session.getAttribute("compID");
		String tClass = (String) session.getAttribute("tClass");
		String groupID = (String) session.getAttribute("groupID");
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

		//String path = session.getServletContext().getRealPath("download");
		System.out.println(filePath + fileName);
		try{
			CSVUtils.createCSVFile(data, filePath, fileName);
			response.getWriter().append("1");
		}catch(Exception e){
			response.getWriter().append("-1");
		}

	/*	HttpHeaders headers = new HttpHeaders();
		String fileName = new String((compID + ".csv").getBytes("UTF-8"), "iso-8859-1");// 为了解决中文名称乱码问题
		headers.setContentDispositionFormData("attachment", fileName);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		// Runtime.getRuntime().exec("taskkill /F /IM chrome.exe");
		return new ResponseEntity<byte[]>(FileUtils.readFileToByteArray(file), headers, HttpStatus.CREATED);*/

		/*OutputStream out = null;
		try {
			response.reset();
			response.setContentType("application/octet-stream; charset=utf-8");
			response.setHeader("Content-Disposition", "attachment; filename=" + file.getName());
			out = response.getOutputStream();
			out.write(FileUtils.readFileToByteArray(file));
			out.flush();
			response.getWriter().append("1");
		} catch (IOException e) {
			e.printStackTrace();
			response.getWriter().append("-1");
		} finally {
			if (out != null) {
				try {
					out.close(); 
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}*/

	}

}
