package org.mayanze.dcims.boke.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 留言表
 * </p>
 *
 * @author mayanze
 * @since 2021-02-14
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("boke_message")
public class Message implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(type = IdType.ASSIGN_UUID)
    private String id;

    /**
     * 留言人
     */
    private String userName;

    /**
     * 留言内容
     */
    private String content;

    /**
     * 留言时间
     */
    private LocalDateTime date;


}
