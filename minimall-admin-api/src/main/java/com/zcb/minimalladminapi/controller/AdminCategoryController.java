package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.zcb.minimalladminapi.vo.CategoryVo;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimalldb.domain.Category;
import com.zcb.minimalldb.service.ICategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: AdminCategoryController
 * @projectName minimall
 * @description: 商品类目
 * @date 2019/7/23 19:46
 */
@RestController
@RequestMapping(value = "/admin/category")
public class AdminCategoryController {
    @Autowired
    private ICategoryService categoryService;

    /**
     * 列表
     * @return
     */
    @GetMapping(value = "/list")
    public JSONObject list() {
        List<CategoryVo> categoryVoList = new ArrayList<>();

        List<Category> categoryList = categoryService.queryByPid(0); //父级类目
        for (Category category : categoryList) {
            CategoryVo categoryVO = new CategoryVo();
            categoryVO.setId(category.getId());
            categoryVO.setDesc(category.getDesc());
            categoryVO.setIconUrl(category.getIconUrl());
            categoryVO.setPicUrl(category.getPicUrl());
            categoryVO.setKeywords(category.getKeywords());
            categoryVO.setName(category.getName());
            categoryVO.setLevel(category.getLevel());

            List<CategoryVo> children = new ArrayList<>();
            List<Category> subCategoryList = categoryService.queryByPid(category.getId()); //子类目
            for (Category subCategory : subCategoryList) {
                CategoryVo subCategoryVo = new CategoryVo();
                subCategoryVo.setId(subCategory.getId());
                subCategoryVo.setDesc(subCategory.getDesc());
                subCategoryVo.setIconUrl(subCategory.getIconUrl());
                subCategoryVo.setPicUrl(subCategory.getPicUrl());
                subCategoryVo.setKeywords(subCategory.getKeywords());
                subCategoryVo.setName(subCategory.getName());
                subCategoryVo.setLevel(subCategory.getLevel());
                children.add(subCategoryVo); //子
            }

            categoryVO.setChildren(children);
            categoryVoList.add(categoryVO);
        }

        return ResponseUtil.ok(categoryVoList);
    }

    @PostMapping(value = "/create")
    public JSONObject add(@RequestBody Category category) {
        categoryService.add(category);
        return ResponseUtil.ok(category);
    }

    @PostMapping(value = "/update")
    public JSONObject update(@RequestBody Category category) {
        if (categoryService.update(category) == 0) {
            return ResponseUtil.updatedDataFailed();
        }
        return ResponseUtil.ok();
    }
    @PostMapping(value = "/delete")
    public JSONObject delete(@RequestBody Category category) {
        Integer id = category.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        categoryService.delete(id);
        return ResponseUtil.ok();
    }
    @GetMapping(value = "/l1")
    public JSONObject getL1() {
        List<Category> categoryList = categoryService.queryL1();
        List<Map<String, Object>> data = new ArrayList<>(categoryList.size());
        for (Category category : categoryList) {
            Map<String, Object> d = new HashMap<>(2);
            d.put("value", category.getId());
            d.put("label", category.getName());
            data.add(d);
        }
        return ResponseUtil.ok(data);
    }

}
