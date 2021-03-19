package org.mayanze.dcims.sys.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 
 * </p>
 *
 * @author mayanze
 * @since 2021-03-18
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Role implements Serializable {

    private static final long serialVersionUID = 1L;
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 角色名称
     */
    private String name;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 备注（角色描述）
     */
    private String remarks;


}
