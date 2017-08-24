package com.hand.web;

import com.github.pagehelper.PageInfo;
import com.hand.dto.Msg;
import com.hand.pojo.Employee;
import com.hand.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.validation.Valid;
import javax.validation.constraints.Size;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 铭刻 on 2017/8/21.
 */
@Controller
public class EmployeeController {

    @Autowired
    private EmployeeService service;

    @RequestMapping(value = "/employees",method = RequestMethod.GET)
    @ResponseBody
    public PageInfo<Employee> getEmployees(int pn, int size, Model model){

        return service.getEmployees(pn,size);

    }

    @RequestMapping(value = "/employee/{empId}",method = RequestMethod.GET)
    @ResponseBody
    public Employee getEmployeeById(@PathVariable("empId")Integer empId){
        return service.getEmployeeById(empId);
    }



    @RequestMapping(value = "/checkEmpName",method = RequestMethod.GET)
    @ResponseBody
    public Boolean checkEmpName(String empName){

        boolean check = service.checkEmpName(empName);
        return check;

    }


    @RequestMapping(value = "/employee",method = RequestMethod.POST)
    @ResponseBody
    public Msg addEmployee(@Valid Employee employee, BindingResult result) {
        //错误存放在map中
        Map<String,Object> map = new HashMap<String,Object>();
        if (result.hasErrors()) {

            List<FieldError> list = result.getFieldErrors();
            for (FieldError error: list) {
                map.put(error.getField(),error.getDefaultMessage());
            }
            //将错误放入到msg中，将msg返回给页面
            return Msg.fail().add("errorFields",map);
        } else {

            service.insertEmployee(employee);
            return Msg.success();
        }


    }

    @RequestMapping(value = "/employee/{empId}",method = RequestMethod.PUT)
    @ResponseBody
    public Msg editEmployee(@Valid Employee employee,BindingResult result,@PathVariable int empId){

        if (result.hasErrors()){
            Map map = new HashMap<String,Object>();
            List<FieldError> list = result.getFieldErrors();
            for (FieldError error:list) {
                map.put(error.getField(),error.getDefaultMessage());
            }
            return Msg.fail().add("fieldErrors",map);
        }else {
            employee.setEmpId(empId);
            System.out.println(employee);
            service.editEmployee(employee);
            return Msg.success();
        }


    }


    @RequestMapping(value = "/employee/{empId}",method = RequestMethod.DELETE)
    @ResponseBody
    public Msg dltOneEmployee(@PathVariable String empId){

        if(empId.contains("-")){

            int result = service.dltEmployees(empId);

            if (result == 0) {

                return Msg.fail();

            } else {

                return Msg.success();
            }

        }else {
            int result = service.dltOneEmployee(Integer.parseInt(empId));

            if (result != 1) {

                return Msg.fail();

            } else {

                return Msg.success();
            }
        }
    }
}
