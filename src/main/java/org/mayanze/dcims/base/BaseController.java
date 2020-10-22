package org.mayanze.dcims.base;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import lombok.extern.slf4j.Slf4j;
import org.mayanze.dcims.utils.BasePage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.Arrays;
import java.util.List;

/**
 * author: mayanze
 * date: 2020/10/4 7:12 下午
 */
@Slf4j
public class BaseController<S extends IService<T>,T> {
    @Autowired
    private S iService;

    @GetMapping(value = "/queryPage",produces = MediaType.APPLICATION_JSON_VALUE)
    public IPage<T> queryPage() {
        BasePage<T> basePage = new BasePage<>();
        BasePage<T> page = iService.page(basePage);
        return page;
    }

    @GetMapping(value = "/queryList",produces = MediaType.APPLICATION_JSON_VALUE)
    public List<T> queryList() {
        return iService.list();
    }

    @GetMapping(value = "/getById",produces = MediaType.APPLICATION_JSON_VALUE)
    public T getById(String id){
        return iService.getById(id);
    }


    @PostMapping(value = "/saveOrUpdateBatch")
    public WebResponse saveOrUpdateBatch(String data,T t) {
        List<T> ts = (List<T>) JSON.parseArray(data, t.getClass());
        try {
            iService.saveOrUpdateBatch(ts);
            return new WebResponse().success();
        } catch (Exception e) {
            log.error(e.toString(),e);
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
