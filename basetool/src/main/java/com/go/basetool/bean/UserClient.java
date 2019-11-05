package com.go.basetool.bean;


import com.go.basetool.APIResultCode;
import com.go.basetool.BigDecimalTool;
import lombok.Data;

import javax.persistence.Column;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class UserClient {
    Integer userID;

    String cookie;

    Integer myRole;
}
