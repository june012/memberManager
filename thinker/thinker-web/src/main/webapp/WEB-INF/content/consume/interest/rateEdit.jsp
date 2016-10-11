<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/common.jsp"%>
<c:set var="pageTitle" value="${empty obj ? '添加门店':'修改门店' }" scope="page" />
<html>
<head>
<title>${pageTitle }</title>
</head>
<body>
	<div class="page-content">
		<div class="container-fluid">
			<!-- 页面导航 -->
			<tool:navBar pageTitle="${pageTitle }"
				pageTitleContent="系统管理-门店管理-${pageTitle }" titleIcon="icon-home" />
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
							<form action="${ctx }/sys/product/edit" class="form-horizontal form_sync"
								method="post" id="form1">
								<!-- 产品id -->
								<input type="hidden" value="${obj.id }" name="id">

								<c:if test="${not empty obj }">
									<input type="hidden" value="${obj.oaId }" name="oaId">
									<input type="hidden" value="${obj.typeName }" name="typeName">
								</c:if>

								<div class="control-group">
									<label class="control-label">产品名:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											validate="{required:true,minlength:1,maxlength:20}"
											name="productName" value="${obj.productName }" />
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">产品类型:</label>
									<div class="controls">
										<select data-placeholder=" " class="span6 chosen" tabindex="6" name="typeId">
											<c:forEach items="${types}" var="type">
												<option value="${type.id }"
														<c:if test="${obj.typeId eq type.id }">
															selected="selected"
														</c:if>
												>${store.typeName}</option>
											</c:forEach>
										</select>
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">产品价格:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											   onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"
											   validate="{required:true}" name="price"
											   value="${obj.price }" />
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
	});
</script>
</body>
</html>