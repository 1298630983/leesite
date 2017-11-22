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
    <meta name="decorator" content="form"/>

</head>
<body style="background: white;">
<div class="form-body">
	<form:form id="inputForm" modelAttribute="sysVip" action="${ctx}/sys/sysVip/save" method="post" class="form-horizontal" cssStyle="padding: 5px;">
		<form:hidden path="id"/>
		<sys:message content="${message}"/>

		<table class="table table-striped table-bordered table-hover">
			<tbody>
				<tr>
					<td class="active" style="width: 15%;"><label class="pull-right">登录名：</label></td>
					<td style="width: 35%;">
						<form:input path="loginName" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="active" style="width: 15%;"><label class="pull-right">密码：</label></td>
					<td style="width: 35%;">
						<form:input path="password" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="active" style="width: 15%;"><label class="pull-right">姓名：</label></td>
					<td style="width: 35%;">
						<form:input path="name" htmlEscape="false" maxlength="100" class="form-control "/>
					</td>
					<td class="active" style="width: 15%;"><label class="pull-right">邮箱：</label></td>
					<td style="width: 35%;">
						<form:input path="email" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
				</tr>
				<tr>
					<td class="active" style="width: 15%;"><label class="pull-right">电话：</label></td>
					<td style="width: 35%;">
						<form:input path="phone" htmlEscape="false" maxlength="200" class="form-control "/>
					</td>
					<td class="active" style="width: 15%;"></td>
		   			<td style="width: 35%;"></td>
		  		</tr>
			</tbody>
		</table>
	</form:form>
</div>

<%@include file="/views/include/foot.jsp" %>
<script type="text/javascript">
	var validateForm;
	function doSubmit(){//回调函数，在编辑和保存动作时，供openDialog调用提交表单。
	  if(validateForm.form()){
		  $("#inputForm").submit();
		  return true;
	  }

	  return false;
	}

	$(document).ready(function() {
		validateForm = $("#inputForm").validate({
			errorElement: 'span',
            errorClass: 'help-inline border-red font-red',
            focusInvalid: false,
            errorContainer: "#messageBox",

			submitHandler: function(form){
				loading('正在提交，请稍等...');
				form.submit();
			},

			errorPlacement: function(error, element) {
				$("#messageBox").text("输入有误，请先更正。");
				if (element.is(":checkbox")||element.is(":radio")||element.parent().is(".input-append")){
					error.appendTo(element.parent().parent());
				} else {
					error.insertAfter(element);
				}
			}
		});

	});
</script>
</body>
</html>