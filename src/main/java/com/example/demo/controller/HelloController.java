package com.example.demo.controller;

import com.example.demo.support.util.AsyncUtil;
import com.example.demo.support.model.LoginInfo;
import com.example.demo.support.util.RedisUtil;
import com.example.demo.support.result.BusinessException;
import com.example.demo.support.result.WebResult;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping(value = "home")
public class HelloController {

    @Autowired
    private RedisUtil redisUtil;

    @Value("${port}")
    private String port;

    @Autowired
    private AsyncUtil asyncUtil;

    @RequestMapping("hello")
    public String sayHello() {
        System.out.println("这是来自："+port+"\nnow=="+ LocalDateTime.now());
        asyncUtil.testA();
        return "来自端口："+port+"<br/>now=="+ LocalDateTime.now();
    }
    @RequestMapping(value = "login")
    public WebResult login(HttpServletRequest request, String username, String password, String validateCode) throws Exception{

        LoginInfo loginInfo = new LoginInfo();
        if(StringUtils.isBlank(password)){
            throw  new BusinessException("1","用户名或密码错误, 请重试！");
        }else {
            Map<String,String> map = new HashMap<>();
            loginInfo.setUsername(username);
//            loginInfo.setId(user.getId());
//            loginInfo.setRealName(user.getRealName());
//            loginInId.setCompanyId(user.getCompanyId());
            long b =7200000;
            String ids = UUID.randomUUID().toString();
            redisUtil.set(ids,loginInfo,b);
//            request.getSession().setAttribute(ids,loginInfo);
            map.put("realName","");
            map.put("Token",ids);

            return WebResult.success(map);
        }
    }
}
