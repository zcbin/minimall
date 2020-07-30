package com.zcb.minimalladminapi.controller;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.zcb.minimalladminapi.annotation.RequiresPermissionsDesc;
import com.zcb.minimalladminapi.dto.GoodsData;
import com.zcb.minimalladminapi.vo.CatVo;
import com.zcb.minimalladminapi.vo.CategoryVo;
import com.zcb.minimallcore.util.ResponseUtil;
import com.zcb.minimallcore.validator.Order;
import com.zcb.minimallcore.validator.Sort;
import com.zcb.minimalldb.domain.*;
import com.zcb.minimalldb.service.*;
import org.apache.shiro.authz.annotation.RequiresPermissions;
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

    /**
     * 列表
     *
     * @param goodsSn 商品编号
     * @param name    名称
     * @param page
     * @param limit
     * @param sort
     * @param order
     * @return
     */
    @RequiresPermissions("admin:goods:list")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "查询")
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
     *
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

    /**
     * 新增
     *
     * @param goodsData
     * @return
     */
    @RequiresPermissions("admin:goods:create")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "上架")
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

    /**
     * 更新
     *
     * @param goodsData
     * @return
     */
    @RequiresPermissions("admin:goods:update")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "编辑")
    @PostMapping(value = "/update")
    public JSONObject update(@RequestBody GoodsData goodsData) {
        Goods goods = goodsData.getGoods();
        GoodsSpecification[] goodsSpecifications = goodsData.getSpecifications();
        GoodsProduct[] goodsProducts = goodsData.getProducts();
        GoodsAttribute[] goodsAttributes = goodsData.getAttributes();
        goodsService.update(goods);
        Integer id = goods.getId();
        goodsSpecificationService.deleteByGid(id);
        goodsProductService.deleteByGid(id);
        goodsAttributeService.deleteByGid(id);
        for (GoodsSpecification goodsSpecification : goodsSpecifications) {
            goodsSpecification.setGoodsId(goods.getId());
            goodsSpecificationService.add(goodsSpecification);
        }
        for (GoodsProduct goodsProduct : goodsProducts) {
            goodsProduct.setGoodsId(goods.getId());
            goodsProductService.add(goodsProduct);
        }
        for (GoodsAttribute goodsAttribute : goodsAttributes) {
            goodsAttribute.setGoodsId(goods.getId());
            goodsAttributeService.add(goodsAttribute);
        }
        return ResponseUtil.ok();

    }

    /**
     * 删除
     *
     * @param goods
     * @return
     */
    @RequiresPermissions("admin:goods:delete")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "删除")
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

    /**
     * 详细信息
     *
     * @param id
     * @return
     */
    @RequiresPermissions("admin:goods:read")
    @RequiresPermissionsDesc(menu = {"商品管理", "商品管理"}, button = "详情")
    @GetMapping(value = "/detail")
    public JSONObject detail(@NotNull Integer id) {
        if (id == null) {
            return ResponseUtil.badArgument();
        }
        Goods goods = goodsService.findById(id);
        List<GoodsSpecification> goodsSpecification = goodsSpecificationService.findByGid(id);
        List<GoodsAttribute> goodsAttribute = goodsAttributeService.findByGid(id);
        List<GoodsProduct> goodsProduct = goodsProductService.findByGid(id);

        Integer categoryId = goods.getCategoryId();
        Integer categoryIds[] = new Integer[]{};
        Category category = categoryService.findById(categoryId);
        if (category != null) {
            Integer parentId = category.getPid();
            categoryIds = new Integer[]{parentId, categoryId};
        }

        Map<String, Object> data = new HashMap<>();
        data.put("goods", goods);
        data.put("specifications", goodsSpecification);
        data.put("products", goodsProduct);
        data.put("attributes", goodsAttribute);
        data.put("categoryIds", categoryIds);
        return ResponseUtil.ok(data);
    }
}
