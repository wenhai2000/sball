package com.example.demo.support.config;

import com.example.demo.support.model.LoginInfo;
import com.example.demo.support.util.AuthUtil;
import com.example.demo.support.util.RedisUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Author : wangyz
 * @Description :
 * @Date :  2017/6/5
 */
//@Slf4j
public class CrossFilter implements HandlerInterceptor, InitializingBean {

    private static Logger log = LoggerFactory.getLogger(CrossFilter.class);

    @Value("${sessionKey}")
    private String sessionKey;

    private String notLogin;



    @Autowired
    private RedisUtil redisUtil;

    public String getNotLogin() {
        return notLogin;
    }

    public void setNotLogin(String notLogin) {
        this.notLogin = notLogin;
    }

    protected void setHeader(HttpServletRequest request, HttpServletResponse response) {
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Methods", request.getHeader("Access-Control-Request-Method"));
        response.setHeader("Access-Control-Allow-Headers", request.getHeader("Access-Control-Request-Headers"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
    }

    @Override
    public void afterPropertiesSet() throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
        setHeader(httpServletRequest, httpServletResponse);
        if (httpServletRequest.getMethod().equals("OPTIONS")){
            httpServletResponse.setStatus(200);
            return false;
        }

        String sessionId = httpServletRequest.getHeader("Token");
        if(sessionId==null || sessionId.trim().equals("")){
            writeNotLoginResponse(httpServletRequest,httpServletResponse);
            return false;
        }

        log.info("mvc:"+sessionId);
//        LoginInfo user = (LoginInfo) httpServletRequest.getSession().getAttribute(sessionId);
        LoginInfo user = (LoginInfo) redisUtil.get(sessionId);
        if (user == null) {
            writeNotLoginResponse(httpServletRequest,httpServletResponse);
            return false;
        }
        AuthUtil.setUserInfo(user);
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {

    }

    @Override
    public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {

    }

    protected void writeNotLoginResponse(HttpServletRequest request, HttpServletResponse response) throws
            ServletException, IOException {
        String notLoginJsonString = "{\"code\":\"111\",\"message\":\"请登录\"}";
        String callback = request.getParameter("callback");
        if (!StringUtils.isBlank(callback)) {
            response.setContentType("application/javascript;charset=UTF-8");
            response.getWriter().write(callback + "(" + notLoginJsonString + ");");
            response.getWriter().flush();
            return;
        }
        if ("XMLHttpRequest".equals(request.getHeader("X-Requested-With"))) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(notLoginJsonString);
            response.getWriter().flush();
            return;
        }
        response.setContentType("text/html;charset=UTF-8");
        response.getWriter().write(notLoginJsonString);
        response.getWriter().flush();
        return;
//        response.sendRedirect(buildLoginUrl(request));
    }
}