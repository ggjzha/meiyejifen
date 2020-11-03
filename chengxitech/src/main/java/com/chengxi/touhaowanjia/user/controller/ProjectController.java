package com.chengxi.touhaowanjia.user.controller;


import com.chengxi.touhaowanjia.user.domain.CouponDomain;
import com.chengxi.touhaowanjia.user.domain.ProjectDomain;
import com.chengxi.touhaowanjia.user.dto.CouponReq;
import com.chengxi.touhaowanjia.user.dto.ProjectReq;
import com.chengxi.touhaowanjia.user.repo.ProjectRepo;
import com.chengxi.touhaowanjia.user.service.ProjectService;
import com.go.basetool.bean.UserClient;
import com.go.basetool.commonreq.PageReq;
import com.go.basetool.threadstatus.AbstractController;
import com.go.basetool.utils.JsonDtoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.jar.JarEntry;

//@Slf4j
@RestController
@Transactional
@RequestMapping("/project")
public class ProjectController extends AbstractController {

    @Autowired
    private ProjectService projectService;

    @PostMapping("/setProject")
    public JsonDtoWrapper setCoupon(@RequestBody ProjectDomain projectDomain){
        UserClient loginUser = getLoginUser();
        JsonDtoWrapper jsonDtoWrapper = projectService.setProject(projectDomain, loginUser);
        return jsonDtoWrapper;
    }

    @PostMapping("/getProject")
    public JsonDtoWrapper getCoupon(@RequestBody ProjectReq projectReq){
        JsonDtoWrapper jsonDtoWrapper = projectService.getCoupon(projectReq.getProjectID(),getLoginUser());
        return jsonDtoWrapper;
    }

    @PostMapping("/projectList")
    public JsonDtoWrapper couponList(@RequestBody PageReq page){
        return projectService.findOnePage(page.getPage(),page.getPageSize(),getLoginUser());
    }

    @PostMapping("/deleteProject")
    public JsonDtoWrapper deleteCoupon(@RequestBody ProjectReq projectReq){
        return projectService.deleteP(projectReq.getProjectID(),getLoginUser());
    }
}
