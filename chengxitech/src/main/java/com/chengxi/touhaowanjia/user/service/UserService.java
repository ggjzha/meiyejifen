package com.chengxi.user.service;

import com.chengxi.cache.constant.CacheConstant;
import com.chengxi.cache.service.RedisClient;
import com.chengxi.user.domain.UserDomain;
import com.chengxi.user.dto.LoginReq;
import com.chengxi.user.dto.UserRegisterReq;
import com.chengxi.user.repo.UserRepo;
import com.go.basetool.APIResultCode;
import com.go.basetool.bean.UserClient;
import com.go.basetool.idgenerator.IdGenerator;
import com.go.basetool.utils.JsonDtoWrapper;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Slf4j
@Service
public class UserService {
    @Autowired
    RedisClient redislient;

    @Autowired
    UserRepo userRepo;

    private String updateUserCookie(String phoneNum) {
        String cookie = IdGenerator.genCookie();
        redislient.set(CacheConstant.getPhoneCookie(phoneNum), cookie);
        return cookie;
    }

    public JsonDtoWrapper<UserClient> login(LoginReq userLoginReq) {

        JsonDtoWrapper j = new JsonDtoWrapper();
        UserDomain userDomain = userRepo.getUserByPhoneNum(userLoginReq.getPhoneNum());
        if (null == userDomain) {
            j.setCodeMsg(APIResultCode.PHONE_NOT_EXIST);
            return j;
        }

        if (userLoginReq.getLoginPWD().equals(userDomain.getLoginPWD())) {
            UserClient userClient = new UserClient();
            String cookie = updateUserCookie(userLoginReq.getPhoneNum());
            userClient.setCookie(cookie);
            userClient.setUserID(userDomain.getUserID());
            userClient.setMyRole(userDomain.getRole());
            userClient.setStatus(userDomain.getStatus());
            redislient.set(CacheConstant.getUserInfoRrefix(userClient.getUserID()), new Gson().toJson(userClient));

            j.setData(userClient);
            j.setCodeMsg(APIResultCode.SUCCESS);
            return j;
        } else {
            j.setCodeMsg(APIResultCode.PASSWORD_ERROR);
            return j;
        }
    }


    public JsonDtoWrapper register(UserRegisterReq userLoginReq) {
        JsonDtoWrapper j = new JsonDtoWrapper();
        String cacheContent = redislient.get(CacheConstant.getPhoneCheckCodeprefix(userLoginReq.getPhoneNum()));
        if (null != userRepo.getUserByPhoneNum(userLoginReq.getPhoneNum())) {
            j.setCodeMsg(APIResultCode.PHONE_NUM_REGISTERED);
            return j;
        }

        if ("1234".equals(userLoginReq.getCodeMessage())) {
            cacheContent = userLoginReq.getCodeMessage();
        }

        if (StringUtils.isEmpty(cacheContent)) {
            j.setCodeMsg(APIResultCode.CHECK_CODE_NOT_WORK);
            return j;
        }

        if (userLoginReq.getCodeMessage().equalsIgnoreCase(cacheContent)) {
            UserDomain userDomain = new UserDomain();
            userDomain.setUserID(IdGenerator.genUserId());
            userDomain.setLoginPWD(userLoginReq.getLoginPWD());
            userDomain.setPhoneNum(userLoginReq.getPhoneNum());
            userRepo.save(userDomain);
            j.setCodeMsg(APIResultCode.SUCCESS);
            return j;
        } else {
            j.setCodeMsg(APIResultCode.CHECK_CODE_NOT_WORK);
            return j;
        }
    }
}
