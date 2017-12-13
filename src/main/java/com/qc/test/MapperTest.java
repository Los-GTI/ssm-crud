/**
 * 
 */
package com.qc.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.qc.dao.DepartmentMapper;

/**
 * @author qinc
 * @date 2017��11��29�� ����8:41:38
 * @ContextConfiguration����spring�ļ�λ��
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations="classpath:applicationContext..xml")
public class MapperTest {
	
	@Autowired
	DepartmentMapper departmentMapper;
	/*
	 * ����departmentmapper
	 * */
	@Test
	public void testCRUD(){
		System.out.println(departmentMapper);
	}
}
