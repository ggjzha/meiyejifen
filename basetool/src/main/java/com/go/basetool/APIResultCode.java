package com.go.basetool;

import java.util.HashMap;
import java.util.Map;

public enum APIResultCode {

    SUCCESS("0", "成功", "success"),
    FAILURE("1", "失败", "failure"),
    NOT_ONLINE("2", "不在线", "NOT_ONLINE"),
    UNKNOWN_ERROR("3", "UNKNOWN_ERROR", "UNKNOWN_ERROR"),
    PHONE_NOT_EXIST("4", "当前手机号码没有注册过", "PHONE_NOT_EXIST"),
    PASSWORD_ERROR("5", "密码错误", "PASSWORD_ERROR"),
    PHONE_NUM_REGISTERED("6", "当前手机号已经注册过", "PHONE_NUM_REGISTERED"),
    CHECK_CODE_NOT_WORK("7", "短信验证码过期或不对", "CHECK_CODE_NOT_WORK"),
    NO_PRIVILEGE("8", "无权做此操作", "NO_PRIVILEGE"),
    NOT_LOGIN("9", "未登录", "NOT_LOGIN"),
    USERID_NOT_EXIST("10", "USERID_NOT_EXIST", "USERID_NOT_EXIST"),
    USER_FROZENED("11", "用户被冻结，请联系所属企业管理员", "USER_FROZENED"),
    PHONE_OR_PWD_IS_NULL("12","手机号或密码为空","PHONE_OR_PWD_IS_NULL"),
    SHOP_NO_CREATE("13","店铺未创建","SHOP_NO_CREATE"),;

    private static final Map<String, APIResultCode> interToEnum = new HashMap<String, APIResultCode>();

    static {
        for (APIResultCode type : APIResultCode.values()) {
            interToEnum.put(type.getCode(), type);
        }
    }

    private String result;
    private String message;
    private String englishMessage;

    private APIResultCode(String code, String message, String englishMessage) {
        this.result = code;
        this.message = message;
        this.englishMessage = englishMessage;
    }

    public static APIResultCode fromCode(String code) {
        return interToEnum.get(code);
    }

    public String getCode() {
        return result;
    }

    public void setCode(String code) {
        this.result = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getEnglishMessage() {
        return englishMessage;
    }

    public void setEnglishMessage(String englishMessage) {
        this.englishMessage = englishMessage;
    }
}
