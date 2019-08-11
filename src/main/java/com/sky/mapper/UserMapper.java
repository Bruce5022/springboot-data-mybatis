package com.sky.mapper;

import com.sky.model.User;
import org.apache.ibatis.annotations.Mapper;

//@Mapper
public interface UserMapper {
    User selectUser(Integer id);
}
