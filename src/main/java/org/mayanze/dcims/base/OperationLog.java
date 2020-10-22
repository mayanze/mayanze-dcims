package org.mayanze.dcims.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * author: mayanze
 * date: 2020/10/19 11:37 上午
 */
@Data
public class OperationLog {
    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;

    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    private String updater;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private LocalDateTime updateTime;

    /**
     * 删除标识
     */
    private int deleted;

    /**
     * 版本
     */
    @TableField(fill = FieldFill.UPDATE)
    private int version;

    /**
     * 备注
     */
    private String remarks;
}
