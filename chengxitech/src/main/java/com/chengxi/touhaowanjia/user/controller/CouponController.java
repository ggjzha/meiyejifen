package com.chengxi.touhaowanjia.user.controller;


import com.chengxi.touhaowanjia.user.domain.CouponDomain;
import com.chengxi.touhaowanjia.user.dto.CouponReq;
import com.chengxi.touhaowanjia.user.repo.CouponRepo;
import com.chengxi.touhaowanjia.user.service.CouponService;
import com.go.basetool.bean.UserClient;
import com.go.basetool.commonreq.PageReq;
import com.go.basetool.threadstatus.AbstractController;
import com.go.basetool.utils.JsonDtoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.jar.JarEntry;
@Slf4j
@RestController
@RequestMapping("/coupon")
public class CouponController extends AbstractController {


    @Autowired
    private CouponService couponService;


    @PostMapping("/setCoupon")
    public JsonDtoWrapper setCoupon(@RequestBody CouponDomain coupon){
        UserClient loginUser = getLoginUser();
        JsonDtoWrapper jsonDtoWrapper = couponService.setCoupon(coupon, loginUser);
        return jsonDtoWrapper;
    }

    @PostMapping("/getCoupon")
    public JsonDtoWrapper getCoupon(@RequestBody CouponReq coupon){
        JsonDtoWrapper jsonDtoWrapper = couponService.getCoupon(coupon.getCouponID(),getLoginUser());
        return jsonDtoWrapper;
    }

    @PostMapping("/couponList")
    public JsonDtoWrapper couponList(@RequestBody PageReq page){
        return couponService.findOnePage(page.getPage(),page.getPageSize(),getLoginUser());
    }

    @PostMapping("/deleteCoupon")
    public JsonDtoWrapper deleteCoupon(@RequestBody CouponReq coupon){
        return couponService.deleteCoupon(coupon.getCouponID(),getLoginUser());
    }
}
