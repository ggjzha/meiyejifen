package com.go.basetool.utils;

import com.go.basetool.bean.UserClient;
import com.go.basetool.bean.UserLoginInfo;
import com.google.gson.Gson;

import java.util.HashMap;

public class WrapperUserLoginInfo {
    public static UserLoginInfo wrapper(UserClient userClient,Object value){
        UserLoginInfo userLoginInfo = new UserLoginInfo();
        userLoginInfo.setUserClient(userClient);
        HashMap<String, String> map = new HashMap<>();
        map.put("data",new Gson().toJson(value));
        userLoginInfo.setHeaders(map);
        return userLoginInfo;
    }
}
