package org.mayanze.dcims;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mayanze.dcims.sys.entity.User;
import org.mayanze.dcims.sys.mapper.UserMapper;
import org.mayanze.dcims.utils.BasePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * 用户测试
 * author: mayanze
 * date: 2020/9/20 2:19 下午
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class UserTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void testSelect() {
        System.out.println(("----- selectAll method test ------"));
        List<User> userList = userMapper.selectList(null);
        userList.forEach(System.out::println);
    }

    @Test
    public void testPage(){
        BasePage page = new BasePage();
        BasePage<User> userIPage = userMapper.selectPage(page, null);
        System.out.println(userIPage);
    }

    @Test
    public void testInsert(){
        User user = new User();
        user.setCode("mayanze");
        user.setName("马艳泽");
//        user.setRoleId();
        int insert = userMapper.insert(user);
        Assert.assertEquals(insert,1);
    }
}
