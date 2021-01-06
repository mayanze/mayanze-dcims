package org.mayanze.dcims.filter;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import org.mayanze.dcims.sys.entity.RequestLog;
import org.mayanze.dcims.sys.service.IRequestLogService;
import org.mayanze.dcims.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Date;

@Component
@WebFilter(urlPatterns = "/*", filterName = "commonFilter")
public class CommonFilter implements Filter {
    @Autowired
    private IRequestLogService requestLogService;
    private ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        // TODO Auto-generated method stub

    }

    @Override
    public void destroy() {
        // TODO Auto-generated method stub
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) req;
        HttpServletResponse response = (HttpServletResponse) resp;
        reqLog(request,response);//记录请求日志
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
        request.setAttribute("ctx", basePath);
        request.setAttribute("sys_version", Math.random());
        chain.doFilter(req, resp);
    }

    /**
     * 请求日志
     * @param request
     */
    @SneakyThrows
    public void reqLog(HttpServletRequest request, HttpServletResponse response){
        RequestLog requestLog = new RequestLog();
        requestLog.setRequestCreator("");
        requestLog.setRequestCreateTime(LocalDateTime.now());
        requestLog.setRequestUrl(request.getRequestURI());
        requestLog.setRequestMenu("");
        requestLog.setResponseMsg(response.getStatus()+"");
        requestLog.setRequestMethod(request.getMethod());
        String parmater = objectMapper.writeValueAsString(request.getParameterMap());
        if(request.getMethod().equals("POST")){
            parmater = RequestUtils.getLines(request);
        }
        requestLog.setRequestParmater(parmater);
        requestLogService.save(requestLog);
    }
}
