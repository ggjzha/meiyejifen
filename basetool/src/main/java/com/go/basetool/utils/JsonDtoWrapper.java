package com.go.basetool.utils;




import com.go.basetool.APIResultCode;

import java.io.Serializable;

public class JsonDtoWrapper<DTO> implements Serializable {
    private String code;
    private String msg;
    private String message;
    private String extraMsg;
    private DTO data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setCodeMsg(APIResultCode cm) {
        this.code = cm.getCode();
        this.msg = cm.getMessage();
        this.message = cm.getMessage();
        this.extraMsg = cm.getEnglishMessage();
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
        this.message = msg;
    }

    public DTO getData() {

        return data;
    }

    public void setData(DTO data) {
        this.data = data;
    }

    public String getExtraMsg() {
        return extraMsg;
    }

    public void setExtraMsg(String extraMsg) {
        this.extraMsg = extraMsg;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
        this.msg = message;
    }
}