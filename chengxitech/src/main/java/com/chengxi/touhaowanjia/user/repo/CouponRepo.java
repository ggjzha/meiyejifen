package com.chengxi.user.repo;

import com.chengxi.touhaowanjia.user.domain.CouponDomain;
import com.chengxi.touhaowanjia.user.domain.UserDomain;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepo extends JpaRepository<CouponDomain,Long> {
    boolean deleteByCouponID(String couponID);
    CouponDomain findByCouponID(String couponID);
    Integer countByShopID(String shopID);

    List<CouponDomain> findAllByShopID(String shopID, PageRequest of);
}
