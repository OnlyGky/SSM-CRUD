package crud_02.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import crud_02.bean.Employee;
import crud_02.bean.Msg;
import crud_02.service.EmployeeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class EmplloyerController {
    @Autowired
    EmployeeService employeeService;

    /**
     * 单个批量二合一
     * 批量删除：1-2-3
     * 单个删除：1
     *
     * @param
     * @return
     */

    @ResponseBody
    @RequestMapping(value="/emp/{ids}",method=RequestMethod.DELETE)
    public Msg deleteEmp(@PathVariable("ids")String ids){
        //批量删除
        if(ids.contains("-")){
            List<Integer> del_ids = new ArrayList<>();
            String[] str_ids = ids.split("-");
            //组装id的集合
            for (String string : str_ids) {
                del_ids.add(Integer.parseInt(string));
            }
            employeeService.deleteBatch(del_ids);
        }else{
            Integer id = Integer.parseInt(ids);
            employeeService.deleteEmp(id);
        }
        return Msg.success();
    }


    //如果直接发送ajax=PUT的请求
    //请求体中有数据但是employee对象封装不上
    /*
    * 原因：Tomcat：1将请求体中的数据封装为一个map
    * 2 request.getParamter("empName")就会从这个map中取值
    * 3SpringMVC封装POJO对象的时候会把POJO每个属性的值request.getParamter("empName")
    * AJAX发送put请求request.getParamter("empName")拿不到
    * TOMCAT一看是PUT就不会封装请求体中的数据为Map，只有POST形式才会封装
    * */
    @RequestMapping(value = "/emp/{empId}",method = RequestMethod.PUT)
    @ResponseBody
    public Msg saveEmp(Employee employee){
        employeeService.updateEmp(employee);
        return Msg.success();
    }


    @RequestMapping(value = "/emp/{id}",method = RequestMethod.GET)
    @ResponseBody
    public Msg getEmp(@PathVariable("id") Integer id){
        Employee employee =employeeService.getEmp(id);
        return Msg.success().add("emp",employee);
    }

    @ResponseBody
    @RequestMapping("/checkuser")
    public Msg checkuse(@RequestParam("empName") String empName){
        boolean b=employeeService.checkUser(empName);
if(b){
    return Msg.success();
}
else{
    return Msg.fail();
}
    }

@RequestMapping(value = "/emp",method = RequestMethod.POST)
@ResponseBody
//@Valid代表进行JSR303校验
    public Msg saveEmp(@Valid Employee emp, BindingResult result){
        Map<String,Object> map=new HashMap<>();
        if(result.hasErrors()){
           List<FieldError> errors= result.getFieldErrors();
        for(FieldError fieldError:errors){
            map.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
         return Msg.fail().add("errorFileds",map);
        }else{
    employeeService.saveEmp(emp);
     return Msg.success();
        }
    }

        @RequestMapping("/emps")
    //@ResponseBody将返回的Pageinfo转化为json字符串
    @ResponseBody
    public Msg getEmpWithJson(@RequestParam(value = "pn", defaultValue = "1")Integer pn){
        PageHelper.startPage(pn,5);
        //startPage后面紧跟着查询就是分页查询
        List<Employee> emps=employeeService.getAll();
        //使用pageInfo包装查询后的结果 封装了详细的分页信息，包括查询得到的数据
        //连续显示五页
        PageInfo page=new PageInfo(emps,5);
        return Msg.success().add("pageInfo",page);
    }
//    查询所有员工
//    @RequestMapping("/emps")
    public String getEmps(@RequestParam(value = "pn", defaultValue = "1")Integer pn,
    Model model){
        PageHelper.startPage(pn,5);
        //startPage后面紧跟着查询就是分页查询
        List<Employee> emps=employeeService.getAll();
        //使用pageInfo包装查询后的结果 封装了详细的分页信息，包括查询得到的数据
        //连续显示五页
        PageInfo page=new PageInfo(emps,5);
        //将数据存放在数据域中，便于被前端页面取到
        model.addAttribute("pageInfo",page);
        return "/list";
    }
}
