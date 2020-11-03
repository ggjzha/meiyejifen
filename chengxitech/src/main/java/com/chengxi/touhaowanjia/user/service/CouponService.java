package com.chengxi.touhaowanjia.user.service;


import com.chengxi.touhaowanjia.user.domain.CouponDomain;
import com.chengxi.touhaowanjia.user.domain.UserDomain;
import com.chengxi.touhaowanjia.user.dto.UserListBean;
import com.chengxi.touhaowanjia.user.repo.CouponRepo;
import com.chengxi.touhaowanjia.user.repo.UserRepo;
import com.go.basetool.APIResultCode;
import com.go.basetool.bean.UserClient;
import com.go.basetool.bean.UserLoginInfo;
import com.go.basetool.idgenerator.IdGenerator;
import com.go.basetool.utils.JsonDtoWrapper;
import com.go.basetool.utils.WrapperUserLoginInfo;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CouponService {
    @Autowired
    private CouponRepo couponRepo;

    public JsonDtoWrapper setCoupon(CouponDomain coupon, UserClient loginUser){
        log.info("addCoupon ,operator: "+loginUser.getUserID() + ",coupon"+ new Gson().toJson(coupon));
        if(StringUtils.isEmpty(coupon.getCouponID())){
            coupon.setCouponID(IdGenerator.genId("couponid"));
            coupon.setShopID(loginUser.getShopID());
        }

        couponRepo.save(coupon);
        JsonDtoWrapper<Object> jsonDtoWrapper = new JsonDtoWrapper<>();
        jsonDtoWrapper.setCodeMsg(APIResultCode.SUCCESS);
        log.info("addCoupon success ,operator: "+loginUser.getUserID() );
        return jsonDtoWrapper;
    }

    public JsonDtoWrapper deleteCoupon(String couponID, UserClient loginUser){
        JsonDtoWrapper<Object> j = new JsonDtoWrapper<>();
        if(StringUtils.isEmpty(couponID)){
            log.error("deleteCoupon faile , couponID is empty,operator: "+loginUser.getUserID());
            j.setCodeMsg(APIResultCode.FAILURE);
            return j;
        }
        couponRepo.deleteByCouponID(couponID);
        j.setCodeMsg(APIResultCode.SUCCESS);
        return j;
    }

    public JsonDtoWrapper getCoupon(String couponID,UserClient userClient){
        CouponDomain coupon = couponRepo.findByCouponID(couponID);
        JsonDtoWrapper jsonDtoWrapper = new JsonDtoWrapper();
        if(null == coupon){
            jsonDtoWrapper.setCodeMsg(APIResultCode.FAILURE);
            log.error("get coupon not exist ,couponid:"+couponID+",operator:"+userClient.getUserID());
            return jsonDtoWrapper;
        }
        jsonDtoWrapper.setCodeMsg(APIResultCode.SUCCESS);
        jsonDtoWrapper.setData(coupon);
        log.info("get coupon success,couponid:"+couponID+",operator:"+userClient.getUserID());
        return jsonDtoWrapper;
    }

    public JsonDtoWrapper findOnePage(Integer page, Integer pageSize, UserClient loginUser){
        log.info("Coupon findOnePage ,operator : " + loginUser.getUserID() + " page: " + page + " pageSize: " + pageSize);
        JsonDtoWrapper j = new JsonDtoWrapper();
        Integer count = couponRepo.countByShopID(loginUser.getShopID());
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
        List<CouponDomain> couponDomainList = couponRepo.findAllByShopID(loginUser.getShopID(), of);

        j.setCodeMsg(APIResultCode.SUCCESS);
        j.setData(couponDomainList);
        return j;
    }

}
