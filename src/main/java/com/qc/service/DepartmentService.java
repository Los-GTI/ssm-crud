/**
 * 
 */
package com.qc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qc.bean.Department;
import com.qc.dao.DepartmentMapper;

/**
 * @author qinc
 * @date 2017年12月3日 下午8:32:54
 */
@Service
public class DepartmentService {

	@Autowired
	DepartmentMapper departmentMapper;
	/**
	 * 查询出部门信息
	 * @return
	 */
	public List<Department> getDepts() {
		// TODO Auto-generated method stub
		List<Department> list=departmentMapper.selectByExample(null);
		return list;
	}
	
		

}
