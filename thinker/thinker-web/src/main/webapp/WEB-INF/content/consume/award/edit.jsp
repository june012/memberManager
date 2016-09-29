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
				pageTitleContent="消费管理-奖励管理-${pageTitle }" titleIcon="icon-home" />
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
							<form action="${ctx }/consume/award//edit" class="form-horizontal form_sync"
								method="post" id="form1">
								<!-- 用户ID -->
								<input type="hidden" value="${obj.id }" name="id">
								<!-- 用户创建日期 -->
								<c:if test="${not empty obj }">
									<!-- 消费时间 -->
									<input type="hidden" id="date" value="<fmt:formatDate value='${obj.date }'/>" name="date">
									<input type="hidden" id="awardAfter" value="${obj.awardAfter}" name="awardAfter">
								</c:if>
								<div class="control-group">
									<label class="control-label">奖励金额:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											validate="{required:true,minlength:0,maxlength:10}"
											   onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"
											name="awardMoney" value="${obj.awardMoney }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">奖励类型:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											   validate="{required:true}" name="awardType"
											   value="${obj.awardType}" />
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
		App.activeMenu("consume/award/list");
	});
</script>
</body>
</html>