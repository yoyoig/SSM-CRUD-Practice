package com.hand.web;

import com.github.pagehelper.PageInfo;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MockMvcBuilder;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import static org.junit.Assert.*;

/**
 * Created by 铭刻 on 2017/8/21.
 */
@RunWith(SpringJUnit4ClassRunner.class)
//用于获取springmvc ioc
@WebAppConfiguration
@ContextConfiguration(locations = "classpath:spring/applicationContext.xml")
public class EmployeeControllerTest {

//    传入springmvc 的IOC
    @Autowired
    WebApplicationContext context;

//    虚拟mvc请求，获得处理结果
    MockMvc mockMvc;

//    初始化MockMvc
    @Before
    public void initMockMvc(){
        mockMvc = MockMvcBuilders.webAppContextSetup(context).build();
    }


    //请求测试
    @Test
    public void getEmployees() throws Exception {
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.get("/employees").param("pn","1").param("size","5")).andReturn();
        MockHttpServletResponse response= result.getResponse();
        response.getWriter();
//        System.out.println(info.getList());


    }

}