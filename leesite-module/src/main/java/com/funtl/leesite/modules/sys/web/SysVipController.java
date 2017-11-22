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
package com.funtl.leesite.modules.sys.web;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.ConstraintViolationException;

import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.google.common.collect.Lists;
import com.funtl.leesite.common.utils.DateUtils;
import com.funtl.leesite.common.utils.MyBeanUtils;
import com.funtl.leesite.common.config.Global;
import com.funtl.leesite.common.persistence.Page;
import com.funtl.leesite.common.web.BaseController;
import com.funtl.leesite.common.utils.StringUtils;
import com.funtl.leesite.common.utils.excel.ExportExcel;
import com.funtl.leesite.common.utils.excel.ImportExcel;
import com.funtl.leesite.modules.sys.entity.SysVip;
import com.funtl.leesite.modules.sys.service.SysVipService;

/**
 * 用户管理Controller
 * @author Jason
 * @version 2017-07-18
 */
@Controller
@RequestMapping(value = "${adminPath}/sys/sysVip")
public class SysVipController extends BaseController {

	@Autowired
	private SysVipService sysVipService;
	
	@ModelAttribute
	public SysVip get(@RequestParam(required=false) String id) {
		SysVip entity = null;
		if (StringUtils.isNotBlank(id)){
			entity = sysVipService.get(id);
		}
		if (entity == null){
			entity = new SysVip();
		}
		return entity;
	}
	
	/**
	 * 会员管理列表页面
	 */
	@RequiresPermissions("sys:sysVip:list")
	@RequestMapping(value = {"list", ""})
	public String list(SysVip sysVip, HttpServletRequest request, HttpServletResponse response, Model model) {
		Page<SysVip> page = sysVipService.findPage(new Page<SysVip>(request, response), sysVip); 
		model.addAttribute("page", page);
		return "modules/sys/sysVipList";
	}

	/**
	 * 查看，增加，编辑会员管理表单页面
	 */
	@RequiresPermissions(value={"sys:sysVip:view","sys:sysVip:add","sys:sysVip:edit"},logical=Logical.OR)
	@RequestMapping(value = "form")
	public String form(SysVip sysVip, Model model) {
		model.addAttribute("sysVip", sysVip);
		return "modules/sys/sysVipForm";
	}

	/**
	 * 保存会员管理
	 */
	@RequiresPermissions(value={"sys:sysVip:add","sys:sysVip:edit"},logical=Logical.OR)
	@RequestMapping(value = "save")
	public String save(SysVip sysVip, Model model, RedirectAttributes redirectAttributes) throws Exception{
		if (!beanValidator(model, sysVip)){
			return form(sysVip, model);
		}
		if(!sysVip.getIsNewRecord()){//编辑表单保存
			SysVip t = sysVipService.get(sysVip.getId());//从数据库取出记录的值
			MyBeanUtils.copyBeanNotNull2Bean(sysVip, t);//将编辑表单中的非NULL值覆盖数据库记录中的值
			sysVipService.save(t);//保存
		}else{//新增表单保存
			sysVipService.save(sysVip);//保存
		}
		addMessage(redirectAttributes, "保存会员管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysVip";
	}
	
	/**
	 * 删除会员管理
	 */
	@RequiresPermissions("sys:sysVip:del")
	@RequestMapping(value = "delete")
	public String delete(SysVip sysVip, RedirectAttributes redirectAttributes) {
		sysVipService.delete(sysVip);
		addMessage(redirectAttributes, "删除会员管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysVip";
	}
	
	/**
	 * 批量删除会员管理
	 */
	@RequiresPermissions("sys:sysVip:del")
	@RequestMapping(value = "deleteAll")
	public String deleteAll(String ids, RedirectAttributes redirectAttributes) {
		String idArray[] =ids.split(",");
		for(String id : idArray){
			sysVipService.delete(sysVipService.get(id));
		}
		addMessage(redirectAttributes, "删除会员管理成功");
		return "redirect:"+Global.getAdminPath()+"/sys/sysVip";
	}
	
	/**
	 * 导出excel文件
	 */
	@RequiresPermissions("sys:sysVip:export")
    @RequestMapping(value = "export", method=RequestMethod.POST)
    public String exportFile(SysVip sysVip, HttpServletRequest request, HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会员管理"+DateUtils.getDate("yyyyMMddHHmmss")+".xlsx";
            Page<SysVip> page = sysVipService.findPage(new Page<SysVip>(request, response, -1), sysVip);
    		new ExportExcel("会员管理", SysVip.class).setDataList(page.getList()).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导出会员管理记录失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysVip";
    }

	/**
	 * 导入Excel数据
	 */
	@RequiresPermissions("sys:sysVip:import")
    @RequestMapping(value = "import", method=RequestMethod.POST)
    public String importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
		try {
			int successNum = 0;
			int failureNum = 0;
			StringBuilder failureMsg = new StringBuilder();
			ImportExcel ei = new ImportExcel(file, 1, 0);
			List<SysVip> list = ei.getDataList(SysVip.class);
			for (SysVip sysVip : list){
				try{
					sysVipService.save(sysVip);
					successNum++;
				}catch(ConstraintViolationException ex){
					failureNum++;
				}catch (Exception ex) {
					failureNum++;
				}
			}
			if (failureNum>0){
				failureMsg.insert(0, "，失败 "+failureNum+" 条会员管理记录。");
			}
			addMessage(redirectAttributes, "已成功导入 "+successNum+" 条会员管理记录"+failureMsg);
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入会员管理失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysVip";
    }
	
	/**
	 * 下载导入会员管理数据模板
	 */
	@RequiresPermissions("sys:sysVip:import")
    @RequestMapping(value = "import/template")
    public String importFileTemplate(HttpServletResponse response, RedirectAttributes redirectAttributes) {
		try {
            String fileName = "会员管理数据导入模板.xlsx";
    		List<SysVip> list = Lists.newArrayList(); 
    		new ExportExcel("会员管理数据", SysVip.class, 1).setDataList(list).write(response, fileName).dispose();
    		return null;
		} catch (Exception e) {
			addMessage(redirectAttributes, "导入模板下载失败！失败信息："+e.getMessage());
		}
		return "redirect:"+Global.getAdminPath()+"/sys/sysVip";
    }
	
	
	

}