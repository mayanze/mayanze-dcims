package org.mayanze.dcims.boke.controller;


import org.mayanze.dcims.boke.entity.Message;
import org.mayanze.dcims.boke.service.IMessageService;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;
import org.mayanze.dcims.base.BaseController;

/**
 * <p>
 * 留言表 前端控制器
 * </p>
 *
 * @author mayanze
 * @since 2021-02-14
 */
@RestController
@RequestMapping("/boke/message")
public class MessageController extends BaseController<IMessageService, Message> {

}
