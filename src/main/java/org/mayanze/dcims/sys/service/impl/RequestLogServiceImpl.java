package org.mayanze.dcims.sys.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.mayanze.dcims.sys.entity.RequestLog;
import org.mayanze.dcims.sys.mapper.RequestLogMapper;
import org.mayanze.dcims.sys.service.IRequestLogService;
import org.mayanze.dcims.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneOffset;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author mayanze
 * @since 2021-01-05
 */
@Service
public class RequestLogServiceImpl extends ServiceImpl<RequestLogMapper, RequestLog> implements IRequestLogService {

    private ObjectMapper objectMapper = new ObjectMapper();

    @Value("${requestLog.ignores}")
    private String ignores;// 忽略项

    /**
     * 记录日志
     *
     * @param retVal
     */
    @Override
    public void saveLog(Object retVal) {
        HttpServletRequest request = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getResponse();
        String currentTimeMillis = request.getAttribute("currentTimeMillis").toString();
        try {
            for (String ignore : ignores.split(",")) {
                if(request.getRequestURI().indexOf(ignore) >= 0){
                    return;
                }
            }
            String result = "";
            if (retVal != null) {
                result = retVal.toString();
            }
            //查询类请求响应太大，疑似查询的不予记录
            if (result.getBytes().length > 200) {//响应大于200b的不予记录
                result = "响应大于200b，不予记录";
            }

            String parmater = objectMapper.writeValueAsString(request.getParameterMap());
            if (request.getMethod().equals("POST")) {
                parmater = RequestUtils.getLines(request);
            }

            long time = System.currentTimeMillis() - Long.parseLong(currentTimeMillis);
            LocalDateTime createTime = Instant.ofEpochMilli(Long.parseLong(currentTimeMillis)).atZone(ZoneOffset.ofHours(8)).toLocalDateTime();

            String ipAddress = RequestUtils.getIpAddress(request);

            RequestLog requestLog = new RequestLog();
            requestLog.setRequestCreator("");
            requestLog.setRequestTime(createTime);
            requestLog.setRequestUrl(request.getRequestURI());
            requestLog.setRequestMenu("");
            requestLog.setResponseMsg(response.getStatus() + ",  " + result);
            requestLog.setRequestMethod(request.getMethod());
            requestLog.setRequestParmater(parmater);
            requestLog.setRequestIp(ipAddress);
            requestLog.setTime(time);
            this.save(requestLog);
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }
}
