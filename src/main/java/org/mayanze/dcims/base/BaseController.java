package org.mayanze.dcims.base;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import org.mayanze.dcims.utils.BasePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * author: mayanze
 * date: 2020/10/4 7:12 下午
 */
public class BaseController<T> {
    @Autowired
    private IService iService;

    @GetMapping(value = "/queryPage",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public IPage<T> queryPage() {
        BaseMapper<T> baseMapper = iService.getBaseMapper();
        BasePage<T> basePage = new BasePage<>();
        BasePage<T> TBasePage = baseMapper.selectPage(basePage, null);
        return TBasePage;
    }

    @GetMapping(value = "/queryList",produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public List<T> queryList() {
        return iService.list();
    }


    @PostMapping(value = "/saveOrUpdateBatch")
    public WebResponse saveOrUpdateBatch(String data,T t) {
        List<?> ts = JSON.parseArray(data, t.getClass());
        try {
            iService.saveOrUpdateBatch(ts);
            return new WebResponse().success();
        } catch (Exception e) {
            return new WebResponse().error(e.getMessage(),e.getStackTrace());
        }
    }

    @DeleteMapping("/removeByIds")
    public WebResponse removeByIds(String ids){
        try {
            iService.removeByIds(Arrays.asList(ids.split(",")));
            return new WebResponse().success();
        } catch (Exception e) {
            return new WebResponse().error(e.getMessage(),e.getStackTrace());
        }
    }
}
