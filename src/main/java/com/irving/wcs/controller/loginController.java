package com.irving.wcs.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 登陆Controller
 */
@Controller
public class loginController {


    @RequestMapping(value="/userLogin", method = RequestMethod.GET)
    @ResponseBody
    public String login(@RequestParam("userName") String name,
                      @RequestParam("passWord") String passWord) {

        if (name.equals("admin") && passWord.equals("123")) {
            return "成功";
        }
        return "失败";
    }
}
