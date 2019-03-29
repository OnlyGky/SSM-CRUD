package crud_02.test;

import crud_02.bean.Employee;
import crud_02.dao.DepartmentMapper;
import crud_02.dao.EmployeeMapper;
import org.apache.ibatis.session.SqlSession;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:WEB-INF/applicationContext.xml"})
public class Test {
    @Autowired
DepartmentMapper departmentMapper;
    @Autowired
    EmployeeMapper employeeMapper;
    @Autowired
    SqlSession sqlSession;
    @org.junit.Test
    public void test(){

//        departmentMapper.insertSelective(new Department(null,"开发部"));
//        departmentMapper.insertSelective(new Department(null,"测试部"));
//        departmentMapper.insertSelective(new Department(null,"开发部"));
//        employeeMapper.insertSelective(new Employee(null,"jerry","M","123@qq.com",1));

//        批量插入
        EmployeeMapper mapper=sqlSession.getMapper(EmployeeMapper.class);
        for(int i=0;i<100;i++){
            String s= UUID.randomUUID().toString().substring(0,5)+i;
            mapper.insertSelective(new Employee(null,s,"M",s+"@qq.com",1));
        }
    }

//        ApplicationContext applicationContext = new FileSystemXmlApplicationContext("src/main/webapp/WEB-INF/applicationContext.xml");
//        System.out.println(1);
//        DepartmentMapper departmentMapper=applicationContext.getBean(DepartmentMapper.class);
//        System.out.println(2);
//        System.out.println(departmentMapper);
//    }


    //Mybatis逆向工程
//    public static void main(String[] args) throws Exception {
//        List<String> warnings = new ArrayList<String>();
//        boolean overwrite = true;
//        File configFile = new File("src/main/mbg.xml");
//        System.out.println(1);
//        ConfigurationParser cp = new ConfigurationParser(warnings);
//        System.out.println(2);
//        Configuration config = cp.parseConfiguration(configFile);
//        System.out.println(3);
//        DefaultShellCallback callback = new DefaultShellCallback(overwrite);
//        System.out.println(4);
//        MyBatisGenerator myBatisGenerator = new MyBatisGenerator(config,
//                callback, warnings);
//        System.out.println(5);
//        myBatisGenerator.generate(null);
//        System.out.println(6);
//    }
}


