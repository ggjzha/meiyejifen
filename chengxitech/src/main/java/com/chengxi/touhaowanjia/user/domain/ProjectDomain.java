package com.chengxi.touhaowanjia.user.domain;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Id;
import javax.persistence.Column;
import java.math.BigDecimal;

@Slf4j
@Data
@Entity
@Table(name = "project")
public class ProjectDomain {
    @Id
    @Column(name = "projectid")
    private String projectID;

    @Column(name = "projectname")
    private String projectName;

    @Column(name = "projectprice")
    private BigDecimal projectPrice;//价格

    @Column(name = "projectneedcredit")
    private Integer projectNeedCredit;//积分

    @Column(columnDefinition = "text",name = "projectimg")
    private String projectImg;//图片

    @Column(name = "shopid")
    private String shopID;
}
