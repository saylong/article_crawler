package com.tensquare.articlecrawler;

import com.tensquare.articlecrawler.utils.IdWorker;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;
import tk.mybatis.spring.annotation.MapperScan;
import us.codecraft.webmagic.scheduler.RedisScheduler;

/**
 * 文章爬虫微服务启动类
 */
@SpringBootApplication
//开启定时任务注解功能
@EnableScheduling
@MapperScan("com.tensquare.articlecrawler.mapper")
public class ArticleCrawlerApplication {
    //入口方法
    public static void main(String[] args) {
        SpringApplication.run(ArticleCrawlerApplication.class, args);
    }

    /**
     * id生成器
     * @return
     */
    @Bean
    public IdWorker idWorker(){
        return new IdWorker(1, 1);
    }
    //注入Redis主机地址
    @Value("${redis.host}")
    private String redis_host;
    
    //定义RedisScheduler的Bean
    @Bean
    public RedisScheduler redisScheduler(){
        return new RedisScheduler(redis_host);
    }
}
