package com.chengxi.touhaowanjia.user.controller;

import com.chengxi.touhaowanjia.user.domain.ShopDomain;
import com.chengxi.touhaowanjia.user.service.ShopService;
import com.go.basetool.bean.UserClient;
import com.go.basetool.commonreq.PageReq;
import com.go.basetool.threadstatus.AbstractController;
import com.go.basetool.utils.JsonDtoWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;


@Slf4j
@Controller
@RequestMapping("/shop")
public class ShopController extends AbstractController {

    @Autowired
    private ShopService shopService;


    @PostMapping("/setShop")
    @ResponseBody
    public JsonDtoWrapper setShop(@RequestBody ShopDomain shop){
        return shopService.setShop(shop,getLoginUser());
    }

    @PostMapping("/getShop")
    @ResponseBody
    public JsonDtoWrapper getShop(){
        UserClient loginUser = getLoginUser();
        return shopService.getShop(loginUser);
    }

    @PostMapping("/getShopList")
    @ResponseBody
    public JsonDtoWrapper getShopList(@RequestBody PageReq page){
        System.out.println(page);
        return shopService.findOnePage(page.getPage(),page.getPageSize(),getLoginUser());
    }
}
