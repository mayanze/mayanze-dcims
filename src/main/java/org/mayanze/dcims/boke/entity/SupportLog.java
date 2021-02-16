package org.mayanze.dcims.boke.entity;

import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 点赞日志
 * </p>
 *
 * @author mayanze
 * @since 2021-02-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class SupportLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private String id;

    /**
     * 点赞IP
     */
    private String ip;

    /**
     * 点赞页面
     */
    private String page;

    /**
     * 点赞日期
     */
    private LocalDateTime date;


}
