# SpringBoot data mybatis专题
# 一.注解方式入门例子
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
*注解方式实现的Mapper接口中,如何实现全局配置中的开启驼峰等配置?
方案:参考类MybatisAutoConfiguration中代码,可以往容器中增加自定义配置类
@Configuration
public class MybatisConfig {
    @Bean
    public ConfigurationCustomizer configurationCustomizer() {
        return (configuration) -> configuration.setMapUnderscoreToCamelCase(true);
    }
}



*注解方式的Mapper接口,都要加@Mapper注解,可以都不加,直接在SpringBoot启动类中增加@MapperScan?
方案:
使用注解@MapperScan(value = "com.sky.mapper")批量扫描实现往容器中增加Mapper
```


一.配置方式入门例子

1.配置类的Mapper代码,跟注解类一样:</br>
```
//@Mapper
public interface UserMapper {
    User selectUser(Integer id);
}

```
2.配置文件增加全局和mapper.xml文件的配置:</br>
```
spring:
  datasource:
    username: root
    password: Windows8
    url: jdbc:mysql://47.94.192.199:3306/skydb?autoReconnect=true&useUnicode=true&characterEncoding=utf-8&allowMultiQueries=true&zeroDateTimeBehavior=convertToNull&useSSL=false
    driver-class-name: com.mysql.cj.jdbc.Driver
mybatis:
  mapper-locations: classpath:mybatis/mapper/*.xml
  config-location: classpath:mybatis/mybatis-config.xml
```
3.配置方式配置全局和映射文件:</br>
```
按照上方路径创建xml文件,*.xml存放业务类映射文件,mybatis-config.xml存放全局配置文件
```


4.测试代码:
```
@RestController
public class DemoController {

    @Resource
    private GroupMapper groupMapper;
    @Resource
    private UserMapper userMapper;

    @RequestMapping("/getGroup/{id}")
    public Object getGroup(@PathVariable("id") Integer id) {
        return groupMapper.getGroupById(id);
    }

    @RequestMapping("/createGroup")
    public Object getGroup(Group group) {
        groupMapper.createGroup(group);
        return group;
    }

    @RequestMapping("/getUser/{id}")
    public Object getUser(@PathVariable("id") Integer id) {
        return userMapper.selectUser(id);
    }
}

```
5.结论及问题方案:
```
配置方式和注解方式的Mapper.java类都是一样的,可以加注解让容器启动后创建出来,也可以通过MapperScan来实现注入到容器中.

xml文件也是可以配置全局和业务映射文件的.


```
