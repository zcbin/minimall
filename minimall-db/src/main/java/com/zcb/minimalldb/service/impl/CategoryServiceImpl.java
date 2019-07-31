package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.CategoryMapper;
import com.zcb.minimalldb.domain.Category;
import com.zcb.minimalldb.domain.CategoryExample;
import com.zcb.minimalldb.service.ICategoryService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
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

    @Override
    public List<Category> queryL1() {
        CategoryExample example = new CategoryExample();
        example.or().andLevelEqualTo("L1").andDeletedEqualTo(false);
        return categoryMapper.selectByExampleSelective(example);
    }

    @Override
    public List<Category> queryByPid(Integer id) {
        CategoryExample example = new CategoryExample();
        example.or().andPidEqualTo(id).andDeletedEqualTo(false);
        return categoryMapper.selectByExample(example);
    }

    @Override
    public Category findById(Integer id) {
        return categoryMapper.selectByPrimaryKey(id);
    }

    @Override
    public int add(Category category) {
        category.setAddTime(LocalDateTime.now());
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.insert(category);
    }

    @Override
    public int update(Category category) {
        category.setUpdateTime(LocalDateTime.now());
        return categoryMapper.updateByPrimaryKeySelective(category);
    }

    @Override
    public int delete(Integer id) {
        return categoryMapper.deleteByPrimaryKey(id);
    }
}
