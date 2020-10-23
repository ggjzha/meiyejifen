package com.chengxi.user.dto;

import lombok.Data;

@Data
public class UserRegisterReq {
    String phoneNum;
    String codeMessage;
    String loginPWD;
}
