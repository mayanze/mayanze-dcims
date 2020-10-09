package org.mayanze.dcims.sys.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author mayanze
 * @since 2020-10-04
 */
@Data
public class User {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String id;
    /**
     * 姓名
     */
    private String name;

    /**
     * 编号
     */
    private String code;

    /**
     * 性别
     */
    private Integer sex;

    /**
     * 年龄
     */
    private Integer age;

    /**
     * 身份证号
     */
    private String idCard;

    /**
     * 住址
     */
    private String address;

    /**
     * 电话号码
     */
    private Long phoneNo;

    /**
     * 角色ID
     */
    private Integer roleId;

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
