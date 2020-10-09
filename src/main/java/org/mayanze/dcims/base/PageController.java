package org.mayanze.dcims.base;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;

/**
 * 页面控制层
 * author: mayanze
 * date: 2020/9/19 4:55 下午
 */
@Controller
public class PageController {
    
    @GetMapping(value = "/pages/**")
    public String toPage(HttpServletRequest request){
        String requestURI = request.getRequestURI();
        return requestURI.substring(requestURI.indexOf("pages")-1);
    }
}
