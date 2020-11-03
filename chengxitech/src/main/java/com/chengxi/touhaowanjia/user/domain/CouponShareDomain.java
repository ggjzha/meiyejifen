package com.chengxi.touhaowanjia.user.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Slf4j
@Data
@Entity
@Table(name = "couponshare")
public class CouponShareDomain {
    @Id
    @Column(name = "couponshareid")
    private String couponShareID;

    @Column(name = "customid")
    private String customID;

    @Column(name = "customname")
    private String customName;

    @Column(name = "couponid")
    private String couponID;

    @Column(name = "couponname")
    private String couponName;

    @Column(name = "status")
    private Integer status;//1 未使用 2 使用

}
