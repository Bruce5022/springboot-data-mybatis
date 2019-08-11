# SpringBoot data mybatis专题
# 一.入门例子
1.引入jar包:</br>
```
<dependency>
    <groupId>org.mybatis.spring.boot</groupId>
    <artifactId>mybatis-spring-boot-starter</artifactId>
    <version>2.1.0</version>
</dependency>
```
2.注解版Mapper代码:</br>
```
@Mapper
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
```
3.配置文件:</br>
```
spring:
  datasource:
    username: root
    password: Windows8
    url: jdbc:mysql://47.94.192.199:3306/skydb
    driver-class-name: com.mysql.cj.jdbc.Driver
```
4.测试代码:
```
@RestController
public class DemoController {

    @Resource
    private GroupMapper groupMapper;

    @RequestMapping("/getGroup/{id}")
    public Object getGroup(@PathVariable("id") Integer id) {
        return groupMapper.getGroupById(id);
    }

    @RequestMapping("/createGroup")
    public Object getGroup(Group group) {
        groupMapper.createGroup(group);
        return group;
    }
}

```
5.结论及问题方案:
```
注解方式实现的Mapper接口中,如何实现全局配置中的开启驼峰等配置?
方案:参考类MybatisAutoConfiguration中代码,可以往容器中增加自定义配置类
@Configuration
public class MybatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return (configuration) -> configuration.setMapUnderscoreToCamelCase(true);
    }
}
注解方式的Mapper接口,都要加@Mapper注解,可以都不加,直接在SpringBoot启动类中增加@MapperScan?
方案:
使用注解@MapperScan(value = "com.sky.mapper")批量扫描实现往容器中增加Mapper
```
