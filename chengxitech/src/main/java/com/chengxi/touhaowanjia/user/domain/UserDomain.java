package com.chengxi.user.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Slf4j
@Data
@Entity
@Table(name = "User")
public class UserDomain {
    @Id
    @Column(name = "userid")
    String userID;

    @Column(name = "phonenum")
    String phoneNum;

    @Column(name = "userheaderpicurl")
    String userHeaderPicUrl;

    @Column(name = "status")
    private Integer status;

    @Column(name = "role")
    private Integer role;//1普通用户 2管理员

    @Column(name = "loginpwd")
    String loginPWD;

    @Column(name = "username")
    String userName;
}
