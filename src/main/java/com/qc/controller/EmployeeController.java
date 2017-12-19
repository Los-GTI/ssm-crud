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
 * @date 2017��12��1�� ����2:12:42
 */
@Controller
public class EmployeeController {

	@Autowired
	EmployeeService employeeService;

	/**
	 * ����idɾ������Ա��
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
	 * ResponseBody������ҪJackson��
	 */
	@RequestMapping("/emps")
	@ResponseBody
	public Msg getEmpsWithJson(@RequestParam(value = "pn", defaultValue = "1") Integer pn) {
		// �ⲻ��һ����ҳ��ѯ
		// ����pageHelper���,��ѯ֮ǰ����ҳ���ÿҳ��ʾ�ļ�¼��
		PageHelper.startPage(pn, 5);
		// startPage��������Ĳ�ѯ���Ƿ�ҳ��ѯ
		List<Employee> emps = employeeService.getAll();
		// ʹ��pageInfo��װ��ѯ��Ľ����ֻ��Ҫ��pageInfo����ҳ������ˣ������װ����ϸ�ķ�ҳ��Ϣ��������ѯ����������
		PageInfo page = new PageInfo(emps, 5);
		return Msg.success().add("pageInfo", page);
	}

	/*
	 * JSR303���У��
	 * Ա�����淽��
	 */
	@RequestMapping(value = "/emp", method = RequestMethod.POST)
	@ResponseBody
	public Msg getEmps(@Valid Employee employee,BindingResult result) {
		if(result.hasErrors()){
			Map<String, Object> map =  new HashMap<>();
			List<FieldError> errors=result.getFieldErrors();
			for(FieldError fieldError:errors){
				System.out.println("������ֶ�����"+fieldError.getField());
				System.out.println("������Ϣ��"+fieldError.getDefaultMessage());
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
		// �ⲻ��һ����ҳ��ѯ
		// ����pageHelper���,��ѯ֮ǰ����ҳ���ÿҳ��ʾ�ļ�¼��
		PageHelper.startPage(pn, 5);
		// startPage��������Ĳ�ѯ���Ƿ�ҳ��ѯ
		List<Employee> emps = employeeService.getAll();
		// ʹ��pageInfo��װ��ѯ��Ľ����ֻ��Ҫ��pageInfo����ҳ������ˣ������װ����ϸ�ķ�ҳ��Ϣ��������ѯ����������
		PageInfo page = new PageInfo(emps, 5);
		model.addAttribute("pageInfo", page);
		return "list";
	}
	/*
	 * ����û����Ƿ����
	 * */
	@ResponseBody
	@RequestMapping("/checkUser")
	public Msg checkuser(@RequestParam("empName") String empName) {
		//���ж��û����ǲ��ǺϷ��ı��ʽ
		String regx="(^[a-zA-Z0-9_-]{6,16}$)|(^[\u2E80-\u9FFF]{2,5})";
		if(!empName.matches(regx)){
			return Msg.fail().add("va_msg", "�û���������6-16λ���ֺ���ĸ��ϻ���2-5λ����");
		}
		//���ݿ��û����ظ�У��
		boolean bool = employeeService.checkuser(empName);
		if (bool) {
			return Msg.success();
		} else {
			return Msg.fail().add("va_msg", "�û���������");
		}
	}
	/*
	 * ����༭��ť����id��ѯ����û���Ϣ������Ϣ���ص�ǰ̨ҳ��
	 * 
	 * */
	@RequestMapping(value="/empUpdate/{id}",method=RequestMethod.GET)
	@ResponseBody
	public Msg queryEmp(@PathVariable("id") Integer id){
		Employee emp=employeeService.findById(id);
		return Msg.success().add("emp_info", emp);
	}
	
	/*
	 * Ա������
	 * */
	@ResponseBody
	@RequestMapping(value="/empUpdateAndSave/{empId}",method=RequestMethod.PUT)
	public Msg saveEmp(Employee employee){
		employeeService.updateEmployee(employee);
		return Msg.success();
	}
}
