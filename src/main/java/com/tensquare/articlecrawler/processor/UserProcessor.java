package com.tensquare.articlecrawler.processor;

import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 文章爬取处理类
 */
@Component
public class UserProcessor implements PageProcessor {
    @Override
    public void process(Page page) {
        //目标地址
        page.addTargetRequests(page.getHtml().links()
                .regex("https://blog.csdn.net/[a-z 0-9 - _]+/article/details/[0-9]{8}")
                .all());
        //设置xpath
        //昵称
        String nickname= page.getHtml().xpath("//*[@id=\"uid\"]/text()").toString();
        //图片地址（css选择器）
        String image= page.getHtml().xpath("//*[@id=\"asideProfile\"]/div[1]/div[1]/a").css("img","src").toString();

        //打印获取页面需要的内容
        System.out.println("昵称："+nickname);
        System.out.println("头像："+image);
        //判断，如果有昵称和头像
        if(!StringUtils.isEmpty(nickname)&&!StringUtils.isEmpty(image)){
            //必须同时有昵称和头像
            page.putField("nickname",nickname);
            page.putField("image",image);
        }else{
            //否则，跳过采集
            page.setSkip(true);
        }
    }

    @Override
    public Site getSite() {
        return Site.me().setSleepTime(3000).setRetryTimes(3);
    }
}
