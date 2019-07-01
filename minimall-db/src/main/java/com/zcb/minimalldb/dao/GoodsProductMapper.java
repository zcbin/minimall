package com.zcb.minimalldb.dao;

import com.zcb.minimalldb.domain.GoodsProduct;
import com.zcb.minimalldb.domain.GoodsProductExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface GoodsProductMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    long countByExample(GoodsProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    int deleteByExample(GoodsProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    int insert(GoodsProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    int insertSelective(GoodsProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    GoodsProduct selectOneByExample(GoodsProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    GoodsProduct selectOneByExampleSelective(@Param("example") GoodsProductExample example, @Param("selective") GoodsProduct.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<GoodsProduct> selectByExampleSelective(@Param("example") GoodsProductExample example, @Param("selective") GoodsProduct.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    List<GoodsProduct> selectByExample(GoodsProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    GoodsProduct selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") GoodsProduct.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    GoodsProduct selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    GoodsProduct selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") GoodsProduct record, @Param("example") GoodsProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") GoodsProduct record, @Param("example") GoodsProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(GoodsProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(GoodsProduct record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") GoodsProductExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table goods_product
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}