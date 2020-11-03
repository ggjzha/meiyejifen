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
@Table(name = "customcredit")
public class CustomCreditDomain {
    @Id
    @Column(name = "customcreditid")
    private String customCreditID;

    @Column(name = "creditid")
    private String creditID;

    @Column(name = "customid")
    private String customID;

    @Column(name = "exchange")
    private Integer exchange;

    @Column(name = "type")
    private Integer type;//1增加 2减少

    @Column(name = "usekind")
    private Integer useKind;//1优惠卷

    @Column(name = "useid")
    private String userid;

    @Column(name = "description")
    private String description;

    @Column(columnDefinition = "date" ,name = "date")
    private Date date;
}
