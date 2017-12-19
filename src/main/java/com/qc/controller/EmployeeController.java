/**
 * 
 */
package com.qc.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.qc.bean.Employee;
import com.qc.bean.Msg;
import com.qc.service.EmployeeService;

/**
 * @author qinc
 * @date 2017年12月1日 下午2:12:42
 */
@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	/**
	 * 根据id删除单个员工
	 * @param id
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value="/deleteById/{id}",method=RequestMethod.GET)
	public Msg deleteById(@PathVariable("id") Integer id){
		System.out.println(id);
		employeeService.deleteEmpById(id);
		return Msg.success();
	}
	/*
	 * ResponseBody工作需要Jackson包
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		// 这不是一个分页查询
		// 引入pageHelper插件,查询之前传入页码和每页显示的记录数
		PageHelper.startPage(pn, 5);
		// startPage后面紧跟的查询就是分页查询
		List<Employee> emps = employeeService.getAll();
		// 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了，里面封装了详细的分页信息，包括查询出来的数据
		PageInfo page = new PageInfo(emps, 5);
		return Msg.success().add("pageInfo", page);
	}

	/*
	 * JSR303后端校验
	 * 员工保存方法
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	@ResponseBody
	public Msg getEmps(@Valid Employee employee,BindingResult result) {
		if(result.hasErrors()){
			Map<String, Object> map =  new HashMap<>();
			List<FieldError> errors=result.getFieldErrors();
			for(FieldError fieldError:errors){
				System.out.println("错误的字段名："+fieldError.getField());
				System.out.println("错误信息："+fieldError.getDefaultMessage());
				map.put(fieldError.getField(),fieldError.getDefaultMessage());
			}
			return Msg.fail().add("errorFields", map);
		}else{
			employeeService.empSave(employee);
			return Msg.success();
		}
	}

	// @RequestMapping("/emps")
	public String getEmps(@RequestParam(value = "pn", defaultValue = "1") Integer pn, Model model) {
		// 这不是一个分页查询
		// 引入pageHelper插件,查询之前传入页码和每页显示的记录数
		PageHelper.startPage(pn, 5);
		// startPage后面紧跟的查询就是分页查询
		List<Employee> emps = employeeService.getAll();
		// 使用pageInfo包装查询后的结果，只需要将pageInfo交给页面就行了，里面封装了详细的分页信息，包括查询出来的数据
		PageInfo page = new PageInfo(emps, 5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
	/*
	 * 检查用户名是否可用
	 * */
	@ResponseBody
	@RequestMapping("/checkUser")
	public Msg checkuser(@RequestParam("empName") String empName) {
		//先判断用户名是不是合法的表达式
		String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regx)){
			return Msg.fail().add("va_msg", "用户名必须是6-16位数字和字母组合或者2-5位中文");
		}
		//数据库用户名重复校验
		boolean bool = employeeService.checkuser(empName);
		if (bool) {
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "用户名不可用");
		}
	}
	/*
	 * 点击编辑按钮根据id查询相关用户信息并把信息返回到前台页面
	 * 
	 * */
	@RequestMapping(value="/empUpdate/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg queryEmp(@PathVariable("id") Integer id){
		Employee emp=employeeService.findById(id);
		return Msg.success().add("emp_info", emp);
	}
	
	/*
	 * 员工更新
	 * */
	@ResponseBody
	@RequestMapping(value="/empUpdateAndSave/{empId}",method=RequestMethod.PUT)
	public Msg saveEmp(Employee employee){
		employeeService.updateEmployee(employee);
		return Msg.success();
	}
}
