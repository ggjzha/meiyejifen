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
@Table(name = "custom")
public class CustomDomain {
    @Id
    @Column(name = "customid")
    private String customID;

    @Column(name = "customname")
    private String customName;

    @Column(name = "openid",unique = true)
    private String openID;

    @Column(name = "customheadurl")
    private String customHeadUrl;
}
