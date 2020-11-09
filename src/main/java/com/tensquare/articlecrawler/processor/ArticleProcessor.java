package com.tensquare.articlecrawler.processor;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Site;
import us.codecraft.webmagic.processor.PageProcessor;

/**
 * 文章的页面处理期
 */
@Component
public class ArticleProcessor implements PageProcessor {
    @Override
    //页面处理规则等
    public void process(Page page) {

        //1.要抓取的url
        page.addTargetRequests(page.getHtml().links()
                 .regex("https://blog.csdn.net/[a-z A-Z]+[a-z A-Z 0-9 - _]*/article/details/[0-9]{8,9}")
                .all());

        //2.当前页面要提取的内容
        //标题
        String title=page.getHtml().xpath("//*[@id=\"mainBox\"]/main/div[1]/div/div/div[1]/h1/text()").toString();
        //内容
        String content=page.getHtml().xpath("//*[@id=\"content_views\"]").toString();
        //[扩展]如果抓不到内容，说明规则不正确，则继续提取
        if(StringUtils.isEmpty(content)){
            content=page.getHtml().xpath("//*[@id=\"content_views\"]").toString();
        }

        //准备放到ResultItems中
        //过滤掉没用的数据
        if(!StringUtils.isEmpty(title)&&!StringUtils.isEmpty(content)){
            page.putField("title",title);
            page.putField("content",content);
        }else{
            //如果数据是废数据（比如是空的），直接跳过，不考虑输出了。
            page.setSkip(true);
        }

    }

    @Override
    public Site getSite() {
        return Site.me()
                .setSleepTime(1000)
                .setRetryTimes(3)
                .setTimeOut(3000);
    }
}
