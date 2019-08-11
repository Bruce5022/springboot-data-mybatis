package com.sky.controller;

import com.sky.mapper.GroupMapper;
import com.sky.model.Group;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

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
