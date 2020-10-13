package org.mayanze.dcims.yl.service.impl;

import org.mayanze.dcims.yl.entity.Jzxx;
import org.mayanze.dcims.yl.mapper.JzxxMapper;
import org.mayanze.dcims.yl.service.IJzxxService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 就诊信息（挂号） 服务实现类
 * </p>
 *
 * @author mayanze
 * @since 2020-10-12
 */
@Service
@Primary
public class JzxxServiceImpl extends ServiceImpl<JzxxMapper, Jzxx> implements IJzxxService {

}
