package com.zcb.minimalldb.dao;

import com.zcb.minimalldb.domain.Feedback;
import com.zcb.minimalldb.domain.FeedbackExample;
import java.util.List;
import org.apache.ibatis.annotations.Param;

public interface FeedbackMapper {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     */
    long countByExample(FeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     */
    int deleteByExample(FeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     */
    int deleteByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     */
    int insert(Feedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     */
    int insertSelective(Feedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Feedback selectOneByExample(FeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Feedback selectOneByExampleSelective(@Param("example") FeedbackExample example, @Param("selective") Feedback.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    List<Feedback> selectByExampleSelective(@Param("example") FeedbackExample example, @Param("selective") Feedback.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     */
    List<Feedback> selectByExample(FeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Feedback selectByPrimaryKeySelective(@Param("id") Integer id, @Param("selective") Feedback.Column ... selective);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     */
    Feedback selectByPrimaryKey(Integer id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    Feedback selectByPrimaryKeyWithLogicalDelete(@Param("id") Integer id, @Param("andLogicalDeleted") boolean andLogicalDeleted);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     */
    int updateByExampleSelective(@Param("record") Feedback record, @Param("example") FeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     */
    int updateByExample(@Param("record") Feedback record, @Param("example") FeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     */
    int updateByPrimaryKeySelective(Feedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     */
    int updateByPrimaryKey(Feedback record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByExample(@Param("example") FeedbackExample example);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table feedback
     *
     * @mbg.generated
     * @project https://github.com/itfsw/mybatis-generator-plugin
     */
    int logicalDeleteByPrimaryKey(Integer id);
}