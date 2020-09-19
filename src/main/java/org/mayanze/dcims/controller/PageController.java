package org.mayanze.dcims.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 页面控制层
 * author: mayanze
 * date: 2020/9/19 4:55 下午
 */
@Controller
public class PageController {
    @RequestMapping("/index")
    public String commonPage(){
        return "index";
    }
}
