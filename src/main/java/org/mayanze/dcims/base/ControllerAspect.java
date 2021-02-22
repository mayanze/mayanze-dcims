package org.mayanze.dcims.base;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;
import org.mayanze.dcims.sys.service.IRequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 控制层切面
 * author: mayanze
 * date: 2021/1/6 5:11 下午
 */
@Slf4j
@Component
@Aspect
public class ControllerAspect {
    @Autowired
    private IRequestLogService requestLogService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Pointcut("execution(* org.mayanze.dcims.*.*Controller.*(..))")
    public void lService() {
    }


    @Before("lService()")
    public void doAccessCheck(JoinPoint jp) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        request.setAttribute("currentTimeMillis", System.currentTimeMillis());//此参数在AfterReturning使用
    }

    @AfterReturning(
            pointcut = "lService()",
            returning = "retVal")
    public void afterReturning(Object retVal) {
        //记录请求日志
        requestLogService.saveLog(retVal);
    }


    @AfterThrowing(value = "lService()",throwing = "ex")
    public void afterThrowing(Throwable ex) {
        afterReturning(ex.getMessage());
    }
}
