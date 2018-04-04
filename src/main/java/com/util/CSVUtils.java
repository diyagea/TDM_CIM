package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 文件操作
 */
public class CSVUtils {
	private static String LINE1_EN = "//|Do Not Delete this row; It contains the Validation Data|Information Type|Cutters No.:|Holders No.:|Units:|mm|inch||Logical Check Box:|TRUE|FALSE||Technology:|Milling|Drilling|Special Lollipop|Special Slot Mill|Special Dove Mill|Special Counter Sink||Tip / Type:|Flat|Ball|Bull|Drilling|Ream|Tap|Center|Full Radius|Corner Radius||Use Full Cutter Length||Taper||Shank||Use Shank Cone Angle||Spindle Dir.:|Clockwise|Counter Clockwise|Off||Coolant:|Off|Flood|Mist|Through|Air||Connect to:|Current pass|Next pass||Cutting Mode:|Climb|Conventional|Mixed||Collapse Direction:|Both Up&Down|Up|Down||Cycle:|Spot Drill|High Speed Peck|Left Hand Tapping|Fine Boring|Counter Boring|Deep Hole Peck|Tapping|Boring|Bore+Spindle Stop|Back Boring|Bore+Dwell+Manual|Bore+Dwell+Feed||Shift||Dwell||Peck||Holder Name|Comment|Number of the Holder stages|Number of the Spindle stages|Bottom Diameter_1|Top Diameter_1|Cone Height_1|Total Height_1|Bottom Diameter_2|Top Diameter_2|Cone Height_2|Total Height_2|Bottom Diameter_3|Top Diameter_3|Cone Height_3|Total Height_3|Bottom Diameter_4|Top Diameter_4|Cone Height_4|Total Height_4|Bottom Diameter_5|Top Diameter_5|Cone Height_5|Total Height_5|Bottom Diameter_6|Top Diameter_6|Cone Height_6|Total Height_6|Bottom Diameter_7|Top Diameter_7|Cone Height_7|Total Height_7|Bottom Diameter_8|Top Diameter_8|Cone Height_8|Total Height_8|Bottom Diameter_9|Top Diameter_9|Cone Height_9|Total Height_9|Bottom Diameter_10|Top Diameter_10|Cone Height_10|Total Height_10|Bottom Diameter_11|Top Diameter_11|Cone Height_11|Total Height_11";
	private static String LINE2_EN = "//||Parameter and Option ID's For the CSV|1000|7000|100|101|102||99|1|0||2101|210101|210102|210103|210104|210105|210106||2102|210201|210202|210203|210204|210205|210206|210207|210208|210209||2107||2111||2201||2204||4203|420301|420302|420303||4204|420401|420402|420403|420404|420405||5107|510701|510702||5108|510801|510802|510803||5109|510901|510902|510903||6101|610101|610102|610103|610104|610105|610106|610107|610108|610109|610110|610111|610112||6102||6105||6107||7001|7002|7003|7004|7011|7012|7013|7014|7021|7022|7023|7024|7031|7032|7033|7034|7041|7042|7043|7044|7051|7052|7053|7054|7061|7062|7063|7064|7071|7072|7073|7074|7081|7082|7083|7084|7091|7092|7093|7094|7101|7102|7103|7104|7111|7112|7113|7114";
	private static String LINE3_EN = "//|Resources for localization:|Check Cutter Names|Check Holder Names|Export to Cimatron CSV|Import from Cimatron CSV|Refresh|Reset All|localize|Language|Utils|||||||||||Check Holder Shapes|||||||||||||||||||||General|Cutter Shape|Cutter & Holder Assembly|Machine Parameters|Motion Parameters|Drill Cycle Parameters|Holder and Spindle|Cutter|Machine|Body|Shank|Main|Misc.|Additional|Spindle|The stages of the Holder and after it the stages of the Spindle; From small to big";
	private static String LINE4_EN = "//|Resources for localization:|Cutters|Holders|Macros must be active!!!|Click Here|Any modifications to the file when the macros are not working might cause data corruption.|Close the file without saving it and open it again with the Macros enabled.|The last change is not allowed, it was reverted.|The last change is not allowed, it caused data-corruption, close the file without saving it.|The Cutters sheet name was changed to:|The Holders sheet name was changed to:|The last change affected a protected cell.|The protected cell value:|The protected cell location:|Warning! Proceeding with the Refresh action will reset the Undo buffer.|Do you want to proceed with the Refresh action?|The only valid input value is one of the options from the Drop-Down list.|Press Retry to retype the input value or press Cancel to select one of the options from the Drop-Down list.|This row is preserved only for the columns IDs.|Press Retry to retype the input value or press Cancel to restore the value before the change.|Warning! Inconsistencies were found, they are marked with Orange background.|Warning! Problems were found, they are marked with Orange background.|Warning! Proceeding with the Reset All action will cause all the information entered in this worksheet to be lost.|Do you want to proceed with the Reset All action?|Warning! The selected file:|Is NOT a CSV file in CimatronE format.|Is from an incompatible CimatronE version.|Is NOT a CSV file of CimatronE Cutters.|Is NOT a CSV file of CimatronE Holders.|Is NOT an original CSV file of CimatronE Cutters.|Is NOT in the same Units (mm, Inch), as the Excel sheet.|Error! Can not determine the Excel sheet Units (mm, Inch).|Press on the Refresh button to restore it.||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||||CimatronE CSV Files (*.csv)|Import Cutters - Select CSV file of CimatronE Cutters|Import Holders - Select CSV file of CimatronE Holders|Localization - Select original CSV file of CimatronE Cutters|Export Cutters|Export Holders/";
	private static String LINE5_EN = "//|Cutters No.:|1|Units:|mm|Holders No.:|1";
	private static String LINE6_EN = "CimatronE10.00|1000|1|100|101|7000|1";
	private static String LINE7_EN = "Cutter Name|Comment|Website|Magazine No.|Diameter Compensation|Length Compensation|Technology|Tip/Type|Catalog Name|Thread Type|Diameter|Corner Radius|Use Full Cutter Length|Full Cutter Length|Clear Length|Cut Length|Taper|Taper Angle|Tip Angle|Shank|Shank Top Diameter|Shank Bottom Diameter|Shank Cone Length|Use Shank Cone Angle|Shank Cone Angle|Shank Free Length|Holder Name|Grip Length|Free Length|Feed|Spin|Vc|Fz|Pitch|Teeth|Plunge Feed (%)|Life Length|Spindle Dir.|Coolant|Gauge Length|Corner Feed (%)|Down Feed (%)|Side Feed (%)|DZ Feed (%)|Entry Feed (%)|End Feed (%)|Retract Feed|Down Step|Side Step|Forward Step|App. Radius|App. Length|Tolerance|Connect to|Cutting Mode|Collapse Direction|Plunge Size|Ramp Angle|DZ/Feed Start|Cycle|Shift|Shift I|Shift J|Dwell|Dwell Time|Peck|Peck Step|Peck Dec.|Misc 1|Misc 2|Misc 3|Misc 4";
	private static String LINE8_EN = "1101|1102|1103|1201|1202|1203|2101|2102|2103|2104|2105|2106|2107|2108|2109|2110|2111|2112|2113|2201|2202|2203|2206|2204|2205|2207|3101|3102|3103|4101|4102|4103|4104|4105|4106|4201|4202|4203|4204|4205|4301|4302|4303|4304|4305|4306|4307|5101|5102|5103|5104|5105|5106|5107|5108|5109|5110|5111|5112|6101|6102|6103|6104|6105|6106|6107|6108|6109|6110|6111|6112|6113";

	
	public static File createCutListFile(List<Map<String, String>> exportDatas, String outPutPath, String fileName) throws Exception {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
				file.mkdir();
			}
			// 定义文件名格式并创建
			//csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
			csvFile = new File(file, fileName +".csv");
			System.out.println("csvFile：" + csvFile);
			if(csvFile.exists()){
				csvFile.delete();
			}
			csvFile.createNewFile();
			
			// UTF-8使正确读取分隔符"|"
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"),
					1024);
			System.out.println("csvFileOutputStream：" + csvFileOutputStream);

			// 写入文件头部
			csvFileOutputStream.write(LINE1_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE2_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE3_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE4_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE5_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE6_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE7_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE8_EN);
			csvFileOutputStream.newLine();

			for(Map<String, String> exportData : exportDatas){
				
				String[] titles = LINE8_EN.split("\\|");
				int n = 1;
				boolean isSpecial = exportData.get("isSpecial") != null && exportData.get("isSpecial").equals("true");
				String specType = exportData.get("specType") == null? "" : exportData.get("specType");
				// 写入文件内容
				for (String s : titles) {
					// 写入内容
					if (s.equals("1101"))
						csvFileOutputStream.write(exportData.get("Name")==null? "" : exportData.get("Name"));// 名称
					if (s.equals("2101"))
						csvFileOutputStream.write(exportData.get("Tech")==null? "" : exportData.get("Tech"));// 工艺
					if (s.equals("2102"))
						csvFileOutputStream.write(exportData.get("Type")==null? "" : exportData.get("Type"));// 类型
					if (s.equals("2105"))
						csvFileOutputStream.write(exportData.get("Dc")==null? "" : exportData.get("Dc"));// 直径
					//以上内容所有刀具都需要
					//以下内容特殊刀具选择需要
					switch(specType){
						case "Lollipop":
							break;
						case "SlotMillingFullRadius":
							if (s.equals("2109"))
								csvFileOutputStream.write(exportData.get("Lpr")==null? "" : exportData.get("Lpr"));// 刃长
							break;
						case "SlotMillingFlat":
							if (s.equals("2109"))
								csvFileOutputStream.write(exportData.get("Lpr")==null? "" : exportData.get("Lpr"));// 刃长
							break;
						case "DoveMillingFlat":
							if (s.equals("2110"))
								csvFileOutputStream.write(exportData.get("Ls")==null? "" : exportData.get("Ls"));// 有效长度
							if (s.equals("2109"))
								csvFileOutputStream.write(exportData.get("Lpr")==null? "" : exportData.get("Lpr"));// 刃长
							if (s.equals("2111"))
								csvFileOutputStream.write("1");// Taper
							if (s.equals("2112"))
								csvFileOutputStream.write(exportData.get("KF")==null? "" : exportData.get("KF"));// Taper Angle
							break;
						default:
							if (s.equals("2110"))
								csvFileOutputStream.write(exportData.get("Ls")==null? "" : exportData.get("Ls"));// 有效长度
							if (s.equals("2109"))
								csvFileOutputStream.write(exportData.get("Lpr")==null? "" : exportData.get("Lpr"));// 刃长
							if (s.equals("3103"))
								csvFileOutputStream.write(exportData.get("Lpr")==null? "" : exportData.get("Lpr")); //伸出长度
							break;
					
					}
				
					//转速n和进给率不在需要
					if (s.equals("4101"))
						csvFileOutputStream.write(exportData.get("Feed")==null? "" : exportData.get("Feed"));//转速
					if (s.equals("4102"))
						csvFileOutputStream.write(exportData.get("Spin")==null? "" : exportData.get("Spin"));//进给率
					if (s.equals("4103"))
						csvFileOutputStream.write(exportData.get("Vc")==null? "" : exportData.get("Vc"));//削切速度vc
					if (s.equals("4104"))
						csvFileOutputStream.write(exportData.get("Fz")==null? "" : exportData.get("Fz"));//每齿进给fz
					if (s.equals("4106"))
						csvFileOutputStream.write(exportData.get("countCuts")==null? "" : exportData.get("countCuts"));//齿数
					
					//Shank Top Diameter 20-2202
					//Shank Bottom Diameter 21-2203
					//Shank Free Length 25-2207
					if(isSpecial){
						if (s.equals("2201"))
							csvFileOutputStream.write("1");
						if (s.equals("2202"))
							csvFileOutputStream.write(exportData.get("DHB")==null? "" : exportData.get("DHB"));//齿数
						if (s.equals("2203"))
							csvFileOutputStream.write(exportData.get("DHB")==null? "" : exportData.get("DHB"));//齿数
						if (s.equals("2207"))
							csvFileOutputStream.write(exportData.get("L4")==null? "" : exportData.get("L4"));//齿数
					}
					
					//填入空白
					if(n < titles.length)
						csvFileOutputStream.write("|");
					n++;
				}
				csvFileOutputStream.flush();
				csvFileOutputStream.newLine();
			}
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
		
		
		
	}
	
	/**
	 * 生成为CVS文件
	 * 
	 * @param exportData
	 *            源数据List
	 * @param map
	 *            csv文件的列表头map
	 * @param outPutPath
	 *            文件路径
	 * @param fileName
	 *            文件名称
	 * @return
	 * @throws Exception 
	 */
	public static File createCSVFile(Map<String, String> exportData, String outPutPath, String fileName) throws Exception {
		File csvFile = null;
		BufferedWriter csvFileOutputStream = null;
		try {
			File file = new File(outPutPath);
			if (!file.exists()) {
				file.mkdir();
			}
			// 定义文件名格式并创建
			//csvFile = File.createTempFile(fileName, ".csv", new File(outPutPath));
			csvFile = new File(file, fileName +".csv");
			System.out.println("csvFile：" + csvFile);
			if(csvFile.exists()){
				csvFile.delete();
			}
			csvFile.createNewFile();
			
			// UTF-8使正确读取分隔符"|"
			csvFileOutputStream = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(csvFile), "UTF-8"),
					1024);
			System.out.println("csvFileOutputStream：" + csvFileOutputStream);

			// 写入文件头部
			csvFileOutputStream.write(LINE1_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE2_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE3_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE4_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE5_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE6_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE7_EN);
			csvFileOutputStream.newLine();
			csvFileOutputStream.write(LINE8_EN);
			csvFileOutputStream.newLine();

			String[] titles = LINE8_EN.split("\\|");
			int n = 1;
			boolean isSpecial = exportData.get("isSpecial") != null && exportData.get("isSpecial").equals("true");
			String specType = exportData.get("specType") == null? "" : exportData.get("specType");
			// 写入文件内容
			for (String s : titles) {
				// 写入内容
				if (s.equals("1101"))
					csvFileOutputStream.write(exportData.get("Name")==null? "" : exportData.get("Name"));// 名称
				if (s.equals("2101"))
					csvFileOutputStream.write(exportData.get("Tech")==null? "" : exportData.get("Tech"));// 工艺
				if (s.equals("2102"))
					csvFileOutputStream.write(exportData.get("Type")==null? "" : exportData.get("Type"));// 类型
				if (s.equals("2105"))
					csvFileOutputStream.write(exportData.get("Dc")==null? "" : exportData.get("Dc"));// 直径
				//以上内容所有刀具都需要
				//以下内容特殊刀具选择需要
				switch(specType){
					case "Lollipop":
						break;
					case "SlotMillingFullRadius":
						if (s.equals("2109"))
							csvFileOutputStream.write(exportData.get("Lpr")==null? "" : exportData.get("Lpr"));// 刃长
						break;
					case "SlotMillingFlat":
						if (s.equals("2109"))
							csvFileOutputStream.write(exportData.get("Lpr")==null? "" : exportData.get("Lpr"));// 刃长
						break;
					case "DoveMillingFlat":
						if (s.equals("2110"))
							csvFileOutputStream.write(exportData.get("Ls")==null? "" : exportData.get("Ls"));// 有效长度
						if (s.equals("2109"))
							csvFileOutputStream.write(exportData.get("Lpr")==null? "" : exportData.get("Lpr"));// 刃长
						if (s.equals("2111"))
							csvFileOutputStream.write("1");// Taper
						if (s.equals("2112"))
							csvFileOutputStream.write(exportData.get("KF")==null? "" : exportData.get("KF"));// Taper Angle
						break;
					default:
						if (s.equals("2110"))
							csvFileOutputStream.write(exportData.get("Ls")==null? "" : exportData.get("Ls"));// 有效长度
						if (s.equals("2109"))
							csvFileOutputStream.write(exportData.get("Lpr")==null? "" : exportData.get("Lpr"));// 刃长
						if (s.equals("3103"))
							csvFileOutputStream.write(exportData.get("Lpr")==null? "" : exportData.get("Lpr")); //伸出长度
						break;
				
				}
				//转速n和进给率不在需要
				if (s.equals("4101"))
					csvFileOutputStream.write(exportData.get("Feed")==null? "" : exportData.get("Feed"));//转速
				if (s.equals("4102"))
					csvFileOutputStream.write(exportData.get("Spin")==null? "" : exportData.get("Spin"));//进给率
				if (s.equals("4103"))
					csvFileOutputStream.write(exportData.get("Vc")==null? "" : exportData.get("Vc"));//削切速度vc
				if (s.equals("4104"))
					csvFileOutputStream.write(exportData.get("Fz")==null? "" : exportData.get("Fz"));//每齿进给fz
				if (s.equals("4106"))
					csvFileOutputStream.write(exportData.get("countCuts")==null? "" : exportData.get("countCuts"));//齿数
				
				//Shank Top Diameter 20-2202
				//Shank Bottom Diameter 21-2203
				//Shank Free Length 25-2207
				if(isSpecial){
					if (s.equals("2201"))
						csvFileOutputStream.write("1");
					if (s.equals("2202"))
						csvFileOutputStream.write(exportData.get("DHB")==null? "" : exportData.get("DHB"));//齿数
					if (s.equals("2203"))
						csvFileOutputStream.write(exportData.get("DHB")==null? "" : exportData.get("DHB"));//齿数
					if (s.equals("2207"))
						csvFileOutputStream.write(exportData.get("L4")==null? "" : exportData.get("L4"));//齿数
				}
				
				if(n < titles.length)
					csvFileOutputStream.write("|");
				n++;
			}
			csvFileOutputStream.flush();
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		} finally {
			try {
				csvFileOutputStream.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return csvFile;
	}
	
	public static List<String> analysisCsv(InputStream in) throws Exception{
		List<String> lines = new ArrayList<String>();
		BufferedReader br = FormatTxtType.toUTF(in);
		String lineTxt = null;
		int rowNum = 0;
	    while ((lineTxt = br.readLine()) != null){
	    	rowNum++;
	    	if(rowNum>9 && lineTxt.length()>0){
	    		//System.out.println(lineTxt.length() +": " + lineTxt);
	    		try{
	    			String toolID = lineTxt.substring(0, lineTxt.indexOf("|"));
	    			System.out.println(toolID);
	    			lines.add(toolID);
	    		}catch(Exception e){
	    			System.out.println("上传数据有误，在第"+rowNum+"行");
	    			e.printStackTrace();
	    		}
	    	}
	    }
		return lines;
	}
	
	/**
	 * 删除该目录filePath下的所有文件
	 * 
	 * @param filePath
	 *            文件目录路径
	 */
	public static void deleteFiles(String filePath) {
		File file = new File(filePath);
		if (file.exists()) {
			File[] files = file.listFiles();
			for (int i = 0; i < files.length; i++) {
				if (files[i].isFile()) {
					files[i].delete();
				}
			}
		}
	}

}
