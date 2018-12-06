package com.irving.wcs.controller;

import com.irving.wcs.common.pojo.Response;
import com.irving.wcs.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * 登陆Controller
 */
@Controller
public class loginController {

    @Resource
    private UserService userServiceImpl;

    @RequestMapping(value="/createUser", method = RequestMethod.GET)
    @ResponseBody
    public Response login(@RequestParam("userName") String name, @RequestParam("passWord") String passWord) {

        String userCode = name + "_code";
        userServiceImpl.createUser(userCode, name, passWord);

        Response resp = new Response();
        resp.setStateCode(200);
        resp.setMsg("登陆失败");
        return resp;
    }
}
