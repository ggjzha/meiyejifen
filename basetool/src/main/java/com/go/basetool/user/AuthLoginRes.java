package com.go.basetool.user;

import lombok.Data;

@Data
public class AuthLoginRes {
    private String code;
    private String msg;
    private Boolean data;
}
