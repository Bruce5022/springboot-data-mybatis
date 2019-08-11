package com.sky.mapper;

import com.sky.model.Group;
import com.sky.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

//@Mapper
public interface GroupMapper {
    @Select("select id,group_code groupCode,group_name groupName from sys_group where id = #{id}")
    Group getGroupById(Integer id);

    @Delete("delete from sys_group where id = #{id}")
    int deleteById(Integer id);

    @Update("update sys_group set _version=#{version} where id = #{id}")
    Group updateGroupVersion(Integer id, Integer version);

    // 解决新增的对象中,自增产生的id为空的问题,下面注解会把新的id给放置到对象中
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into sys_group(group_code,group_name) values(#{groupCode},#{groupName})")
    int createGroup(Group group);
}
