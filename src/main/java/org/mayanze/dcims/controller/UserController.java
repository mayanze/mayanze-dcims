package org.mayanze.dcims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 用户
 * author: mayanze
 * date: 2020/9/18 4:20 下午
 */
@Controller
@RequestMapping("/user")
public class UserController {
    @GetMapping("/page")
    public String userPage(){
        return "index";
    }
}
