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
@Table
public class ProjectDomain {
    @Id
    @Column("itemid")
    private String itemID;

    @Column("itemname")
    private String itemName;

    @Column("itemprice")
    private Integer itemPrice;

    @Column("needIntegral")
    private Integer needIntegral;
}
