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
@Table(name = "coupon")
public class CouponDomain {
    @Id
    @Column(name = "couponid")
    private String couponID;

    @Column(name = "couponname")
    private String couponName;

    @Column(name = "couponprice")
    private Integer couponPrice;

    @Column(name = "couponavailable")
    private Integer couponAvailable;

    @Column(name = "couponslogan")
    private String couponSlogan;

    @Column(name = "coupondescription")
    private String couponDescription;

    @Column(name = "couponimg",columnDefinition = "text")
    private String couponImg;

    @Column(name = "shopid")
    private String shopID;
}
