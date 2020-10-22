package org.mayanze.dcims.sys.entity;

import lombok.Data;
import org.mayanze.dcims.base.OperationLog;

import java.io.Serializable;

/**
 * <p>
 * 用户表
 * </p>
 *
 * @author mayanze
 * @since 2020-10-04
 */
@Data
public class User extends OperationLog implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * id
     */
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

}
