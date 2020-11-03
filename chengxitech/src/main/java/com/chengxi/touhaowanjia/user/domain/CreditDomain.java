package com.chengxi.touhaowanjia.user.domain;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Data
@Entity
@Table(name = "credit")
public class CreditDomain {
    @Id
    @Column(name = "creditid")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer creditID;

    @Column(name = "shopid")
    private String shopID;

    @Column(name = "customid")
    private Integer customID;

    @Column(name = "credit")
    private Integer credit;

    @Column(name = "invitecustomid")
    private String inviteCustomID;

    @Column(name = "givecredit")
    private String giveCredit;
}
