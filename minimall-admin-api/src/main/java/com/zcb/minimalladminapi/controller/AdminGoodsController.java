package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimalladminapi.dto.GoodsData;
import com.zcb.minimalladminapi.vo.CatVo;
import com.zcb.minimalladminapi.vo.CategoryVo;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.*;
import com.zcb.minimalldb.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotNull;
import java.util.*;

/**
 * @author zcbin
 * @title: AdminGoodsController
 * @projectName minimall
 * @description: 商品
 * @date 2019/8/1 19:29
 */
@RestController
@CrossOrigin
@RequestMapping(value = "/admin/goods")
public class AdminGoodsController {
    @Autowired
    private IGoodsService goodsService;

    @Autowired
    private IGoodsSpecificationService goodsSpecificationService; //规格

    @Autowired
    private IGoodsProductService goodsProductService; //货品

    @Autowired
    private IGoodsAttributeService goodsAttributeService; //参数
    @Autowired
    private ICategoryService categoryService; //类目
    @GetMapping(value = "/list")
    public JSONObject list(String goodsSn, String name,
                           @RequestParam(defaultValue = "1") Integer page,
                           @RequestParam(defaultValue = "10") Integer limit,
                           @Sort @RequestParam(defaultValue = "add_time") String sort,
                           @Order @RequestParam(defaultValue = "desc") String order) {
        List<Goods> goodsList = goodsService.query(goodsSn, name, page, limit, sort, order);
        long total = PageInfo.of(goodsList).getTotal();
        Map<String, Object> data = new HashMap<>();
        data.put("total", total);
        data.put("items", goodsList);
        return ResponseUtil.ok(data);
    }

    /**
     * 分类数据
     * @return
     */
    @GetMapping(value = "/catAndBrand")
    public JSONObject list2() {
        //分类
        List<Category> l1List = categoryService.queryL1();
        List<CatVo> categoryList = new ArrayList<>(l1List.size());
        for (Category category : l1List) {
            CatVo catVo = new CatVo();
            catVo.setValue(category.getId());
            catVo.setLabel(category.getName());

            List<Category> l2List = categoryService.queryByPid(category.getId());
            List<CatVo> children = new ArrayList<>(l2List.size());
            for (Category l2 : l2List) {
                CatVo catVo1 = new CatVo();
                catVo1.setValue(l2.getId());
                catVo1.setLabel(l2.getName());
                children.add(catVo1);
            }
            catVo.setChildren(children);
            categoryList.add(catVo);
        }
        Map<String, Object> data = new HashMap<>();
        data.put("categoryList", categoryList);
        return ResponseUtil.ok(data);
    }

    //新增
    @PostMapping(value = "/create")
    public JSONObject create(@RequestBody GoodsData goodsData) {

        Goods goods = goodsData.getGoods();
        GoodsSpecification[] specifications = goodsData.getSpecifications();
        GoodsProduct[] products = goodsData.getProducts();
        GoodsAttribute[] attributes = goodsData.getAttributes();

        goodsService.add(goods);

        for (GoodsSpecification goodsSpecification : specifications) {
            goodsSpecification.setGoodsId(goods.getId());
            goodsSpecificationService.add(goodsSpecification);
        }
        for (GoodsProduct goodsProduct : products) {
            goodsProduct.setGoodsId(goods.getId());
            goodsProductService.add(goodsProduct);
        }
        for (GoodsAttribute goodsAttribute : attributes) {
            goodsAttribute.setGoodsId(goods.getId());
            goodsAttributeService.add(goodsAttribute);
        }

        return ResponseUtil.ok();

    }
    @PostMapping(value = "/delete")
    public JSONObject delete(@RequestBody Goods goods) {
        Integer id = goods.getId();
        if (id == null) {
            return ResponseUtil.badArgument();
        }

        goodsService.delete(id);
        goodsAttributeService.deleteByGid(id);
        goodsProductService.deleteByGid(id);
        goodsSpecificationService.delete(id);
        return ResponseUtil.ok();
    }
}
