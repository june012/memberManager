<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/common.jsp"%>
<c:set var="pageTitle" value="${empty obj ? '添加用户':'修改用户' }" scope="page" />
<html>
<head>
<title>${pageTitle }</title>
</head>
<body>
	<div class="page-content">
		<div class="container-fluid">
			<!-- 页面导航 -->
			<tool:navBar pageTitle="${pageTitle }"
				pageTitleContent="系统管理-用户管理-${pageTitle }" titleIcon="icon-home" />
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
							<form action="${ctx }/sys/admin/edit" class="form-horizontal form_sync"
								method="post" id="form1">
								<!-- 用户ID -->
								<input type="hidden" value="${obj.id }" name="id">
								<div class="control-group">
									<label class="control-label">姓名:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											validate="{required:true,minlength:2,maxlength:10}"
											name="name" value="${obj.name }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">登录名:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											validate="{required:true,
													   isLoginName:true,
													   remote:{type:'POST',
													   		   url:'${ctx }/sys/admin/isAvailable',
													   		   data:{oldValue:'${obj.loginId }'}},
													   messages:{remote:'该登录名已存在'}}"
											name="loginId" value="${obj.loginId }"
											${not empty obj ? 'readonly="readonly"':'' } />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">密码:</label>
									<div class="controls">
										<input type="hidden" value="${obj.passwd }" name="oldpwd">
										<input type="password" id="passwd" class="span6 m-wrap"
											validate="{required:true,isPasswd:true}" name="passwd"
											value="${obj.passwd }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">重复密码:</label>
									<div class="controls">
										<input type="password" class="span6 m-wrap"
											validate="{required:true,isPasswd:true,equalTo:'#passwd'}"
											name="repasswd" value="${obj.passwd }" />
									</div>
								</div>

								<div class="control-group">
									<label class="control-label">性别:</label>
									<div class="controls">
										<select data-placeholder=" " class="span6 chosen" tabindex="6" name="gender">
											<option value="male"
													<c:if test="${obj.gender eq male }">
														selected="selected"
													</c:if>
											>男</option>
											<option value="female"
													<c:if test="${obj.gender eq female }">
														selected="selected"
													</c:if>
											>女</option>
										</select>
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">分配角色:</label>
									<div class="controls">
										<shiro:hasRole name="administrator">
											<select data-placeholder=" " class="span6 chosen" tabindex="6" name="roleId">
												<c:forEach items="${roles }" var="role">
													<option value="${role.id }"
														<c:forEach items="${obj.roles }" var="userRole">
															<c:if test="${role.id eq userRole.id }">
																selected="selected"
															</c:if>
														</c:forEach>
													>${role.name }(${role.realName })</option>
												</c:forEach>
											</select>
										</shiro:hasRole>
										<shiro:lacksRole name="administrator">
											<select data-placeholder=" " class="span6 chosen" tabindex="6" name="roleId">
												<c:forEach items="${roles }" var="role">
													<c:if test="${role.name ne 'administrator'}">
														<option value="${role.id }"
																<c:forEach items="${obj.roles }" var="userRole">
																	<c:if test="${role.id eq userRole.id }">
																		selected="selected"
																	</c:if>
																</c:forEach>
														>${role.name }(${role.realName })</option>
													</c:if>
												</c:forEach>

											</select>
										</shiro:lacksRole>
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
											<c:forEach items="${stores }" var="store">
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
		App.activeMenu("sys/admin/list");
	});
</script>
</body>
</html>