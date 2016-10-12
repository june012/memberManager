<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/common.jsp"%>
<c:set var="pageTitle" value="添加产品类型" scope="page" />
<html>
<head>
<title>${pageTitle }</title>
</head>
<body>
	<div class="page-content">
		<div class="container-fluid">
			<!-- 页面导航 -->
			<tool:navBar pageTitle="${pageTitle }"
				pageTitleContent="系统管理-产品管理-${pageTitle }" titleIcon="icon-home" />
			<!-- 主体内容 -->
			<div class="row-fluid">
				<div class="span12">
					<div class="portlet box blue">
						<div class="portlet-title">
							<h4>
								<i class="icon-reorder"></i>请填写表单
							</h4>
							<div class="tools">
								<a href="javascript:;" class="collapse"></a> <a
									href="javascript:;" class="remove"></a>
							</div>
						</div>
						<div class="portlet-body form">

							<form action="${ctx }/sys/product/typeAdd" class="form-horizontal form_sync"
								method="post" id="form1">
								<div class="control-group">
									<label class="control-label">已有产品种类:</label>
									<div class="controls">
										<select span6 m-wrap tabindex="6" id="oldType" validate="{required:false}">
											<c:forEach items="${types}" var="type">
												<option value="${type.id }">${type.typeName}</option>
											</c:forEach>
										</select>
										<a class="btn blue" id="deleteType">
											删除<i class="icon-pencil"></i>
										</a>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">产品类型名:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											validate="{required:true,minlength:1,maxlength:20}"
											name="typeName" />
									</div>
								</div>
								<div class="form-actions">
									<button type="submit" class="btn blue">提交</button>
									<a class='btn' href="${header.Referer }">返回</a>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>
<%@ include file="/WEB-INF/content/common/plugins/jquery-validation.jsp"%>
<script type="text/javascript">
	$(function(){
		App.activeMenu("sys/product/list");

		$('#deleteType').bind('click', function(){
				var oldTypeId = $("#oldType").val();
				$.ajax({
					type: 'POST',
					url: '/sys/product/deleteType',
					dataType:'json',
					data:{'oldTypeId':oldTypeId},
					success: function(){
						alert("111");
						window.location.href="/sys/product/typeEdit";
					},
					error:function(){
						alert("222");
					}
				});
		});
	});
</script>
</body>
</html>