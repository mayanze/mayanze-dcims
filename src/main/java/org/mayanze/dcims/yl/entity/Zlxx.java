package org.mayanze.dcims.yl.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class Zlxx implements Serializable {

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

    /**
     * 创建人
     */
    private String creator;

    /**
     * 创建时间
     */
    private LocalDateTime createTime;

    /**
     * 修改人
     */
    private String modifier;

    /**
     * 修改时间
     */
    private LocalDateTime modifyTime;

    /**
     * 删除标识
     */
    private Integer deleteFlag;

    /**
     * 备注
     */
    private String remarks;


}
