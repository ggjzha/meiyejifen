package com.chengxi.touhaowanjia.user.domain;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;

@Slf4j
@Data
@Entity
@Table(name = "shop")
public class ShopDomain {
    @Id
    @Column(name = "shopid")
    private String shopID;

    @Column(name = "shopname")
    private String shopName;

    @Column(name = "shopaddr")
    private String shopAddr;

    @Column(name = "shopphone")
    private String shopPhone;

    @Column(name = "shopimg",columnDefinition = "text")
    private String shopImg;

    @Column(name = "shopimgcontent",columnDefinition = "text")
    private String shopImgContent;

    @Column(name = "shopconvert")
    private Integer shopConvert;
}
