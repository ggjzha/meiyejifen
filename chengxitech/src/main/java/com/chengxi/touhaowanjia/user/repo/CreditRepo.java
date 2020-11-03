package com.chengxi.touhaowanjia.user.repo;

import com.chengxi.touhaowanjia.user.domain.CreditDomain;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CreditRepo extends JpaRepository<CreditDomain,Long> {
}
