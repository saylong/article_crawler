package com.tensquare.articlecrawler.pipeline;

import com.tensquare.articlecrawler.dao.Article;
import com.tensquare.articlecrawler.mapper.ArticleRepository;
import com.tensquare.articlecrawler.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.util.Date;

/**
 * 自定义管道输出
 */
@Component
public class ArticlePipeline implements Pipeline {

    //注入id生成器
    @Autowired
    private IdWorker idWorker;
    //注入dao
    @Autowired
    private ArticleRepository articleRepository;

    @Override
    public void process(ResultItems resultItems, Task task) {
        //目标：要向mysql中写数据
        //文章对象
        Article article=new Article();
        //id
        article.setId(idWorker.nextId()+"");
        //标题
        article.setTitle(resultItems.get("title"));
        //内容
        article.setContent(resultItems.get("content"));
        //其他，如日期等..
        article.setCreatetime(new Date());

        //保存操作
        articleRepository.insert(article);
    }
}
