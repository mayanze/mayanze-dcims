package org.mayanze.dcims.sys.controller;


import org.mayanze.dcims.sys.entity.RequestLog;
import org.mayanze.dcims.sys.entity.Role;
import org.mayanze.dcims.sys.service.IRequestLogService;
import org.mayanze.dcims.sys.service.IRoleService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.mayanze.dcims.base.BaseController;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author mayanze
 * @since 2021-03-18
 */
@RestController
@RequestMapping("/sys/role")
public class RoleController extends BaseController<IRoleService, Role> {

}
