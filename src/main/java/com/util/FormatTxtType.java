package com.util;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
/**
 * 读取不同编码的TXT文件
 * @author DIYAGEA
 *
 */
public class FormatTxtType {

	//定义四种TXT编码
	private final static String GBK = "GBK";
	private final static String UTF16 = "UTF-16BE";
	private final static String UNICODE = "Unicode";
	private final static String UTF8 = "UTF-8";
	
	/**
	 * 根据传入的TXT File，改为编码UTF8
	 * @param f
	 */
	public static BufferedReader toUTF(InputStream in){
		//声明输入输出流
		InputStreamReader isr = null;
		BufferedReader br = null;
		//clone inputStream
		ByteArrayOutputStream baos = new ByteArrayOutputStream();  
		byte[] buffer = new byte[1024];  
        try{
        	int len;  
        	while ((len = in.read(buffer)) > -1 ) {  
        		baos.write(buffer, 0, len);  
        	}  
        	baos.flush();                
        }catch(Exception e){
        	
        }
		  
		InputStream stream = new ByteArrayInputStream(baos.toByteArray());  
		InputStream copy = new ByteArrayInputStream(baos.toByteArray()); 
		//用副本获取编码
		String charset = getFileCharset(copy);
		System.out.println(charset);
		//如果是UTF8，即不用转换编码
		if(UTF8.equals(charset))
			return new BufferedReader(new InputStreamReader(stream));
		//System.out.println(charset);
		try {
			//获得文件输入流
             isr = new InputStreamReader(stream,charset);
             br = new BufferedReader(isr);
             
         }catch (IOException e) {
             e.printStackTrace();
         } 
        	 
		return br;
	}
	
	/**
	 * 根据TXT内容判断编码
	 * @param file
	 * @return 编码格式
	 */
	private static String getFileCharset(InputStream in) {
		String charset = null;
		try {
			//获得文件输入流
			BufferedInputStream bin = new BufferedInputStream(in);
			int p = (bin.read() << 8) + bin.read();
			//关闭输入流
			bin.close();
			//判断
			switch (p) {
			case 0xefbb:
				charset = UTF8;
				break;
			case 0xfffe:
				charset = UNICODE;
				break;
			case 0xfeff:
				charset = UTF16;
				break;
			default:
				charset = GBK;
			}
			return charset;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return charset;
	}
}
