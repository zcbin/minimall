package com.zcb.minimalldb.service.impl;

import com.zcb.minimalldb.dao.GoodsSpecificationMapper;
import com.zcb.minimalldb.domain.GoodsSpecification;
import com.zcb.minimalldb.domain.GoodsSpecificationExample;
import com.zcb.minimalldb.service.IGoodsSpecificationService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zcbin
 * @title: GoodsSpecificationServiceImpl
 * @projectName minimall
 * @description: TODO
 * @date 2019/6/19 21:56
 */
@Service
public class GoodsSpecificationServiceImpl implements IGoodsSpecificationService {
    @Resource
    private GoodsSpecificationMapper goodsSpecificationMapper;
    @Override
    public List<GoodsSpecification> queryByGid(Integer gid) {
        GoodsSpecificationExample example = new GoodsSpecificationExample();
        example.or().andGoodsIdEqualTo(gid).andDeletedEqualTo(false);
        return goodsSpecificationMapper.selectByExample(example);
    }

    @Override
    public Object getSpecificationVoList(Integer gid) {
        List<GoodsSpecification> gsList = this.queryByGid(gid);

        Map<String, VO> map = new HashMap<>();
        List<VO> voList = new ArrayList<>();
        for (GoodsSpecification gs : gsList) {
            String specification = gs.getSpecification();
            VO vo = map.get(specification);
            if (vo == null) {
                vo = new VO();
                vo.setName(specification);
                List<GoodsSpecification> valueList = new ArrayList<>();
                valueList.add(gs);
                vo.setValueList(valueList);
                map.put(specification, vo);
                voList.add(vo);
            } else {
                List<GoodsSpecification> valueList = vo.getValueList();
                valueList.add(gs);
            }
        }
        return voList;
    }
    private class VO {
        private String name;
        private List<GoodsSpecification> valueList;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<GoodsSpecification> getValueList() {
            return valueList;
        }

        public void setValueList(List<GoodsSpecification> valueList) {
            this.valueList = valueList;
        }
    }

}
