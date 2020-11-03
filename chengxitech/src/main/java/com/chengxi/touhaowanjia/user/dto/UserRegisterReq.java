package com.chengxi.touhaowanjia.user.dto;

import lombok.Data;

@Data
public class UserRegisterReq {
    String phoneNum;
    //String codeMessage;//验证码
    String loginPWD;
    String username;
    String shopid;
}
