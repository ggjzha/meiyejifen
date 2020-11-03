package com.chengxi.touhaowanjia.user.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.DynamicInsert;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Slf4j
@Data
@Entity
@Table(name = "user")
public class UserDomain {
    @Id
    @Column(name = "userid")
    String userID;

    @Column(name = "phonenum",unique = true)
    String phoneNum;

    @Column(name = "role")
    private Integer role;//1普通用户 2管理员

    @Column(name = "loginpwd")
    String loginPWD;

    @Column(name = "username")
    String userName;

    @Column(name = "shopid")
    private String shopID;
}
