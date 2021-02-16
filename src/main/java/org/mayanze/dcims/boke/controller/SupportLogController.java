package org.mayanze.dcims.boke.controller;


import org.mayanze.dcims.base.WebResponse;
import org.mayanze.dcims.boke.entity.SupportLog;
import org.mayanze.dcims.boke.service.ISupportLogService;
import org.mayanze.dcims.sys.service.IRequestLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.mayanze.dcims.base.BaseController;

/**
 * <p>
 * 点赞日志 前端控制器
 * </p>
 *
 * @author mayanze
 * @since 2021-02-15
 */
@RestController
@RequestMapping("/boke/support-log")
public class SupportLogController extends BaseController<ISupportLogService, SupportLog> {

}
