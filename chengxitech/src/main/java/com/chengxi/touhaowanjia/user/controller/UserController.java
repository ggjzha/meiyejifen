package com.chengxi.user.controller;

import com.chengxi.user.dto.LoginReq;
import com.chengxi.user.dto.UserRegisterReq;
import com.chengxi.user.service.UserService;
import com.go.basetool.bean.UserClient;
import com.go.basetool.threadstatus.AbstractController;
import com.go.basetool.utils.JsonDtoWrapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Slf4j
@Controller
@RequestMapping("user")
public class UserController extends AbstractController {

    @Autowired
    UserService userService;

    @PostMapping("/addUser")
    @ResponseBody
    public JsonDtoWrapper login(@RequestBody User userLoginReq) {
        JsonDtoWrapper j = userService.login(userLoginReq);
        UserClient u = getLoginUser();
        log.info(new Gson().toJson(j));
        return j;
    }

    @PostMapping("/register")
    @ResponseBody
    public JsonDtoWrapper register(@RequestBody UserRegisterReq userLoginReq) {
        return userService.register(userLoginReq);
    }
}
