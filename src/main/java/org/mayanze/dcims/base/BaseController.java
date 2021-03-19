package org.mayanze.dcims.base;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.conditions.query.QueryChainWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.mayanze.dcims.utils.RequestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import javax.servlet.http.HttpServletRequest;
import java.util.Arrays;
import java.util.List;

/**
 * author: mayanze
 * date: 2020/10/4 7:12 下午
 */
@Slf4j
public class BaseController<S extends IService<T>, T> {
    @Autowired
    private S iService;

    @PostMapping(value = "/queryPage", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse queryPage(HttpServletRequest request) {
        String lines = RequestUtils.getLines(request);
        Page<T> page = new Page();
        if(!StringUtils.isEmpty(lines)){
            page = JSON.parseObject(lines, page.getClass());
        }
        QueryWrapper queryWrapper = getQueryWrapper1(lines);
        return new WebResponse().success(iService.page(page, queryWrapper));
    }

    @GetMapping(value = "/queryList", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<T> queryList() {
        return iService.list();
    }

    @PostMapping(value = "/queryCount", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse queryCount(HttpServletRequest request) {
        QueryWrapper queryWrapper = getQueryWrapper(request);
        return new WebResponse().success(iService.count(queryWrapper));
    }

    private QueryWrapper getQueryWrapper(HttpServletRequest request) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        String lines = RequestUtils.getLines(request);
        if(!StringUtils.isEmpty(lines)){
            List<JSONObject> jsonObjects = JSON.parseArray(lines, JSONObject.class);
            for (JSONObject jsonObject : jsonObjects) {
                switch (jsonObject.getString("expression")){
                    case "eq":
                        queryWrapper.eq(jsonObject.getString("column"),jsonObject.get("val"));
                        break;

                }
            }
        }
        return queryWrapper;
    }

    private QueryWrapper getQueryWrapper1(String lines) {
        QueryWrapper queryWrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(lines)){
            JSONObject query = JSON.parseObject(lines, JSONObject.class);
            if(query.getString("condition") == null){
                return queryWrapper;
            }
            List<JSONObject> jsonObjects = JSON.parseArray(query.getString("condition"), JSONObject.class);
            for (JSONObject jsonObject : jsonObjects) {
                switch (jsonObject.getString("expression")){
                    case "eq":
                        queryWrapper.eq(jsonObject.getString("column"),jsonObject.get("val"));
                        break;
                    case "like":
                        if(jsonObject.get("val") != null){
                            queryWrapper.like(jsonObject.getString("column"),jsonObject.get("val"));
                        }
                        break;
                }
            }
        }
        return queryWrapper;
    }

    @GetMapping(value = "/getById", produces = MediaType.APPLICATION_JSON_VALUE)
    public T getById(String id) {
        return iService.getById(id);
    }


    @PostMapping(value = "/saveOrUpdateBatch")
    public WebResponse saveOrUpdateBatch(String data, T t) {
        List<T> ts = (List<T>) JSON.parseArray(data, t.getClass());
        try {
            iService.saveOrUpdateBatch(ts);
            return new WebResponse().success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new WebResponse().error(e.getMessage(), e.getStackTrace());
        }
    }

    @PostMapping(value = "/saveOrUpdate")
    public WebResponse saveOrUpdate(HttpServletRequest request, T t) {
        try {
            String lines = RequestUtils.getLines(request);
            if (!StringUtils.isEmpty(lines)) {
                t = (T) JSON.parseObject(lines, t.getClass());
                iService.saveOrUpdate(t);
            } else {
                iService.saveOrUpdate(t);
            }
            return new WebResponse().success();
        } catch (Exception e) {
            log.error(e.toString(), e);
            return new WebResponse().error(e.getMessage(), e.getStackTrace());
        }
    }

    @DeleteMapping("/removeByIds")
    public WebResponse removeByIds(String ids) {
        try {
            iService.removeByIds(Arrays.asList(ids.split(",")));
            return new WebResponse().success();
        } catch (Exception e) {
            return new WebResponse().error(e.getMessage(), e.getStackTrace());
        }
    }
}
