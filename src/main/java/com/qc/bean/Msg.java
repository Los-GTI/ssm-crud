/**
 * 
 */
package com.qc.bean;

import java.util.HashMap;
import java.util.Map;

/**
 * @author qinc
 * @date 2017��12��3�� ����1:20:40
 */
public class Msg {

	// ״̬�룬�ɹ�100��ʧ��200
	private int code;
	// ��ʾ��Ϣ
	private String msg;
	// �û�Ҫ���ظ������������
	private Map<String, Object> extend = new HashMap<String, Object>();

	public static Msg success() {
		Msg result = new Msg();
		result.setCode(100);
		result.setMsg("����ɹ���");
		return result;
	}

	public static Msg fail() {
		Msg result = new Msg();
		result.setCode(200);
		result.setMsg("����ʧ�ܣ�");
		return result;
	}

	public Msg add(String key, Object value) {
		this.getExtend().put(key, value);
		return this;
	}

	/**
	 * @return the code
	 */
	public int getCode() {
		return code;
	}

	/**
	 * @param code
	 *            the code to set
	 */
	public void setCode(int code) {
		this.code = code;
	}

	/**
	 * @return the msg
	 */
	public String getMsg() {
		return msg;
	}

	/**
	 * @param msg
	 *            the msg to set
	 */
	public void setMsg(String msg) {
		this.msg = msg;
	}

	/**
	 * @return the extend
	 */
	public Map<String, Object> getExtend() {
		return extend;
	}

	/**
	 * @param extend
	 *            the extend to set
	 */
	public void setExtend(Map<String, Object> extend) {
		this.extend = extend;
	}
}
