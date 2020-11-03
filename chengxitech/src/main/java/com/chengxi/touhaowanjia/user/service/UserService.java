package com.chengxi.touhaowanjia.user.service;

import com.chengxi.cache.constant.CacheConstant;
import com.chengxi.cache.service.RedisClient;
import com.chengxi.touhaowanjia.user.domain.UserDomain;
import com.chengxi.touhaowanjia.user.dto.UserDeleteReq;
import com.chengxi.touhaowanjia.user.dto.LoginReq;
import com.chengxi.touhaowanjia.user.dto.UserListBean;
import com.chengxi.touhaowanjia.user.dto.UserRegisterReq;
import com.chengxi.touhaowanjia.user.repo.UserRepo;
import com.go.basetool.APIResultCode;
import com.go.basetool.bean.UserClient;
import com.go.basetool.idgenerator.IdGenerator;
import com.go.basetool.utils.JsonDtoWrapper;
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
public class UserService {
    @Autowired
    RedisClient redislient;

    @Autowired
    UserRepo userRepo;

    private Integer seconds = 3600 * 24 * 3;//3天有效期

    public String updateUserCookie(String phoneNum) {
        String cookie = IdGenerator.genCookie();
        //将cookie存入redis里面
        redislient.set(CacheConstant.getPhoneCookie(phoneNum), cookie, seconds);
        return cookie;
    }

    public void updateUser(UserClient userClient) {
        redislient.set(CacheConstant.getUserInfoRrefix(userClient.getUserID()), new Gson().toJson(userClient), seconds);
    }

    public JsonDtoWrapper<UserClient> login(LoginReq loginUser) {
        //默认object
        JsonDtoWrapper j = new JsonDtoWrapper();
        UserDomain userDomain = userRepo.getUserByPhoneNum(loginUser.getPhoneNum());
        if (null == userDomain) {
            j.setCodeMsg(APIResultCode.PHONE_NOT_EXIST);
            log.error("User not exist,phoneNum : " + loginUser.getPhoneNum() + " loginPWD : " + loginUser.getLoginPWD());
            return j;
        }

        if (loginUser.getLoginPWD().equals(userDomain.getLoginPWD())) {
            UserClient userClient = new UserClient();
            String cookie = updateUserCookie(loginUser.getPhoneNum());
            userClient.setCookie(cookie);
            userClient.setUserID(userDomain.getUserID());
            userClient.setMyRole(userDomain.getRole());
            userClient.setUsername(userDomain.getUserName());
            userClient.setShopID(userDomain.getShopID());
            redislient.set(CacheConstant.getUserInfoRrefix(userClient.getUserID()), new Gson().toJson(userClient), seconds);
            j.setData(userClient);
            j.setCodeMsg(APIResultCode.SUCCESS);
            log.info("User login success,phoneNum : " + loginUser.getPhoneNum() + " loginPWD : " + loginUser.getLoginPWD());
            return j;
        } else {
            j.setCodeMsg(APIResultCode.PASSWORD_ERROR);
            log.error("User password error,phoneNum : " + loginUser.getPhoneNum() + " loginPWD : " + loginUser.getLoginPWD());
            return j;
        }
    }

    public JsonDtoWrapper delete(String userID) {
        JsonDtoWrapper j = new JsonDtoWrapper();
        UserDomain user = userRepo.findByUserID(userID);
        if (null == user) {
            j.setCodeMsg(APIResultCode.PHONE_NOT_EXIST);
            log.error("User delete phoneNum is null,userID : " + userID);
            return j;
        }
        if (null != redislient.get(CacheConstant.getUserInfoRrefix(user.getUserID()))) {
            redislient.delete(CacheConstant.getUserInfoRrefix(user.getUserID()));
            redislient.delete(CacheConstant.getPhoneCookie(user.getPhoneNum()));
            log.info("User delete clear redis cache,userID : " + userID);
        }
        userRepo.delete(user);
        j.setCodeMsg(APIResultCode.SUCCESS);
        log.info("User delete success,userID : " + userID);
        return j;
    }

    public JsonDtoWrapper register(UserDomain userLoginReq, UserClient loginUser) {
        JsonDtoWrapper j = new JsonDtoWrapper();

        if (null != userRepo.getUserByPhoneNum(userLoginReq.getPhoneNum())) {
            j.setCodeMsg(APIResultCode.PHONE_NUM_REGISTERED);
            log.error("User registered, operator : " + loginUser.getUserID() + ", exist phoneNum : " + userLoginReq.getPhoneNum());
            return j;
        }
        if (!"".equals(userLoginReq.getPhoneNum()) && userLoginReq.getPhoneNum() != null
                && !"".equals(userLoginReq.getLoginPWD()) && userLoginReq.getLoginPWD() != null) {
            UserDomain userDomain = new UserDomain();
            userDomain.setUserID(IdGenerator.genUserId());
            userDomain.setLoginPWD(userLoginReq.getLoginPWD());
            userDomain.setPhoneNum(userLoginReq.getPhoneNum());
            userDomain.setShopID(loginUser.getShopID());
            userDomain.setRole(1);
            userDomain.setUserName(userLoginReq.getUserName());
            userRepo.save(userDomain);
            j.setCodeMsg(APIResultCode.SUCCESS);
            log.info("User register success,operator : " + loginUser.getUserID() + ", new phoneNum : " + userLoginReq.getPhoneNum());
            return j;
        } else {
            j.setCodeMsg(APIResultCode.PHONE_OR_PWD_IS_NULL);
            log.error("User register phoneNum is null,operator : " + loginUser.getUserID());
            return j;
        }
    }

    public JsonDtoWrapper findOnePage(Integer page, Integer pageSize, UserClient loginUser) {
        log.info("User findOnePage ,operator : " + loginUser.getUserID() + " page: " + page + " pageSize: " + pageSize);
        JsonDtoWrapper j = new JsonDtoWrapper();
        Integer count = userRepo.countByShopID(loginUser.getShopID());
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
        List<UserDomain> allByShopID = userRepo.findAllByShopID(loginUser.getShopID(), of);
        List<UserListBean> userList = new ArrayList<>();

        for (UserDomain user : allByShopID) {
            if (user.getRole() == 2) {
                continue;
            }
            UserListBean userListBean = new UserListBean();
            userListBean.setUserID(user.getUserID());
            userListBean.setPhoneNum(user.getPhoneNum());
            userListBean.setLoginPWD(user.getLoginPWD());
            userListBean.setUserName(user.getUserName());
            userList.add(userListBean);
        }

        j.setCodeMsg(APIResultCode.SUCCESS);
        j.setData(userList);
        return j;
    }

    public JsonDtoWrapper modifyUser(UserListBean user, UserClient loginUser) {
        String s = new Gson().toJson(user);
        log.info("User modifyUser ,operator : " + loginUser.getUserID() + ",modify userinfo: " + s);
        JsonDtoWrapper j = new JsonDtoWrapper();
        UserDomain userDomain = userRepo.findByUserID(user.getUserID());
        System.out.println();
        if (null == userDomain) {
            j.setCodeMsg(APIResultCode.FAILURE);
            log.error("User modifyUser not exist ,operator : " + loginUser.getUserID() + ",modify userinfo: " + s);
            return j;
        }

        if (StringUtils.isEmpty(user.getLoginPWD()) && StringUtils.isEmpty(user.getPhoneNum()) && StringUtils.isEmpty(user.getUserName())) {
            j.setCodeMsg(APIResultCode.FAILURE);
            log.error("User modifyUser have null param,operator : " + loginUser.getUserID() + ",modify userinfo: " + s);
            return j;
        }

        userRepo.updateUserById(user.getLoginPWD(),user.getPhoneNum(),user.getUserName(),user.getUserID());
        j.setCodeMsg(APIResultCode.SUCCESS);
        return j;
    }
}
