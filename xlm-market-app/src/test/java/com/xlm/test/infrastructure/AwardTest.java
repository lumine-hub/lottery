package com.xlm.test.infrastructure;

import com.xlm.infrastructure.persistent.dao.IAwardDao;
import com.xlm.infrastructure.persistent.po.Award;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * @author xlm
 * 2024/7/16 下午7:47
 */
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest
public class AwardTest {

    @Autowired
    private IAwardDao awardDao;

    @Test
    public void queryAwardList() {
        List<Award> awards = awardDao.queryAwardList();
        System.out.println(awards);
    }
}
