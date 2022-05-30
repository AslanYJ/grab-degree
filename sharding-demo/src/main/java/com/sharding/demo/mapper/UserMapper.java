package com.sharding.demo.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.sharding.demo.domain.entity.User;

/**
 * @author yjlan
 * @version V1.0
 * @Description mapper
 * @date 2022.05.30 09:02
 */
@Mapper
public interface UserMapper extends  BaseMapper<User>{

    
    int insertV2(User user);
    
    User selectByUserId(@Param("userId") Long userId);
}
