package com.chengxi.touhaowanjia.user.repo;

import com.chengxi.touhaowanjia.user.domain.CouponDomain;
import com.chengxi.touhaowanjia.user.domain.ProjectDomain;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProjectRepo extends JpaRepository<ProjectDomain,Long> {

    Integer countByShopID(String shopID);

    ProjectDomain findByProjectID(String projectID);

    void deleteByProjectID(String projectID);

    List<ProjectDomain> findAllByShopID(String shopID, PageRequest of);
}
