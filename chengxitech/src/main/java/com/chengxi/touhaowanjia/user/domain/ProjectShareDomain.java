package com.chengxi.touhaowanjia.user.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "projectshare")
public class ProjectShareDomain {
    @Id
    @Column(name = "projectshareid")
    private String projectShareID;

    @Column(name = "projectid")
    private String projectID;

    @Column(name = "projectname")
    private  String projectName;

    @Column(name = "customid")
    private String customID;

    @Column(name = "customname")
    private String customName;

    @Column(name = "status")
    private String status; //1 未使用 2 使用
}
