package org.mayanze.dcims.utils;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 适配 jQuery MiniUI 页面分页格式
 * author: mayanze
 * date: 2020/9/23 11:53 上午
 */
public class BasePage<T> extends Page<T> {
    public List<T> data;

    @Override
    public Page<T> setRecords(List<T> records) {
        this.data = records;
        return this;
    }
}
