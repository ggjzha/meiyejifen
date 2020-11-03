package com.chengxi.touhaowanjia.user.dto;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Id;

@Data
public class ShopSetReq {
    private String shopID;

    private String shopName;

    private String shopAddr;

    private String shopPhone;

    private String shopImg;

    private String shopImgContent;
}
