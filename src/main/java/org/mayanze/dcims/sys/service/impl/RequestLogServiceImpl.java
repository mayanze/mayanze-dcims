package org.mayanze.dcims.sys.service.impl;

import org.mayanze.dcims.sys.entity.RequestLog;
import org.mayanze.dcims.sys.mapper.RequestLogMapper;
import org.mayanze.dcims.sys.service.IRequestLogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author mayanze
 * @since 2021-01-05
 */
@Service
public class RequestLogServiceImpl extends ServiceImpl<RequestLogMapper, RequestLog> implements IRequestLogService {

}
