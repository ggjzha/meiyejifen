package com.chengxi.touhaowanjia.user.controller;

import com.chengxi.touhaowanjia.user.domain.UserDomain;
import com.chengxi.touhaowanjia.user.dto.UserDeleteReq;
import com.chengxi.touhaowanjia.user.dto.LoginReq;
import com.chengxi.touhaowanjia.user.dto.UserListBean;
import com.chengxi.touhaowanjia.user.dto.UserRegisterReq;
import com.chengxi.touhaowanjia.user.service.UserService;
import com.go.basetool.bean.UserClient;
import com.go.basetool.commonreq.PageReq;
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
@RequestMapping("/user")
public class UserController extends AbstractController {

    @Autowired
    UserService userService;

    @PostMapping("/login")
    @ResponseBody
    public JsonDtoWrapper login(@RequestBody LoginReq loginUser) {
        JsonDtoWrapper j = userService.login(loginUser);
        return j;
    }

    @PostMapping("/register")
    @ResponseBody
    public JsonDtoWrapper register(@RequestBody UserDomain user) {
        UserClient loginUser = getLoginUser();
        System.out.println(user);
        System.out.println(loginUser);
        return userService.register(user,loginUser);
    }

    @PostMapping("/delete")
    @ResponseBody
    public JsonDtoWrapper delete(@RequestBody UserDeleteReq userDeleteReq){
        return userService.delete(userDeleteReq.getUserID());
    }

    @PostMapping("/userList")
    @ResponseBody
    public JsonDtoWrapper findOnePage(@RequestBody PageReq page){
       return userService.findOnePage(page.getPage(),page.getPageSize(),getLoginUser());
    }

    @PostMapping("/modify")
    @ResponseBody
    public  JsonDtoWrapper modifyUser(@RequestBody UserListBean user){
        return userService.modifyUser(user,getLoginUser());
    }
}
