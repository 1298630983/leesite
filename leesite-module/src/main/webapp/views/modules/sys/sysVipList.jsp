<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="/views/include/taglib.jsp" %>

<!DOCTYPE html>
<!--[if IE 8]> <html lang="zh" class="ie8 no-js"> <![endif]-->
<!--[if IE 9]> <html lang="zh" class="ie9 no-js"> <![endif]-->
<!--[if !IE]><!-->
<html>
<!--<![endif]-->
<!-- BEGIN HEAD -->
<head>
	<title>${fns:getConfig('productName')} | 会员管理</title>
    <meta name="decorator" content="default"/>
</head>

<body class="page-container-bg-solid page-header-fixed page-sidebar-closed-hide-logo">
<div class="page-container">
	<div class="page-sidebar-wrapper">
        <div class="page-sidebar navbar-collapse collapse">
            <ul class="page-sidebar-menu" data-keep-expanded="false" data-auto-scroll="true" data-slide-speed="200">
                <li class="heading">
                    <h3 class="uppercase">功能菜单</h3>
                </li>
                <t:menu menu="${fns:getTopMenu()}" parentName="用户管理" currentName="会员管理"></t:menu>
            </ul>
        </div>
    </div>

    <div class="page-content-wrapper">
        <div class="page-content" style="padding-top: 0;">
            <div class="row">
                <div class="col-md-12">
                    <div class="portlet light">
                        <div class="portlet-title">
                            <div class="caption">
                                <span class="caption-subject bold font-grey-gallery uppercase"> 会员管理 </span>
                                <span class="caption-helper"></span>
                            </div>
                            <div class="tools">
                                <a href="" class="fullscreen"> </a>
                            </div>
                        </div>
                        <div class="portlet-body">
                            <sys:message content="${message}"/>
                            <div class="row" style="margin-bottom: 20px;">
                                <div class="col-md-12">
									<form:form id="searchForm" modelAttribute="sysVip" action="${ctx}/sys/sysVip" method="post" class="form-inline">
										<input id="pageNo" name="pageNo" type="hidden" value="${page.pageNo}"/>
										<input id="pageSize" name="pageSize" type="hidden" value="${page.pageSize}"/>
										<table:sortColumn id="orderBy" name="orderBy" value="${page.orderBy}" callback="sortOrRefresh();"/><!-- 支持排序 -->
										<div class="form-group">
                                            		<label>姓名：</label>
                                            			<form:input path="name" htmlEscape="false" maxlength="100" class="form-control input-sm"/>
                                        </div>
									</form:form>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="pull-left">
										<shiro:hasPermission name="sys:sysVip:add">
											<table:addRow url="${ctx}/sys/sysVip/form" title="会员管理"></table:addRow><!-- 增加按钮 -->
										</shiro:hasPermission>
										<shiro:hasPermission name="sys:sysVip:edit">
											<table:editRow url="${ctx}/sys/sysVip/form" title="会员管理" id="contentTable"></table:editRow><!-- 编辑按钮 -->
										</shiro:hasPermission>
										<shiro:hasPermission name="sys:sysVip:del">
											<table:delRow url="${ctx}/sys/sysVip/deleteAll" id="contentTable"></table:delRow><!-- 删除按钮 -->
										</shiro:hasPermission>
										<shiro:hasPermission name="sys:sysVip:import">
											<table:importExcel url="${ctx}/sys/sysVip/import"></table:importExcel><!-- 导入按钮 -->
										</shiro:hasPermission>
										<shiro:hasPermission name="sys:sysVip:export">
											<table:exportExcel url="${ctx}/sys/sysVip/export"></table:exportExcel><!-- 导出按钮 -->
										</shiro:hasPermission>
                                        <button class="btn btn-default btn-sm" onclick="sortOrRefresh()" title="刷新"><i class="fa fa-refresh"></i> 刷新</button>
                                    </div>
                                    <div class="pull-right">
                                        <button class="btn btn-primary btn-sm" onclick="search()"><i class="fa fa-search"></i> 查询</button>
                                        <button class="btn btn-primary btn-sm" onclick="reset()"><i class="fa fa-refresh"></i> 重置</button>
                                    </div>
                                </div>
                            </div>

                            <div class="row">
                                <div class="col-md-12">
                                    <div class="table-scrollable">
                                        <table id="contentTable" class="table table-striped table-bordered table-hover table-checkable">
											<thead>
												<tr style="cursor: pointer">
													<th>
														<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
															<input type="checkbox" class="group-checkable" data-set=".checkboxes" />
															<span></span>
														</label>
													</th>
													<th class="sort-column name">姓名</th>
													<th>操作</th>
												</tr>
											</thead>
											<tbody>
											<c:forEach items="${page.list}" var="sysVip">
												<tr>
													<td>
														<label class="mt-checkbox mt-checkbox-single mt-checkbox-outline">
                                                            <input type="checkbox" class="checkboxes i-checks" id="${sysVip.id}" />
                                                            <span></span>
                                                        </label>
													</td>
													<td><a  href="#" onclick="openDialogView('查看会员管理', '${ctx}/sys/sysVip/form?id=${sysVip.id}','900px', '600px')">
														${sysVip.name}
													</a></td>
													<td>
														<shiro:hasPermission name="sys:sysVip:view">
															<a href="#" onclick="openDialogView('查看会员管理', '${ctx}/sys/sysVip/form?id=${sysVip.id}','900px', '600px')" class="btn btn-info btn-xs" ><i class="fa fa-search-plus"></i> 查看</a>
														</shiro:hasPermission>
														<shiro:hasPermission name="sys:sysVip:edit">
															<a href="#" onclick="openDialog('修改会员管理', '${ctx}/sys/sysVip/form?id=${sysVip.id}','900px', '600px')" class="btn btn-success btn-xs" ><i class="fa fa-edit"></i> 修改</a>
														</shiro:hasPermission>
														<shiro:hasPermission name="sys:sysVip:del">
															<a href="${ctx}/sys/sysVip/delete?id=${sysVip.id}" onclick="return confirmx('确认要删除该会员管理吗？', this.href)" class="btn btn-danger btn-xs"><i class="fa fa-trash"></i> 删除</a>
														</shiro:hasPermission>
													</td>
												</tr>
											</c:forEach>
											</tbody>
										</table>
                                    </div>
                                    <table:page page="${page}"></table:page>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>

<%@ include file="/views/include/foot.jsp" %>
<script type="text/javascript">
	$(document).ready(function() {
	});
</script>
</body>
</html>