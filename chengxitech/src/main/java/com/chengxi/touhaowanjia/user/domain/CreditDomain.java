package com.chengxi.touhaowanjia.user.domain;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Data
@Entity
@Table(name = "shopcredit")
public class ShopCreditDomain {
    @Id
  //  @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "shopcreditid")
    private Long shopCreditID;

    @Column("shopid")
    private String shopID;

    @Column("shopcreditval")
    private Integer shopCreditVal;
}
