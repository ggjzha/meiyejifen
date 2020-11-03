package com.service;

import com.chengxi.ChatApplication;
import com.chengxi.touhaowanjia.user.domain.UserDomain;
import com.chengxi.touhaowanjia.user.repo.UserRepo;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Commit;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;


@RunWith(SpringRunner.class)
@SpringBootTest(classes = ChatApplication.class)
public class UserServiceTest {

    @Autowired
    private UserRepo userRepo;


    @Test
    public void test(){
        System.out.println(userRepo);
        UserDomain byUserId = userRepo.findByUserID("userid46903c17-da1f-446d-915c-45c9baaee788");
        System.out.println(byUserId);
    }
    @Test
    @Transactional
    public void test2(){
        UserDomain userDomain = new UserDomain();
        userDomain.setUserID("www");
        userDomain.setPhoneNum("456");
        userDomain.setLoginPWD("789");
        userDomain.setShopID("gzj");
        UserDomain save = userRepo.save(userDomain);
        //int i = 1/0;
        Integer gzj = userRepo.updatePasswordById("123", "gzj");
        System.out.println(gzj);

        //userRepo.flush();
    }

    @Test
    @Transactional
    @Commit
    public void test3(){

        UserDomain userDomain = new UserDomain();
        userDomain.setUserID("www");
        userDomain.setPhoneNum("456");
        userDomain.setLoginPWD("789");
        userDomain.setShopID("gzj");
        UserDomain save = userRepo.saveAndFlush(userDomain);
        //userRepo.flush();
    }
}
