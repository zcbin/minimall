package com.zcb.minimallsearch;

import com.zcb.minimallsearch.domain.Goods;
import com.zcb.minimallsearch.service.IGoodsService;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles({"db", "core", "search"})

class MinimallSearchApplicationTests {

    @Autowired
    private IGoodsService IGoodsService;
    @Test
    void contextLoads() {
        System.out.println(IGoodsService.count());
    }

    @Test
    void save() {
        Goods goods = new Goods();
        goods.setId(4L);
        goods.setGoodsSn("00000011126");
        goods.setName("棉袄4");
        goods.setKeywords("Spring Data 的使命是给各种数据访问提供统一的编程接口，不管是关系型数据库（如MySQL），还是非关系数据库（如Redis），或者类似Elasticsearch这样的索引数据库。从而简化开发人员的代码，提高开发效率");
        goods.setDeleted(false);
        goods.setDetail("dlsid");
        System.out.println(IGoodsService.save(goods));

    }

}
