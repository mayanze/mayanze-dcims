package org.mayanze.dcims.yl.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.mayanze.dcims.sys.entity.User;
import org.mayanze.dcims.sys.service.IUserService;
import org.mayanze.dcims.yl.entity.Jzxx;
import org.mayanze.dcims.yl.mapper.JzxxMapper;
import org.mayanze.dcims.yl.service.IJzxxService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 就诊信息（挂号） 服务实现类
 * </p>
 *
 * @author mayanze
 * @since 2020-10-12
 */
@Service
public class JzxxServiceImpl extends ServiceImpl<JzxxMapper, Jzxx> implements IJzxxService {
    @Autowired
    private IUserService userService;
    @Override
    public <E extends IPage<Jzxx>> E page(E page) {
        E page1 = super.page(page);
        List<Jzxx> records = page1.getRecords();
        for (Jzxx record : records) {
            if(!StringUtils.isEmpty(record.getUserId())){
                User u = userService.getById(record.getUserId());
                record.setUserName(u.getName());
            }
        }
        return page1;
    }
}
