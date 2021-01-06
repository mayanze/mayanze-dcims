package org.mayanze.dcims.sys.entity;

import java.time.LocalDateTime;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 请求日志
 * </p>
 *
 * @author mayanze
 * @since 2021-01-05
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class RequestLog implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 请求ID
     */
    @TableId(type = IdType.ASSIGN_UUID)
    private String requestId;

    /**
     * 请求菜单
     */
    private String requestMenu;

    /**
     * 请求地址
     */
    private String requestUrl;

    /**
     * 请求人
     */
    private String requestCreator;

    /**
     * 请求时间
     */
    private LocalDateTime requestCreateTime;

    /**
     * 效应消息
     */
    private String responseMsg;

    /**
     * ip地址
     */
    private String requestIpAddress;

    /**
     * 请求参数
     */
    private String requestParmater;

    /**
     * 请求类型
     */
    private String requestMethod;
}
