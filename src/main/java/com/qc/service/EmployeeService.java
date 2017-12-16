/**
 * 
 */
package com.qc.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.qc.bean.Department;
import com.qc.bean.Employee;
import com.qc.bean.EmployeeExample;
import com.qc.bean.EmployeeExample.Criteria;
import com.qc.dao.EmployeeMapper;

/**
 * @author qinc
 * @date 2017��12��1�� ����2:20:34
 */
@Service
public class EmployeeService {
	
	@Autowired
	EmployeeMapper employeeMapper;

	/**
	 * ��ѯ����Ա����Ϣ
	 * @return
	 */
	public List<Employee> getAll() {
		// TODO Auto-generated method stub
		return employeeMapper.selectByExampleWithDept(null);
	}

	/**
	 * @param employee
	 */
	public void empSave(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.insertSelective(employee);
	}

	/**
	 * �����û����Ƿ����
	 * @param empName
	 * @return true:�������
	 */
	public boolean checkuser(String empName) {
		// TODO Auto-generated method stub
		EmployeeExample example=new EmployeeExample();
		Criteria criteria=example.createCriteria();
		criteria.andEmpNameEqualTo(empName);
		long count=employeeMapper.countByExample(example);
		return count==0;
	}

	/**
	 * @param empId
	 * @return
	 */
	public Employee findById(int id) {
		// TODO Auto-generated method stub
		return employeeMapper.selectByPrimaryKeyWithDept(id);
	}

	/**
	 * @param employee
	 */
	public void updateEmployee(Employee employee) {
		// TODO Auto-generated method stub
		employeeMapper.updateByPrimaryKeySelective(employee);
	}
	
	
}
