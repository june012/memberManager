<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/common.jsp"%>
<c:set var="pageTitle" value="${empty obj ? '添加提现记录':'修改提现记录' }" scope="page" />
<html>
<head>
<title>${pageTitle }</title>
</head>
<body>
	<div class="page-content">
		<div class="container-fluid">
			<!-- 页面导航 -->
			<tool:navBar pageTitle="${pageTitle }"
				pageTitleContent="消费管理-提现管理-${pageTitle }" titleIcon="icon-home" />
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
							<form action="${ctx }/consume/draw//edit" class="form-horizontal form_sync"
								method="post" id="form1">
								<!-- 用户创建日期 -->
								<c:if test="${not empty obj }">
									<!-- 用户ID -->
									<input type="hidden" value="${obj.id }" name="id">
									<!-- 用户状态 -->
									<input type="hidden" value="${obj.account_after }" name="account_after">
									<%--<input type="hidden" id="time" value="<fmt:formatDate value='${obj.createTime }'/>" name="createTime">--%>
								</c:if>
								<div class="control-group">
									<label class="control-label">会员编号:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap" id="member-name" name="userid"
												<c:choose>
													<c:when test="${not empty obj }">
														readonly
														value="${obj.userid }"
													</c:when>
													<c:otherwise>
														placeholder="请选择会员" onclick="pop()"
													</c:otherwise>
												</c:choose>
										/>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">提现金额:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap" id="money"
											   onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"
											   name="money"
											   value="${obj.money }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">提现原因:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											   validate="{required:false}" name="reson"
											   value="${obj.reson }" />
									</div>
								</div>
								<div class="form-actions">
									<button onclick="verification()" class="btn blue">提交</button>
									<a class='btn' href="${header.Referer }">返回</a>
								</div>
							</form>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<%--弹出框--%>
	<div id="choose-box-wrapper">
		<div id="choose-box">
			<div id="choose-box-title">
				<span>选择学校11111</span>
			</div>
			<div id="choose-a-province">
			</div>
			<div id="choose-a-school">
			</div>
			<div id="choose-box-bottom">
				<input type="botton" onclick="hide()" value="关闭" />
			</div>
		</div>
	</div>
	</div>
	<%@ include file="/WEB-INF/content/common/plugins/choose.jsp"%>
<%@ include file="/WEB-INF/content/common/plugins/jquery-validation.jsp"%>
<script type="text/javascript">
	$(function(){
		App.activeMenu("consume/draw/list");
	});
</script>
</body>
</html>