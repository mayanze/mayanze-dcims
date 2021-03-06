package org.mayanze.dcims.sys.controller;


import org.mayanze.dcims.sys.entity.RequestLog;
import org.mayanze.dcims.sys.entity.User;
import org.mayanze.dcims.sys.service.IRequestLogService;
import org.mayanze.dcims.sys.service.IUserService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.mayanze.dcims.base.BaseController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author mayanze
 * @since 2021-03-18
 */
@RestController
@RequestMapping("/sys/user")
public class UserController extends BaseController<IUserService, User> {

}
