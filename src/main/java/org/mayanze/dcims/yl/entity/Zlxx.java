package org.mayanze.dcims.yl.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.mayanze.dcims.base.OperationLog;

/**
 * <p>
 * 治疗信息
 * </p>
 *
 * @author mayanze
 * @since 2020-10-12
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class Zlxx extends OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 治疗项目名称
     */
    private String name;

    /**
     * 治疗项目描述
     */
    private String describe;

    /**
     * 治疗牙齿
     */
    private String tooth;

    /**
     * 治疗费用
     */
    private Double cost;
}
