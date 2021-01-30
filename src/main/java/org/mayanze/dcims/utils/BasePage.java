package org.mayanze.dcims.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 适配 jQuery MiniUI 页面分页格式
 * author: mayanze
 * date: 2020/9/23 11:53 上午
 */
public class BasePage<T> extends Page<T> {
    private String code="2000";
    public List<T> data;
    public void setData(List<T> data) {
        this.data = data;
    }

    @Override
    public Page<T> setRecords(List<T> records) {
        this.data = records;
        return super.setRecords(records);
    }
}
