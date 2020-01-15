package com.zcb.minimallsearch.service.impl;

import com.zcb.minimallsearch.dao.KeywordMapper;
import com.zcb.minimallsearch.domain.Keyword;
import com.zcb.minimallsearch.repository.KeywordRespository;
import com.zcb.minimallsearch.service.IKeywordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Iterator;
import java.util.List;

/**
 * @author zcbin
 * @title KeywordServiceImpl
 * @projectName minimall
 * @description
 * @date 2020/1/15 1:52 下午
 */
@Service
public class KeywordServiceImpl implements IKeywordService {
    @Autowired
    KeywordRespository keywordRespository;
    @Resource
    KeywordMapper keywordMapper;


    @Override
    public void importAll() {
        List<Keyword> keyList = keywordMapper.selectById(null);
        for (Keyword keyword : keyList) {
            System.out.println(keyword.toString());
        }
        Iterable<Keyword> iterable = keywordRespository.saveAll(keyList);
        Iterator<Keyword> iterator = iterable.iterator();
        int result = 0;
        while (iterator.hasNext()) {
            iterator.next();
            result ++;
        }


        System.out.println("import:" + result);
    }

    @Override
    public void deleteAll() {
        keywordRespository.deleteAll();
    }

    @Override
    public Page<Keyword> search(String keyword, Integer pageNum, Integer pageSize) {
        Pageable pageable = PageRequest.of(pageNum, pageSize);
        return keywordRespository.findKeywordByKeywordMatches(keyword, pageable);
    }
}
