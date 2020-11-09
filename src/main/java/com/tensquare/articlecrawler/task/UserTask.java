package com.tensquare.articlecrawler.task;

import com.tensquare.articlecrawler.pipeline.UserDbPipeline;
import com.tensquare.articlecrawler.processor.UserProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.Spider;
import us.codecraft.webmagic.pipeline.ConsolePipeline;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * 用户抓取的任务调度类
 *
 * 抓取的用户会重复，可手动做逻辑判断，如将昵称放到redis中，判断昵称是否存在。
 * 文件名最好重新随机生成。
 */
@Component
public class UserTask {

    //注入ArticleProcessor
    @Autowired
    private UserProcessor userProcessor;
    //注入文章pipeline
    @Autowired
    private UserDbPipeline userDbPipeline;
    //注入RedisScheduler
    @Autowired
    private RedisScheduler redisScheduler;

    /**
     * 抓取AI频道的文章数据
     */
//    @Scheduled(cron = "0 0 0 * * ?")//每天0点0分0秒执行一次
/*    @Scheduled(
            //首次执行等待5秒
            initialDelay = 5000
            //任务执行完成后等待的时间,这里1小时
            ,fixedDelay = 1000*60*60
    )*/
    public void userTask(){
        System.out.println("开始爬取用户信息......");

        //蜘蛛
        Spider.create(userProcessor)
                //添加种子url
                .addUrl("https://blog.csdn.net")
                //添加输出管道到控制台
                .addPipeline(new ConsolePipeline())
                //添加输出管道到数据库
                .addPipeline(userDbPipeline)
                //设置Scheduler
                .setScheduler(redisScheduler)
                //异步执行，效率更高
                .start();
    }



}
