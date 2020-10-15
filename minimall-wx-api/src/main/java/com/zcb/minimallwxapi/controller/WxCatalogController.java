package com.zcb.minimallwxapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Category;
import com.zcb.minimalldb.service.ICategoryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: WxCatalogController
 * @projectName minimall
 * @description: 分类
 * @date 2019/6/30 19:18
 */

@RestController
@RequestMapping(value = "/wx/catalog")
public class WxCatalogController {
    private static final Logger LOGGER = LogManager.getLogger();

    @Autowired
    private ICategoryService categoryService; //分类服务

    /**
     * 分类
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/index")
    public JSONObject catalogIndex(Integer id) {
        //一级分类
        List<Category> categoryList = categoryService.queryL1();
        Category category = null; //当前一级分类
        if (categoryList != null && categoryList.size() > 0) {
//            if (id != null) {
//                category = categoryService.findById(id);
//            } else {
//                category = categoryList.get(0);
//            }
            category = categoryList.get(0);
        }
        //二级分类
        List<Category> secondList = null;
        if (category != null) {
            secondList = categoryService.queryByPid(category.getId());
        }
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("categoryList", categoryList);
        data.put("currentCategory", category);
        data.put("currentSubCategory", secondList);
        return ResponseUtil.ok(data);

    }

    /**
     * 二级分类
     *
     * @param id
     * @return
     */
    @RequestMapping(value = "/current")
    public JSONObject secondCategory(@NotNull Integer id) {
        Category category = categoryService.findById(id);
        if (category == null) {
            return ResponseUtil.badArgumentValue();
        }
        List<Category> secondList = categoryService.queryByPid(category.getId());
        Map<String, Object> data = new HashMap<String, Object>();
        data.put("currentCategory", category);
        data.put("currentSubCategory", secondList);
        return ResponseUtil.ok(data);
    }
}
