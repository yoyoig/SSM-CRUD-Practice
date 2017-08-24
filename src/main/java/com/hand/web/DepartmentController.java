package com.hand.web;

import com.hand.dto.Msg;
import com.hand.pojo.Department;
import com.hand.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * Created by 铭刻 on 2017/8/21.
 */
@Controller
public class DepartmentController {


    @Autowired
    private DepartmentService service;

    @RequestMapping(value = "/department",method = RequestMethod.GET)
    @ResponseBody
    public Msg queryAllDept(){

        return Msg.success().add("depts",service.getDeptAll());
    }

}
