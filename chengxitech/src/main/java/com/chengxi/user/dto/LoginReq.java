package com.chengxi.user.dto;

import lombok.Data;

@Data
public class LoginReq {
    String phoneNum;
    String loginPWD;
}