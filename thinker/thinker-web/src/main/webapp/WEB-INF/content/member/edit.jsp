<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/common.jsp"%>
<c:set var="pageTitle" value="${empty obj ? '添加会员':'修改会员信息' }" scope="page" />
<html>
<head>
<title>${pageTitle }</title>
</head>
<body>
	<div class="page-content">
		<div class="container-fluid">
			<!-- 页面导航 -->
			<tool:navBar pageTitle="${pageTitle }"
				pageTitleContent="会员管理-会员管理-${pageTitle }" titleIcon="icon-home" />
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
							<form action="${ctx }/member/create" class="form-horizontal form_sync" enctype="multipart/form-data"
								method="post" id="form1">
								<%--<!-- 用户创建日期 -->--%>
								<c:if test="${not empty obj }">
									<!-- 会员钱包 -->
									<%--本金--%>
									<input type="hidden" value="${obj.principal }"   name="principal">
									<%--余额--%>
									<input type="hidden" value="${obj.account }" name="account">
									<%--奖金--%>
									<input type="hidden" value="${obj.award }" name="award">
									<%--积分--%>
									<input type="hidden" value="${obj.credit }" name="credit">
									<%--等级--%>
									<input type="hidden" value="${obj.level }" name="level">
									<%--可被消费金额--%>
									<input type="hidden" value="${obj.canBeConsumed }" name="canBeConsumed">
									<%--利息--%>
									<input type="hidden" value="${obj.interest }" name="interest">
									<!-- 会员ID -->
									<input type="hidden" value="${obj.id }" name="id">
									<input type="hidden" value="${obj.status }" name="status">
									<input type="hidden" value="${obj.lastLoginTime }"   name="principal">
									<input type="hidden" value="${obj.devicesId }"   name="principal">
								</c:if>
								<div class="control-group">
									<label class="control-label">*登录账号(手机号):</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											   onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"
												<%--<c:if test="${not empty obj }">--%>
											   		<%--readonly--%>
												<%--</c:if>--%>
												<c:choose>
													<c:when test="${not empty obj }">
														readonly
														validate="{required:true,
														isMobile:true,}"
													</c:when>
													<c:otherwise>
														validate="{required:true,
														isMobile:true,
														remote:{type:'POST',
														url:'${ctx }/member/isAvailable',
														data:{}},
														messages:{remote:'该手机号已被注册'}}"
													</c:otherwise>
												</c:choose>
											   name="phone"
											   value="${obj.phone }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">*密码:</label>
									<div class="controls">
										<input type="hidden" value="${obj.password }" name="oldpwd">
										<input type="password" id="passwd" class="span6 m-wrap"
											   validate="{required:true,isPasswd:true}" name="password"
											   value="${obj.password}" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">*重复密码:</label>
									<div class="controls">
										<input type="password" class="span6 m-wrap"
											   validate="{required:true,isPasswd:true,equalTo:'#passwd'}"
											   value="${obj.password}" />
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">姓名:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											   name="name"
											   value="${obj.name }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">性别:</label>
									<div class="controls">
										<select data-placeholder=" " class="span6 chosen" tabindex="6" name="gender">
											<option value="男"
													<c:if test="${obj.gender eq '男' }">
														selected="selected"
													</c:if>
											>男</option>
											<option value="女"
													<c:if test="${obj.gender eq '女' }">
														selected="selected"
													</c:if>
											>女</option>
										</select>
									</div>
								</div>
								<div class="control-group">
								<label class="control-label">年龄:</label>
								<div class="controls">
									<input type="text" class="span6 m-wrap"
										   onkeyup="this.value=this.value.replace(/[^\d]/ig,'')"
										   name="age" value="${obj.age }" />
								</div>
							</div>
								<div class="control-group">
									<label class="control-label">微信号:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											   name="openid"
											   value="${obj.openid }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">头像:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											   name="avater" value="${obj.avater}" />
										<%--<input type="file" class="span6 m-wrap"--%>
											   <%--name="file"/>--%>

									</div>
								</div>
								<div class="control-group">
									<label class="control-label">地址:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											   name="address"
											   value="${obj.address }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">所属门店:</label>
									<div class="controls">
										<shiro:hasRole name="administrator">
										<select data-placeholder=" " class="span6 chosen" tabindex="6" name="storeId">

											<option value="0"
													<c:if test="${obj.storeId eq 0 }">
														selected="selected"
													</c:if>
											>总店</option>
											<c:forEach items="${stores}" var="store">
												<option value="${store.id }"
														<c:if test="${store.id eq obj.storeId }">
															selected="selected"
														</c:if>
												>${store.storeName }</option>
											</c:forEach>
										</select>
										</shiro:hasRole>
										<shiro:lacksRole name="administrator">
											<select data-placeholder=" " class="span6 chosen" tabindex="6" name="storeId">
												<option value="${currentUser.storeId}" selected>${currentUser.storeName}</option>
											</select>
										</shiro:lacksRole>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">备注:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											   name="remark"
											   value="${obj.remark }" />
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
		App.activeMenu("member/list");
	});
</script>
</body>
</html>