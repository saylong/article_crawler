package com.tensquare.articlecrawler.task;

import com.tensquare.articlecrawler.pipeline.ArticlePipeline;
import com.tensquare.articlecrawler.processor.ArticleProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * 任务执行的类
 */
@Component
public class ArticleTask {

    //注入文章的页面处理对象
    @Autowired
    private ArticleProcessor articleProcessor;
    //注入文章的输出对象
    @Autowired
    private ArticlePipeline articlePipeline;

    //注入redis调度队列
    @Autowired
    private RedisScheduler redisScheduler;

    //目标：每一小时执行一次爬取csdn的播客中的人工智能的文章
/*    @Scheduled(
            //首次执行等待5秒
            initialDelay = 5000
            //任务执行完成后等待的时间,这里1小时
            ,fixedDelay = 1000*60*60
    )*/
    public void aiTask(){
        //创建爬虫
        Spider.create(articleProcessor)
                //添加种子url
                .addUrl("https://blog.csdn.net/nav/ai")
                //添加输出
                .addPipeline(new ConsolePipeline())//控制台输出
                .addPipeline(articlePipeline)//自定义数据库输出
                //调度队列（虑重）
                .setScheduler(redisScheduler)
                //设置抓取的线程数，默认是1，这里设置3个
                .thread(3)
                //多线程的异步运行
                .start();
    }
}
