package com.hand.dao;

import com.hand.pojo.Department;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.Assert.*;

/**
 * Created by 铭刻 on 2017/8/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class DepartmentMapperTest {

    @Autowired
    private DepartmentMapper mapper;

    @Test
    public void insertSelective() throws Exception {

//        mapper.insertSelective(new Department(1,"测试部"));
//        mapper.insertSelective(new Department(2,"开发部"));
//        mapper.insertSelective(new Department(3,"移动部"));
//        mapper.insertSelective(new Department(4,"运维部"));
//        mapper.insertSelective(new Department(5,"实施部"));

        Department department = mapper.selectByPrimaryKeyWithEmployee(5);
        System.out.println(department);

    }

}