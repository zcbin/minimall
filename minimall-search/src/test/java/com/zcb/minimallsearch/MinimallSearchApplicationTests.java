package com.zcb.minimallsearch;

import com.alibaba.druid.sql.visitor.functions.Isnull;
import com.zcb.minimallsearch.domain.Goods;
import com.zcb.minimallsearch.domain.Keyword;
import com.zcb.minimallsearch.service.IGoodsService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
@ActiveProfiles({"db", "core", "search"})

public class MinimallSearchApplicationTests {

    @Autowired
    private ElasticsearchTemplate elasticsearchTemplate;


    @Test
    public void createIndex() {
        elasticsearchTemplate.createIndex(Goods.class);
    }

    @Test
    public void createIndex1() {
        elasticsearchTemplate.createIndex(Keyword.class);
    }

    @Test
    public void deleteIndex() {
        elasticsearchTemplate.deleteIndex(Goods.class);
    }

}
