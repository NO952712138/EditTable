package com.tfx.web;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.connector.OutputBuffer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.tfx.dao.CidsiDataDao;
import com.tfx.domain.CidsiData;
import com.tfx.domain.Grid;
import com.tfx.service.CidsiDataService;

@Controller
@EnableAutoConfiguration
@RequestMapping("/")
public class MainController {

	@Autowired
	CidsiDataService cidsiDataService;
	@RequestMapping("main")
	public String toMain(HttpServletRequest request)
	{
		return "main";
	}
	@RequestMapping("upload")
	@ResponseBody
	public String upload(@RequestParam("fileName")MultipartFile file,HttpServletRequest request) throws IOException 
	{
		String filename=file.getOriginalFilename();
		BufferedReader reader=null;
		if(file.isEmpty())
		{
			return "empty";
		}
		String localfile="D:\\temp\\"+filename;
		System.out.println(filename);
		System.out.println(localfile);
		File local=new File(localfile);
		try{
			if(!local.exists())
			{
				local.createNewFile();
			}
			file.transferTo(local);
			reader=new BufferedReader(new FileReader(local));
			String temp=null;
			int index=0;
			while((temp=reader.readLine())!=null)
			{
				if(index==0)
				{
					index++;
					continue;
				}
				else
				{
					CidsiData tempdata=new CidsiData();
					String[]temparr=temp.split(",");
					tempdata.setA1(Double.parseDouble(temparr[0]));
					tempdata.setA2(Double.parseDouble(temparr[1]));
					tempdata.setA3(Double.parseDouble(temparr[2]));
					tempdata.setA4(Double.parseDouble(temparr[3]));
					tempdata.setA5(Double.parseDouble(temparr[4]));
					tempdata.setA6(Double.parseDouble(temparr[5]));
					tempdata.setA7(Double.parseDouble(temparr[6]));
					tempdata.setA8(Double.parseDouble(temparr[7]));
					tempdata.setA9(Double.parseDouble(temparr[8]));
					tempdata.setA10(Double.parseDouble(temparr[9]));
					tempdata.setA11(Double.parseDouble(temparr[10]));
					tempdata.setA12(Double.parseDouble(temparr[11]));
					tempdata.setQuality(temparr[12]);
					cidsiDataService.insertData(tempdata);
				}
				
			}
			return "ok";
		}catch(IOException e)
		{
			e.printStackTrace();
			return "false";
		}
		
		
		
	}
	@RequestMapping("download")
	public String download(HttpServletRequest request,HttpServletResponse response)
	{
		OutputStream out=null;
		BufferedWriter writer=null;
		InputStream input=null;
		File file=new File("D:\\temp\\测试题数据样本-v4.0.csv");
		try {
			input=new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		response.reset();  
	    response.setHeader("Content-Disposition", "attachment; fileName='测试题数据样本-v4.0.csv'");  
	    response.addHeader("Content-Length", "" + file.length());  
	    response.setContentType("application/octet-stream;charset=UTF-8");  
	    
	    try
	    {
	    	byte[] b = new byte[2048];
	        int length;
	        out=response.getOutputStream();
	    	while((length=input.read(b))!=-1)
	    	{
	    		out.write(b, 0, length);
	    	}
	    	 
	    	 
	    }catch(IOException e)
	    {
	    	e.printStackTrace();
	    }
	    finally{
	    	try {
				input.close();
	    		out.flush();
				out.close();
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    	
	    }
	   
		return null;
	}
	@RequestMapping("querydata")
	public @ResponseBody HashMap<String,Object> querydata(HttpServletRequest req,
			@RequestParam("pageNow")int pageNow,
			@RequestParam("pageSize")int pageSize)
	{
		int rowOffSet=(pageNow-1)*pageSize+1;
		int total=cidsiDataService.getNumOfRecord();
		HashMap<String,Object> map=new HashMap<String,Object>();
		map.put("data",cidsiDataService.queryData(rowOffSet, pageSize));
		map.put("total", total);
		return map;
	}
	
}
