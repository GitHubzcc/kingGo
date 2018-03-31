package com.go.util.Filter;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Enumeration;

/**
 * Created by eriz on 2018/3/19.
 */
public class SysFilter implements Filter {

    private Logger log = Logger.getLogger(getClass().getName());


    FilterConfig filterConfig;

    public void init(FilterConfig filterConfig) throws ServletException {
        this.filterConfig = filterConfig;
    }

    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        log.info("start filter.");
        HttpServletRequest hrequest = (HttpServletRequest) request;
        /*Enumeration<String> enumeration = filterConfig.getInitParameterNames();
        while (enumeration.hasMoreElements()){
            System.out.println(enumeration.nextElement().toString()+"-----enumeration-----------");
            if(!enumeration.nextElement().equals("login")){

            }
        }*/

        /*String user = (String) hrequest.getSession().getAttribute("username");
        //判断用户是否登录
        if (user == null) {
            RequestDispatcher requestDispatcher = request.getRequestDispatcher("/page/login.jsp");
            requestDispatcher.forward(request, response);
            return;
        }*/


        //登录了进行url判断参数、参数名称是否含有非法字符
        String parameterName = null;
        String parameterValue = null;
        // 获取请求的参数
        @SuppressWarnings("unchecked")
        Enumeration<String> allParameter = request.getParameterNames();
//        System.out.println(allParameter);
        while (allParameter.hasMoreElements()) {
            //参数名
            parameterName = allParameter.nextElement();
            //参数
            parameterValue = request.getParameter(parameterName);
            if (null != parameterValue) {
                for (String str : invalidCharacter) {
                    if (StringUtils.containsIgnoreCase(parameterValue, str)) {
                        request.setAttribute("errorMessage", "非法字符：" + str);
                        RequestDispatcher requestDispatcher = request.getRequestDispatcher("/index.jsp");
                        requestDispatcher.forward(request, response);
                        return;
                    }
                }
            }
        }
        chain.doFilter(request, response); // 执行目标资源，放行
    }

    // 需要过滤的非法字符
    private static String[] invalidCharacter = new String[]{
            "script", "select", "insert", "document", "window", "function",
            "delete", "update", "prompt", "alert", "create", "alter",
            "drop", "iframe", "link", "where", "replace", "function", "onabort",
            "onactivate", "onafterprint", "onafterupdate", "onbeforeactivate",
            "onbeforecopy", "onbeforecut", "onbeforedeactivateonfocus",
            "onkeydown", "onkeypress", "onkeyup", "onload",
            "expression", "applet", "layer", "ilayeditfocus", "onbeforepaste",
            "onbeforeprint", "onbeforeunload", "onbeforeupdate",
            "onblur", "onbounce", "oncellchange", "oncontextmenu",
            "oncontrolselect", "oncopy", "oncut", "ondataavailable",
            "ondatasetchanged", "ondatasetcomplete", "ondeactivate",
            "ondrag", "ondrop", "onerror", "onfilterchange", "onfinish", "onhelp",
            "onlayoutcomplete", "onlosecapture", "onmouse", "ote",
            "onpropertychange", "onreadystatechange", "onreset", "onresize",
            "onresizeend", "onresizestart", "onrow", "onscroll",
            "onselect", "onstaronsubmit", "onunload", "IMgsrc", "infarction"
    };


    public void destroy() {

    }
}
