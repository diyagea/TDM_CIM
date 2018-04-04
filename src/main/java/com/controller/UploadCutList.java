package com.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

import com.DAO.CutListDAO_oracle;
import com.util.CSVUtils;

@Controller("uploadCutList")
public class UploadCutList {

	@Autowired
	private CutListDAO_oracle cutListDAO;
	
	
	@RequestMapping(value = "uploadForword", method = RequestMethod.GET)
	public String uploadForword() {
        return "uploadPage";  
	}
	
	@RequestMapping(value = "uploadCsv", method = RequestMethod.POST)
	public String uploadCsv(HttpServletRequest request,HttpServletResponse response) {
		String filename = "";
		//分析结果刀具ID列表
		List<String> toolIDList = new ArrayList<String>();
		//创建一个通用的多部分解析器  
        CommonsMultipartResolver multipartResolver = new CommonsMultipartResolver(request.getSession().getServletContext());  
        //判断 request 是否有文件上传,即多部分请求  
        if(multipartResolver.isMultipart(request)){  
            //转换成多部分request    
            MultipartHttpServletRequest multiRequest = (MultipartHttpServletRequest)request;  
            //取得request中的所有文件名  
            Iterator<String> iter = multiRequest.getFileNames();  
            while(iter.hasNext()){  
                //取得上传文件  
                MultipartFile file = multiRequest.getFile(iter.next());  
                if(file != null){
                	filename = file.getOriginalFilename();
                	filename = filename.substring(0, filename.indexOf("."));
                	try {
                		toolIDList = CSVUtils.analysisCsv(file.getInputStream());
					} catch (Exception e) {
						System.out.println("分析失败"+e);
						e.printStackTrace();
					}
                	
                   /* //取得当前上传文件的文件名称  
                    String myFileName = file.getOriginalFilename();  
                    //如果名称不为“”,说明该文件存在，否则说明该文件不存在  
                    if(myFileName.trim() !=""){  
                        System.out.println(myFileName);  
                        //重命名上传后的文件名  
                        String fileName = "upload-" + file.getOriginalFilename();  
                        //定义上传路径  
                        String path = "D:/upload" + fileName;  
                        File localFile = new File(path);  
                        try {
							file.transferTo(localFile);
						} catch (IllegalStateException | IOException e) {
							System.out.println("上传文件失败");
							e.printStackTrace();
						}  
                    }*/  
                }  
            }  
        }
        boolean flag = false;
        Map resultsMap = null;
        if(toolIDList.size() == 0 || "".equals(filename)){
        	return "showAnalysisResults";
        }
        try{
        	resultsMap = cutListDAO.createCutList(toolIDList, filename.toUpperCase());
        }catch(Exception e){
        	e.printStackTrace();
        	return "showAnalysisResults";
        }
        flag = true;
        request.setAttribute("flag", flag);
        request.setAttribute("upResults", resultsMap.get("results"));
        request.setAttribute("cutListID", resultsMap.get("cutListID"));
        request.setAttribute("ncProgram", resultsMap.get("ncProg"));
        return "showAnalysisResults";  
	}
}
