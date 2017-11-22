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
package com.funtl.leesite.modules.sys.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.funtl.leesite.common.persistence.Page;
import com.funtl.leesite.common.service.CrudService;
import com.funtl.leesite.modules.sys.entity.SysVip;
import com.funtl.leesite.modules.sys.dao.SysVipDao;

/**
 * 用户管理Service
 * @author Jason
 * @version 2017-07-18
 */
@Service
@Transactional(readOnly = true)
public class SysVipService extends CrudService<SysVipDao, SysVip> {

	public SysVip get(String id) {
		return super.get(id);
	}
	
	public List<SysVip> findList(SysVip sysVip) {
		return super.findList(sysVip);
	}
	
	public Page<SysVip> findPage(Page<SysVip> page, SysVip sysVip) {
		return super.findPage(page, sysVip);
	}
	
	@Transactional(readOnly = false)
	public void save(SysVip sysVip) {
		super.save(sysVip);
	}
	
	@Transactional(readOnly = false)
	public void delete(SysVip sysVip) {
		super.delete(sysVip);
	}
	
	
	
	
}