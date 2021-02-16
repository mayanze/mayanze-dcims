package org.mayanze.dcims.boke.service.impl;

import org.mayanze.dcims.boke.entity.Message;
import org.mayanze.dcims.boke.mapper.MessageMapper;
import org.mayanze.dcims.boke.service.IMessageService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 留言表 服务实现类
 * </p>
 *
 * @author mayanze
 * @since 2021-02-14
 */
@Service
public class MessageServiceImpl extends ServiceImpl<MessageMapper, Message> implements IMessageService {

}
