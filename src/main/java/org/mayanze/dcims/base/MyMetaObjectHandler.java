package org.mayanze.dcims.base;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

/**
 * author: mayanze
 * date: 2020/10/19 3:15 下午
 */
@Slf4j
@Component
public class MyMetaObjectHandler implements MetaObjectHandler {
    private final static String USER = "mayanze";

    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("start insert fill ....");
        this.strictInsertFill(metaObject, "createTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐使用)
        this.strictInsertFill(metaObject, "creator", String.class, USER); // 起始版本 3.3.0(推荐使用)
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        log.info("start update fill ....");
        this.strictUpdateFill(metaObject, "updateTime", LocalDateTime.class, LocalDateTime.now()); // 起始版本 3.3.0(推荐)
        this.strictUpdateFill(metaObject, "updater", String.class, USER);
        this.strictUpdateFill(metaObject, "version", Integer.class, (Integer)metaObject.getValue("version")+1);

    }
}
