package com.chengxi.touhaowanjia.user.service;


import com.chengxi.touhaowanjia.user.domain.CouponDomain;
import com.chengxi.touhaowanjia.user.domain.ProjectDomain;
import com.chengxi.touhaowanjia.user.repo.ProjectRepo;
import com.go.basetool.APIResultCode;
import com.go.basetool.bean.UserClient;
import com.go.basetool.idgenerator.IdGenerator;
import com.go.basetool.utils.JsonDtoWrapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
public class ProjectService {
    @Autowired
    private ProjectRepo projectRepo;

    public JsonDtoWrapper setProject(ProjectDomain projectDomain, UserClient loginUser) {
        log.info("project ,operator: " + loginUser.getUserID() + ",project:" + new Gson().toJson(projectDomain));
        if (StringUtils.isEmpty(projectDomain.getProjectID())) {
            projectDomain.setProjectID(IdGenerator.genId("projectid"));
            projectDomain.setShopID(loginUser.getShopID());
        }

        projectRepo.save(projectDomain);
        JsonDtoWrapper<Object> jsonDtoWrapper = new JsonDtoWrapper<>();
        jsonDtoWrapper.setCodeMsg(APIResultCode.SUCCESS);
        log.info("project success ,operator: " + loginUser.getUserID());
        return jsonDtoWrapper;
    }
    public JsonDtoWrapper deleteP(String projectID, UserClient loginUser){
        JsonDtoWrapper<Object> j = new JsonDtoWrapper<>();
        if(StringUtils.isEmpty(projectID)){
            log.error("deleteCoupon faile , couponID is empty,operator: "+loginUser.getUserID());
            j.setCodeMsg(APIResultCode.FAILURE);
            return j;
        }
        projectRepo.deleteByProjectID(projectID);
        j.setCodeMsg(APIResultCode.SUCCESS);
        return j;
    }

    public JsonDtoWrapper getCoupon(String projectID,UserClient userClient){
        ProjectDomain coupon = projectRepo.findByProjectID(projectID);
        JsonDtoWrapper jsonDtoWrapper = new JsonDtoWrapper();
        if(null == coupon){
            jsonDtoWrapper.setCodeMsg(APIResultCode.FAILURE);
            log.error("get coupon not exist ,couponid:"+projectID+",operator:"+userClient.getUserID());
            return jsonDtoWrapper;
        }
        jsonDtoWrapper.setCodeMsg(APIResultCode.SUCCESS);
        jsonDtoWrapper.setData(coupon);
        log.info("get coupon success,couponid:"+projectID+",operator:"+userClient.getUserID());
        return jsonDtoWrapper;
    }

    public JsonDtoWrapper findOnePage(Integer page, Integer pageSize, UserClient loginUser){
        log.info("Coupon findOnePage ,operator : " + loginUser.getUserID() + " page: " + page + " pageSize: " + pageSize);
        JsonDtoWrapper j = new JsonDtoWrapper();
        Integer count = projectRepo.countByShopID(loginUser.getShopID());
        int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        page = page - 1;
        if (page <= 0) {
            page = 0;
            log.error("Coupon findOnePage change page,operator : " + loginUser.getUserID() + " page: " + page);
        } else if (page > pageCount - 1) {
            page = pageCount - 1;
            log.error("Coupon findOnePage change page,operator : " + loginUser.getUserID() + " page: " + page);
        }

        PageRequest of = PageRequest.of(page, pageSize);
        List<ProjectDomain> couponDomainList = projectRepo.findAllByShopID(loginUser.getShopID(), of);

        j.setCodeMsg(APIResultCode.SUCCESS);
        j.setData(couponDomainList);
        return j;
    }
}
