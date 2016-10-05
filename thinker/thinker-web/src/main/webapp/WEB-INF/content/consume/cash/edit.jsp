<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/common.jsp"%>
<c:set var="pageTitle" value="${empty obj ? '添加消费记录':'修改消费记录' }" scope="page" />
<html>
<head>
<title>${pageTitle }</title>
</head>
<body>
	<div class="page-content">
		<div class="container-fluid">
			<!-- 页面导航 -->
			<tool:navBar pageTitle="${pageTitle }"
				pageTitleContent="消费管理-消费管理-${pageTitle }" titleIcon="icon-home" />
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
							<form action="${ctx }/consume/cash//edit" class="form-horizontal form_sync"
								method="post" id="form1">
								<!-- 用户创建日期 -->
								<c:if test="${not empty obj }">
									<!-- 用户ID -->
									<input type="hidden" value="${obj.id }" name="id">
									<!-- 用户状态 -->
									<input type="hidden" id="time" value="<fmt:formatDate value='${obj.createTime }'/>" name="createTime">
								</c:if>
								<div class="control-group">
									<label class="control-label">会员id:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											   validate="{required:true,minlength:2,maxlength:10}"
											   name="userid" value="${obj.userid }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">产品名称:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											validate="{required:true,minlength:2,maxlength:10}"
											name="productName" value="${obj.productName }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">产品类别:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											   validate="{required:true,minlength:2,maxlength:10}"
											   name="productType" value="${obj.productType }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">产品价格:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap"
											   validate="{required:true,minlength:1,maxlength:10}" id="productPrice"
											   name="productPrice" value="${obj.productPrice }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">数量:</label>
									<div class="controls">
										<input type="number" class="span6 m-wrap" min="1" max="999" id="count"
											   validate="{required:true}" name="count"
												<c:if test="${obj.discount eq null}">
													value="1"
												</c:if>
											   value="${obj.count }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">折扣:</label>
									<div class="controls">
										<input type="number" class="span6 m-wrap" min="0.1" step="0.1" max="1"
											   validate="{required:true}" name="discount" id="discount"
										<c:if test="${obj.discount eq null}">
											value="1"
										</c:if>
											   value="${obj.discount }" />
									</div>
								</div>
								<div class="control-group">
									<label class="control-label">消费总金额:</label>
									<div class="controls">
										<input type="text" class="span6 m-wrap" readonly="readonly"
											   validate="{required:true}" name="money" id="money"
												<c:if test="${obj.money eq null}">
													value="0"
												</c:if>
											   value="${obj.money }" />
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
		App.activeMenu("consume/cash/list");
		$('#count').change(function(){totalPrice();});
		$('#discount').change(function(){totalPrice();});
		$('#productPrice').change(function(){totalPrice();});
	function totalPrice() {
			var productPrice = $('#productPrice').val();
			var count = $('#count').val();
			var discount = $("#discount").val();
			var total= accMul(accMul(productPrice,count),discount);
			$('#money').val(total);
		}
	});

	function accMul(arg1,arg2)
	{
		var m=0,s1=arg1.toString(),s2=arg2.toString();
		try{m+=s1.split(".")[1].length}catch(e){}
		try{m+=s2.split(".")[1].length}catch(e){}
		return Number(s1.replace(".",""))*Number(s2.replace(".",""))/Math.pow(10,m)
	}
</script>
</body>
</html>