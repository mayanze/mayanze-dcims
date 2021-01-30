package org.mayanze.dcims.sys.controller;


import org.mayanze.dcims.base.BaseController;
import org.mayanze.dcims.sys.entity.User;
import org.mayanze.dcims.sys.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author mayanze
 * @since 2020-10-04
 */
@RestController
@RequestMapping("/sys/user")
public class UserController extends BaseController<IUserService,User> {

    @GetMapping("/login")
    public String login(){
        return "{\"code\":20000,\"data\":{\"token\":\"admin-token\"}}";
    }

    @GetMapping("/info")
    public String info(){
        return "{\"code\":20000,\"data\":{\"token\":\"admin-token\"}}";
    }
}
