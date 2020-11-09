package com.tensquare.articlecrawler.dao;

import tk.mybatis.mapper.annotation.KeySql;

import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;

/**
 * @author lsl
 * @date 2020/11/9
 */
@Table(name = "tb_article")
public class Article {
    @Id
    @KeySql(useGeneratedKeys = true)
    private String id;

    private Object title;
    private Object content;
    private Date createtime;

    public void setId(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setTitle(Object title) {
        this.title = title;
    }

    public Object getTitle() {
        return title;
    }

    public void setContent(Object content) {
        this.content = content;
    }

    public Object getContent() {
        return content;
    }

    public void setCreatetime(Date createtime) {
        this.createtime = createtime;
    }

    public Date getCreatetime() {
        return createtime;
    }
}
