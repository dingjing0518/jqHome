/**

 * @Title: WechatController.java

 * @Package com.by.controller

 * @Description: TODO

 * Copyright: Copyright (c) 2016 

 * Company:Joymap

 * 

 * @author Carl

 * @date 2016年5月17日 上午11:11:05

 * @version V1.0

 */



package com.by.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**

  * @ClassName: WechatController

  * @Description: TODO

  * @author Carl

  * @date 2016年5月17日 上午11:11:05

  *


  */
@Controller
@RequestMapping("/wechat")
public class WechatController {
	
	/**
	 * @Description: 后台进行POST请求(请写在代码执行结尾)
	 * @return void  返回类型
	 */
	public static void doBgPostReq(HttpServletResponse response,String postUrl,Map<String, ?> paramMap) throws IOException {
		response.setContentType( "text/html;charset=utf-8");   
	    PrintWriter out = response.getWriter();  
	    out.println("<form name='postSubmit' method='post' action='"+postUrl+"' >");  
	    for (String key : paramMap.keySet()) {
	    	out.println("<input type='hidden' name='"+key+"' value='" + paramMap.get(key)+ "'>");
		}
	    out.println("</form>");   
	    out.println("<script>");   
	    out.println("  document.postSubmit.submit()");   
	    out.println("</script>");   
	}
	
	/**
	 * 进行请求
	 * @param request
	 * @return
	 * @throws IOException 
	 */
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public void doPostRedirectView(HttpServletRequest request,HttpServletResponse response,ModelMap map) throws IOException {
		map.put("username", "cch");
		map.put("password", "Jqtm2016.");
		doBgPostReq(response, "http://cch.joymap.cn/cch/index.php?s=/home/user/login.html", map);
	}
}
