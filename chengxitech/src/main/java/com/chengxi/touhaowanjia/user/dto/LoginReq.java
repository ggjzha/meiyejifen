package com.chengxi.touhaowanjia.user.dto;

import lombok.Data;

@Data
public class LoginReq {
    String phoneNum;
    String loginPWD;
}