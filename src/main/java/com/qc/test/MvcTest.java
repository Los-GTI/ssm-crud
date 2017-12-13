/**
 * 
 */
package com.qc.test;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import com.github.pagehelper.PageInfo;

/**
 * @author qinc
 * @date 2017��12��1�� ����3:56:19
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"classpath:applicationContext.xml","classpth:spring-mvc.xml"})
public class MvcTest {

	//����Springmvc��ioc
	@Autowired
	WebApplicationContext context;
	//����mvc���󣬻�ȡ��������
	MockMvc mockMvc;
	
	@Before
	public void initMockMvc(){
		mockMvc=MockMvcBuilders.webAppContextSetup(context).build();
	}
	
	@Test
	public void testPage() throws Exception{
		//ģ�������õ�����ֵ
		MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/emps").param("pn", "1")).andReturn();
		//����ɹ�֮���������л���pageInfo�����ǿ���ȡ��pageInfo����֤
		MockHttpServletRequest request=result.getRequest();
		PageInfo pageInfo=(PageInfo)request.getAttribute("pageInfo");
		System.out.println("��ǰҳ�룺"+pageInfo.getPageNum());
		System.out.println("��ǰҳ�룺"+pageInfo.getPages());	
	}
}
