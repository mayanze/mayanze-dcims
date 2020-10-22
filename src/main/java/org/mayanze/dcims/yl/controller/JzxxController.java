package org.mayanze.dcims.yl.controller;


import org.mayanze.dcims.yl.entity.Jzxx;
import org.mayanze.dcims.yl.service.IJzxxService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.mayanze.dcims.base.BaseController;

/**
 * <p>
 * 就诊信息（挂号） 前端控制器
 * </p>
 *
 * @author mayanze
 * @since 2020-10-12
 */
@RestController
@RequestMapping("/yl/jzxx")
public class JzxxController extends BaseController<IJzxxService,Jzxx> {

}
