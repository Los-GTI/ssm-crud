/**
 * 
 */
package com.qc.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.qc.bean.Department;
import com.qc.bean.Msg;
import com.qc.service.DepartmentService;

/**
 * @author qinc
 * @date 2017年12月3日 下午8:31:39
 */
@Controller
public class DepartmentController {
		
	    @Autowired
		DepartmentService departmentService;
	    
	    @RequestMapping("/deptNameSelect")
	    @ResponseBody
	    public Msg getDepts(){
	    	List<Department> list=departmentService.getDepts();
	    	return Msg.success().add("depts", list);
	    }
}
