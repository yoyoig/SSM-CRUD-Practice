package com.hand.service;

import com.github.pagehelper.PageInfo;
import com.hand.pojo.Employee;

import java.util.List;

/**
 * Created by 铭刻 on 2017/8/19.
 */
public interface EmployeeService {

    PageInfo getEmployees(int pn, int size);

    Employee getEmployeeById(int id);

    int insertEmployee(Employee employee);

    Boolean checkEmpName(String empName);

    int editEmployee(Employee employee);

    int dltOneEmployee(int id);

    int dltEmployees(String empId);

    PageInfo getEmployeesByCondition(int pn, int size);
}
