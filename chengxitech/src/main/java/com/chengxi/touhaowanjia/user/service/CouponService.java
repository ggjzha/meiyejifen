package com.chengxi.user.service;


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
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@Transactional
public class CouponService {
    @Autowired
    private CouponRepo couponRepo;
    @Autowired
    private UserRepo userRepo;

    public JsonDtoWrapper addCoupon(CouponDomain coupon, UserClient loginUser){

        UserDomain user = userRepo.findByUserID(loginUser.getUserID());
        coupon.setCouponID(IdGenerator.genId("shopid"));
        coupon.setShopID(user.getShopID());
        couponRepo.save(coupon);

        JsonDtoWrapper<Object> jsonDtoWrapper = new JsonDtoWrapper<>();
        jsonDtoWrapper.setCodeMsg(APIResultCode.SUCCESS);
        return jsonDtoWrapper;
    }

    public JsonDtoWrapper deleteCoupon(CouponDomain coupon){
        couponRepo.delete(coupon);
        JsonDtoWrapper<Object> j = new JsonDtoWrapper<>();
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
        log.info("User findOnePage ,operator : " + loginUser.getUserID() + " page: " + page + " pageSize: " + pageSize);
        JsonDtoWrapper j = new JsonDtoWrapper();
        Integer count = couponRepo.countByShopID(loginUser.getShopID());
        int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        page = page - 1;
        if (page <= 0) {
            page = 0;
            log.error("User findOnePage change page,operator : " + loginUser.getUserID() + " page: " + page);
        } else if (page > pageCount - 1) {
            page = pageCount - 1;
            log.error("User findOnePage change page,operator : " + loginUser.getUserID() + " page: " + page);
        }

        PageRequest of = PageRequest.of(page, pageSize);
        List<CouponDomain> couponDomainList = couponRepo.findAllByShopID(loginUser.getShopID(), of);

        j.setCodeMsg(APIResultCode.SUCCESS);
        j.setData(couponDomainList);
        return j;
    }

}
