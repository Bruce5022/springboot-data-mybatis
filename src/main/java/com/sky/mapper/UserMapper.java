package com.sky.mapper;

import com.sky.model.User;
import org.apache.ibatis.annotations.MapKey;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UserMapper {


    // 1:User对象的结构
    @MapKey("id")
    Map<Integer, User> searchUserCodeLikeWithMap(String code);

    List<User> searchUsers();

    User selectUser(Integer id);

    User selectUserByPhone(@Param("id") Integer id, String phone);

    User selectUserByMap(Map<String, Object> map);

    List<User> searchUserCodeLike(String code);

    Map selectUserWithMap(Integer id);
}
