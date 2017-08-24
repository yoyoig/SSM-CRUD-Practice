package com.hand.service.serviceImp;

import com.hand.dao.DepartmentMapper;
import com.hand.pojo.Department;
import com.hand.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by 铭刻 on 2017/8/21.
 */
@Service
public class DepartmentImp implements DepartmentService {

    @Autowired
    private DepartmentMapper mapper;

    @Override
    public List<Department> getDeptAll() {
        return mapper.selectByExample(null);
    }
}
