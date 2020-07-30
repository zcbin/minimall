package com.zcb.minimallsearch.domain;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;

@Document(indexName = "minimall_keyword", type = "minmall_keyword", shards = 1, replicas = 0)
public class Keyword {
    public Keyword() {
    }

    @Id
    private Integer id;

    @Field(analyzer = "ik_max_word", type = FieldType.Text)
    private String keyword;

    @Field(type = FieldType.Keyword)
    private String url;

    @Field(type = FieldType.Boolean)
    private Boolean isHot;

    @Field(type = FieldType.Boolean)
    private Boolean isDefault;

    @Field(type = FieldType.Integer)
    private Integer sortOrder;

    @Field(type = FieldType.Long)
    private LocalDateTime addTime;
    @Field(type = FieldType.Long)
    private LocalDateTime updateTime;

    @Field(type = FieldType.Boolean)
    private Boolean deleted;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Boolean getHot() {
        return isHot;
    }

    public void setHot(Boolean hot) {
        isHot = hot;
    }

    public Boolean getDefault() {
        return isDefault;
    }

    public void setDefault(Boolean aDefault) {
        isDefault = aDefault;
    }

    public Integer getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(Integer sortOrder) {
        this.sortOrder = sortOrder;
    }

    public LocalDateTime getAddTime() {
        return addTime;
    }

    public void setAddTime(LocalDateTime addTime) {
        this.addTime = addTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    @Override
    public String toString() {
        return "Keyword{" +
                "id=" + id +
                ", keyword='" + keyword + '\'' +
                ", url='" + url + '\'' +
                ", isHot=" + isHot +
                ", isDefault=" + isDefault +
                ", sortOrder=" + sortOrder +
                ", addTime=" + addTime +
                ", updateTime=" + updateTime +
                ", deleted=" + deleted +
                '}';
    }
}