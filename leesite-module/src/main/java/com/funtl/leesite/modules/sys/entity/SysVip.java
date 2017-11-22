/*
 * Copyright 2015-2017 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.funtl.leesite.modules.sys.entity;

import org.hibernate.validator.constraints.Length;

import com.funtl.leesite.common.persistence.DataEntity;
import com.funtl.leesite.common.utils.excel.annotation.ExcelField;

/**
 * 用户管理Entity
 * @author Jason
 * @version 2017-07-18
 */
public class SysVip extends DataEntity<SysVip> {
	
	private static final long serialVersionUID = 1L;
	private String loginName;		// 登录名
	private String password;		// 密码
	private String name;		// 姓名
	private String email;		// 邮箱
	private String phone;		// 电话
	
	public SysVip() {
		super();
	}

	public SysVip(String id){
		super(id);
	}

	@Length(min=0, max=100, message="登录名长度必须介于 0 和 100 之间")
	@ExcelField(title="登录名", align=2, sort=1)
	public String getLoginName() {
		return loginName;
	}

	public void setLoginName(String loginName) {
		this.loginName = loginName;
	}
	
	@Length(min=0, max=100, message="密码长度必须介于 0 和 100 之间")
	@ExcelField(title="密码", align=2, sort=2)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	@Length(min=0, max=100, message="姓名长度必须介于 0 和 100 之间")
	@ExcelField(title="姓名", align=2, sort=3)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
	
	@Length(min=0, max=200, message="邮箱长度必须介于 0 和 200 之间")
	@ExcelField(title="邮箱", align=2, sort=4)
	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}
	
	@Length(min=0, max=200, message="电话长度必须介于 0 和 200 之间")
	@ExcelField(title="电话", align=2, sort=5)
	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}
	
}