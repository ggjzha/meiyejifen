package com.chengxi.touhaowanjia.user.domain;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

@Slf4j
@Data
@Entity
@Table(name = "couponshop")
public class CouponShopDomain {
    @Id
    @Column(name = "conshopid")
    private String conShopID;

    @Column(name = "couponid")
    private String conponID;

    @Column(name = "couponname")
    private String couponName;

    @Column(name = "customid")
    private String customID;

    @Column(name = "customname")
    private String customName;

    @Column(columnDefinition = "date",name = "date")
    private Date date;
}
