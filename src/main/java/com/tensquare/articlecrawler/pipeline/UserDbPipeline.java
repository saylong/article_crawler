package com.tensquare.articlecrawler.pipeline;

import com.tensquare.articlecrawler.dao.User;
import com.tensquare.articlecrawler.mapper.UserRepository;
import com.tensquare.articlecrawler.utils.DownloadUtil;
import com.tensquare.articlecrawler.utils.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import us.codecraft.webmagic.ResultItems;
import us.codecraft.webmagic.Task;
import us.codecraft.webmagic.pipeline.Pipeline;

import java.io.IOException;

/**
 * 入库类，用于持久化数据到目标位置
 */
@Component
public class UserDbPipeline implements Pipeline {
    //注入dao
    @Autowired
    private UserRepository userRepository;
    //注入id生成器
    @Autowired
    private IdWorker idWorker;

    //注入图片文件路径
    @Value("${file.savepath}")
    private String savePath;

    //频道ID
    private String channelId;
    public void setChannelId(String channelId) {
        this.channelId = channelId;
    }

    @Override
    public void process(ResultItems resultItems, Task task) {
        String nickname = resultItems.get("nickname");
        String image= resultItems.get("image");
//        System.out.println("nickname:"+nickname+",image:"+image);
        //--------数据库的保存
        //创建用户对象
        User user=new User();
        //id
        user.setId(idWorker.nextId()+"");
        //昵称
        user.setNickname(nickname);
        //得到url的文件名的部分
        String avatar=image.substring(image.lastIndexOf("/")+1);
        //头像
        user.setAvatar(avatar);

        //数据库保存
        userRepository.insert(user);

        //--------图片文件的保存
        try {
            //下载图片到本地
            DownloadUtil.download(image,avatar,savePath);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
