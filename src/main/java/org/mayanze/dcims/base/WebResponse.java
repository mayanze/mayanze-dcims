package org.mayanze.dcims.base;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 页面响应
 * author: mayanze
 * date: 2020/10/8 10:17 上午
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class WebResponse {
    private boolean success;
    private String message;
    private StackTraceElement[] stackTrace;

    public WebResponse success(){
        this.success = true;
        return this;
    }

    public WebResponse error(String message,StackTraceElement[] stackTrace){
        this.success = false;
        this.message = message;
        this.stackTrace = stackTrace;
        return this;
    }
}
