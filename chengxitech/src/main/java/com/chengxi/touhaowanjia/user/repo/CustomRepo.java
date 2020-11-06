package com.chengxi.touhaowanjia.user.repo;

import com.chengxi.touhaowanjia.user.domain.CustomDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomRepo extends JpaRepository<CustomDomain,Long> {
    CustomDomain findByCustomID(String CustomID);
}
