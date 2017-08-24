package com.hand.dao;

import com.github.pagehelper.PageInfo;
import com.hand.pojo.Employee;
import com.hand.pojo.EmployeeExample;
import com.hand.service.EmployeeService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.List;
import java.util.UUID;

import static org.junit.Assert.*;

/**
 * Created by 铭刻 on 2017/8/19.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class EmployeeMapperTest {
    @Autowired
    private EmployeeMapper mapper;

    @Autowired
    private EmployeeService service;

    @Test
    public void insertSelective() throws Exception {
//        for (int i = 0;i<1000;i++){
//            String name = UUID.randomUUID().toString().substring(0,5);
//            mapper.insertSelective(new Employee(null,name,"M",name+"@email.com",1));
//        }
//        System.out.println("success");

//        //employeeExample 为employee条件对象，
//        EmployeeExample example = new EmployeeExample();
//
//        //可链式对条件对象添加条件。可满足单表的任何操作
//        example.createCriteria().andDIdEqualTo(1);
//
//        //查询时需要将条件对象放入mapper方法中
//        List<Employee> employees = mapper.selectByExample(example);
//
//        System.out.println("---" + employees.size());


//        PageInfo<Employee> list = service.getEmployees(1,5);
//        System.out.println(list.getList().get(2));

        Boolean b = service.checkEmpName("4935ca");
        System.out.println(b);
    }

}