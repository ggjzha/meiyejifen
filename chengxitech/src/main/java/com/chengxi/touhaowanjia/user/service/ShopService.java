package com.chengxi.touhaowanjia.user.service;

import com.chengxi.cache.constant.CacheConstant;
import com.chengxi.cache.service.RedisClient;
import com.chengxi.touhaowanjia.user.domain.CouponDomain;
import com.chengxi.touhaowanjia.user.domain.ShopDomain;
import com.chengxi.touhaowanjia.user.domain.UserDomain;
import com.chengxi.touhaowanjia.user.dto.ShopSetReq;
import com.chengxi.touhaowanjia.user.repo.ShopRepo;
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
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

@Slf4j
@Service
@Transactional
public class ShopService {
    @Autowired
    private ShopRepo shopRepo;
    @Autowired
    private UserRepo userRepo;
    @Autowired
    RedisClient redislient;
    private Integer seconds = 3600 * 24 * 3;//3天有效期

    public JsonDtoWrapper setShop(ShopDomain shop, UserClient loginUser) {
        JsonDtoWrapper jsonDtoWrapper = new JsonDtoWrapper();
        if(StringUtils.isEmpty(loginUser.getShopID())){
            log.info("shop create , loginUser:" + loginUser.getUserID());
            shop.setShopID(IdGenerator.genId("shopid"));
            shopRepo.save(shop);

            userRepo.updateShopIDById(shop.getShopID(),loginUser.getUserID());

            loginUser.setShopID(shop.getShopID());
            redislient.set(CacheConstant.getUserInfoRrefix(loginUser.getUserID()), new Gson().toJson(loginUser),seconds);

            jsonDtoWrapper.setCodeMsg(APIResultCode.SUCCESS);
            log.info("shop create success, loginUser:" + loginUser.getUserID());
            return jsonDtoWrapper;
        }
        if(null == shop.getShopID()){
            jsonDtoWrapper.setCodeMsg(APIResultCode.FAILURE);
            log.error("shop not exist,shopid is null,loginUser: " + loginUser.getUserID());
            return jsonDtoWrapper;
        }

        shopRepo.save(shop);
        jsonDtoWrapper.setCodeMsg(APIResultCode.SUCCESS);
        log.info("shop set success,loginUser: " + loginUser.getUserID());
        return jsonDtoWrapper;
    }

    public JsonDtoWrapper getShop(UserClient loginUser){
        String shopID = loginUser.getUserID();

        JsonDtoWrapper jsonDtoWrapper = new JsonDtoWrapper();
        if(StringUtils.isEmpty(shopID)){
            jsonDtoWrapper.setCodeMsg(APIResultCode.SHOP_NO_CREATE);
            return jsonDtoWrapper;
        }

        ShopDomain shop = shopRepo.findByShopID(loginUser.getShopID());
        if (null == shop) {
            jsonDtoWrapper.setCodeMsg(APIResultCode.SHOP_NO_CREATE);
            log.error("shop not create " +",loginUser: " + loginUser.getUserID() + ",username: " +loginUser.getUsername());
            return jsonDtoWrapper;
        }
        jsonDtoWrapper.setCodeMsg(APIResultCode.SUCCESS);
        jsonDtoWrapper.setData(shop);
        return jsonDtoWrapper;
    }

    public JsonDtoWrapper findOnePage(Integer page, Integer pageSize, UserClient loginUser) {
        log.info("shop findOnePage ,operator : " + loginUser.getUserID() + " page: " + page + " pageSize: " + pageSize);
        JsonDtoWrapper j = new JsonDtoWrapper();
        Integer count = (int)shopRepo.count();
        int pageCount = count % pageSize == 0 ? count / pageSize : count / pageSize + 1;
        page = page - 1;
        if (page <= 0) {
            page = 0;
            log.error("shop findOnePage change page,operator : " + loginUser.getUserID() + " page: " + page);
        } else if (page > pageCount - 1) {
            page = pageCount - 1;
            log.error("shop findOnePage change page,operator : " + loginUser.getUserID() + " page: " + page);
        }

        PageRequest of = PageRequest.of(page, pageSize);
        List<ShopDomain> allBy = shopRepo.findAllBy(of);

        System.out.println(allBy);
        j.setCodeMsg(APIResultCode.SUCCESS);
        j.setData(allBy);
        return j;
    }
}
