package org.mayanze.dcims.filter;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Component
@WebFilter(urlPatterns = "/*", filterName = "commonFilter")
public class CommonFilter implements Filter {

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
        String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
                + request.getContextPath();
        request.setAttribute("ctx", basePath);
//        request.setAttribute("ctx", "https://iot.sinotrans.com/iot/obor");
        request.setAttribute("cdnctx", "https://uat.iot.sinotrans.com/iotcdn");
        request.setAttribute("sys_version", Math.random());
        chain.doFilter(req, resp);
    }
}
