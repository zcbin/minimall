package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.CategoryMapper;
import com.zcb.minimalldb.domain.Category;
import com.zcb.minimalldb.domain.CategoryExample;
import com.zcb.minimalldb.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
@Service
public class CategoryServiceImpl implements ICategoryService {
    @Resource
    private CategoryMapper categoryMapper;
    private Category.Column[] CHANNEL = {Category.Column.id, Category.Column.name, Category.Column.desc, Category.Column.iconUrl};
    @Override
    public List<Category> queryChannel(String level) {
        CategoryExample example = new CategoryExample();
        example.or().andLevelEqualTo(level).andDeletedEqualTo(false);
        return categoryMapper.selectByExampleSelective(example, CHANNEL);
    }
}
