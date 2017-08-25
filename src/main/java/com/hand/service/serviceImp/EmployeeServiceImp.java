package com.hand.service.serviceImp;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.hand.dao.EmployeeMapper;
import com.hand.pojo.Employee;
import com.hand.pojo.EmployeeExample;
import com.hand.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 铭刻 on 2017/8/21.
 */

@Service
public class EmployeeServiceImp implements EmployeeService {

    @Autowired
    private EmployeeMapper mapper;

    @Override
    public PageInfo getEmployees(int pn, int size) {


        PageHelper.startPage(pn,size);
        List<Employee> employeeList = mapper.selectByExampleWithDepartment(null);
        PageInfo<Employee> pageInfo = new PageInfo<Employee>(employeeList,5);
        //优化，当当前页码大于查询的总页数时，让当前页码等于最后一页
        if(pn>pageInfo.getPages()){
            pn=pageInfo.getPages();
            PageHelper.startPage(pn,size);
            List<Employee> employeeList2 = mapper.selectByExampleWithDepartment(null);
            PageInfo<Employee> pageInfo2 = new PageInfo<Employee>(employeeList2,5);
            return pageInfo2;
        }else {
            return pageInfo;
        }

    }

    @Override
    public Employee getEmployeeById(int id) {
        return mapper.selectByPrimaryKey(id);
    }

    @Override
    public int insertEmployee(Employee employee) {
        return mapper.insert(employee);
    }

    @Override
    public Boolean checkEmpName(String empName) {
        EmployeeExample example = new EmployeeExample();
        example.createCriteria().andEmpNameEqualTo(empName);
        List list = mapper.selectByExample(example);
        if(list.size()>=1){
            return false;
        }else {
            return true;
        }
    }

    @Override
    public int editEmployee(Employee employee) {
        return mapper.updateByPrimaryKey(employee);
    }

    @Override
    public int dltOneEmployee(int id) {
        return mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int dltEmployees(String empId) {
        List<Integer> ids = new ArrayList<Integer>();
        String [] empIds = empId.split("-");
        for (String id:empIds) {
            ids.add(Integer.parseInt(id));
        }
        EmployeeExample example = new EmployeeExample();
        example.createCriteria().andEmpIdIn(ids);
        return mapper.deleteByExample(example);
    }
}
