package com.zcb.minimalladminapi.dto;

import com.zcb.minimalldb.domain.Goods;
import com.zcb.minimalldb.domain.GoodsAttribute;
import com.zcb.minimalldb.domain.GoodsProduct;
import com.zcb.minimalldb.domain.GoodsSpecification;

/**
 * @author zcbin
 * @title: GoodsData
 * @projectName minimall
 * @description: TODO
 * @date 2019/8/3 14:21
 */
public class GoodsData {
    Goods goods;
    GoodsSpecification[] specifications;
    GoodsProduct[] products;
    GoodsAttribute[] attributes;

    public Goods getGoods() {
        return goods;
    }

    public void setGoods(Goods goods) {
        this.goods = goods;
    }

    public GoodsSpecification[] getSpecifications() {
        return specifications;
    }

    public void setSpecifications(GoodsSpecification[] specifications) {
        this.specifications = specifications;
    }

    public GoodsProduct[] getProducts() {
        return products;
    }

    public void setProducts(GoodsProduct[] products) {
        this.products = products;
    }

    public GoodsAttribute[] getAttributes() {
        return attributes;
    }

    public void setAttributes(GoodsAttribute[] attributes) {
        this.attributes = attributes;
    }
}
