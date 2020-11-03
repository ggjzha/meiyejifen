package com.chengxi.touhaowanjia.user.repo;

import com.chengxi.touhaowanjia.user.domain.CouponDomain;
import com.chengxi.touhaowanjia.user.domain.ShopDomain;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShopRepo extends JpaRepository<ShopDomain,Long> {
    ShopDomain findByShopID(String shopID);

    List<ShopDomain> findAllBy( Pageable of);
}
