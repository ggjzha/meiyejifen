package com.chengxi.touhaowanjia.user.repo;


import com.chengxi.touhaowanjia.user.domain.UserDomain;
import com.go.basetool.commonreq.PageReq;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface UserRepo extends JpaRepository<UserDomain, Long>, JpaSpecificationExecutor<UserDomain> {
    @Query("select o from UserDomain o where o.phoneNum=?1")
    UserDomain getUserByPhoneNum(String phoneNum);

    void deleteByPhoneNum(String phoneNum);

    void deleteByUserID(String userID);

    UserDomain findByUserID(String userID);

    
    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query("update UserDomain u set u.loginPWD=?1 where u.userID=?2")
    Integer updatePasswordById(String password,String uid);

    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query("update UserDomain u set u.shopID=?1 where u.userID=?2")
    Integer updateShopIDById(String shopID,String uid);

    @Modifying(clearAutomatically = true,flushAutomatically = true)
    @Query("update UserDomain u set u.loginPWD=?1 ,u.phoneNum = ?2 ,u.userName =?3 where u.userID=?4")
    Integer updateUserById(String password,String phoneNum,String userName,String uid);

    List<UserDomain> findAllByShopID(String shopID , Pageable pageable);
    Integer countByShopID(String shopID);
}
