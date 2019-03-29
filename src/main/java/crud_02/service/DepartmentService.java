package crud_02.service;

import crud_02.bean.Department;
import crud_02.dao.DepartmentMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    public List getDepts() {

        List<Department>list=departmentMapper.selectByExample(null);
        return list;
    }
}
