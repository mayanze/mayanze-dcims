package org.mayanze.dcims.sys.controller;


import org.mayanze.dcims.sys.entity.RequestLog;
import org.mayanze.dcims.sys.service.IRequestLogService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.mayanze.dcims.base.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mayanze
 * @since 2021-01-05
 */
@RestController
@RequestMapping("/sys/request-log")
public class RequestLogController extends BaseController<IRequestLogService, RequestLog> {

}
