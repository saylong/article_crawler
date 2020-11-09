package com.tensquare.articlecrawler.mapper;

import com.tensquare.articlecrawler.dao.User;
import org.springframework.stereotype.Repository;
import tk.mybatis.mapper.common.Mapper;

/**
 * @author lsl
 * @date 2020/11/9
 */
@Repository
public interface UserRepository extends Mapper<User> {
}
