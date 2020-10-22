package org.mayanze.dcims.yl.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mayanze.dcims.base.OperationLog;

/**
 * <p>
 * 就诊信息（挂号）
 * </p>
 *
 * @author mayanze
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Jzxx extends OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
//    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 病人id(用户id)
     */
    private String userId;

    /**
     *
     */
    @TableField(exist=false)
    private String userName;

    /**
     * 就诊号
     */
    private String code;

}
