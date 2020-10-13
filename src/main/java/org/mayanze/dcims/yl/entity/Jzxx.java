package org.mayanze.dcims.yl.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

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
public class Jzxx implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 病人id(用户id)
     */
    private String userId;

    /**
     * 就诊号
     */
    private String code;

    /**
     * 就诊时间
     */
    private LocalDateTime time;

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
