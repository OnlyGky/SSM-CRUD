package crud_02.test;

import com.github.pagehelper.PageInfo;
import crud_02.bean.Employee;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

//使用Spring测试模块提供的功能来测试
@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration(locations={"classpath:WEB-INF/applicationContext.xml", "classpath:WEB-INF/springMVC.xml"})
public class MvcTest {
    //传入Springmvc的ioc
    @Autowired
    WebApplicationContext context;
    //虚拟mvc请求 获取处理结果
    MockMvc mockMvc;
    @Before
    public void initMokcMvn(){
        mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
    }
    @Test
    public void testPage() throws Exception {
//        模拟请求拿到返回值
      MvcResult mvcResult= mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn","1")).andReturn();
//        请求成功后，请求域中会有pageInfo:我们可以取出pageInfo进行验证
        MockHttpServletRequest request=mvcResult.getRequest();
        PageInfo pi=(PageInfo)request.getAttribute("pageInfo");
        System.out.println(pi.getPageNum());
        List<Employee>list=pi.getList();
        for(Employee employee:list)
            System.out.println(employee.getClass());
    }
}
