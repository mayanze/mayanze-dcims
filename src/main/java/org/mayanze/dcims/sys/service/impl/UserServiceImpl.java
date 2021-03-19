package org.mayanze.dcims.sys.service.impl;

import org.mayanze.dcims.sys.entity.User;
import org.mayanze.dcims.sys.mapper.UserMapper;
import org.mayanze.dcims.sys.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author mayanze
 * @since 2021-03-18
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
