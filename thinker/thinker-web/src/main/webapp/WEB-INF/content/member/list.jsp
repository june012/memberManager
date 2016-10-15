<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/common.jsp"%>
<html>
<head>
	<title>用户列表</title>
</head>
<body>
<div class="page-content">
	<div class="container-fluid">
		<!-- 页面导航 -->
		<tool:navBar pageTitle="会员列表" pageTitleContent="会员管理-会员管理-会员列表" titleIcon="icon-home"/>
		<!-- 主体内容 -->
		<div class="row-fluid">
			<div class="span12">
				<div class="portlet box purple">
					<div class="portlet-title">
						<h4>
							<i class="diy_icon_04_38"></i>列表
						</h4>
						<div class="tools">
							<a href="javascript:;" class="collapse"></a><a
								href="javascript:;" class="remove"></a>
						</div>
					</div>
					<div class="portlet-body">
						<div class="row-fluid">
							<form class="queryForm span8">
								<div class="row-fluid">

									<div class="span2">
										<div class="control-group">
											<div class="controls">
												<input type="text" id="name" class="m-wrap span12" placeholder="姓名">
											</div>
										</div>
									</div>
									<div class="span2">
										<div class="control-group">
											<div class="controls">
												<input type="text" id="phone" class="m-wrap span12" placeholder="手机号">
											</div>
										</div>
									</div>
									<div class="span4">
										<select id="storeId" class="span4">
											<option value="">全部</option>
										</select>
									</div>


									<div class="span3">
										<div class="control-group">
											<div class="controls">
												<a class="btn blue" href="javascript:void(0)" onclick="javascript:doQuery();">
													<i class="icon-search"></i>
													查询</a>
												<button type="reset" class="btn" onclick="javascript:Page.clearQuery();">
													<i class="icon-trash"></i>清空
												</button>
											</div>
										</div>
									</div>
								</div>
							</form>
							<tool:memberOperBtns modelKey="member"></tool:memberOperBtns>
						</div>
						<table class="table table-striped table-bordered table-hover" id="sample_1">

						</table>
					</div>
				</div>
			</div>
		</div>
	</div>
</div>
<%@ include file="/WEB-INF/content/common/plugins/datepicker.jsp"%>
<%@ include file="/WEB-INF/content/common/plugins/page.jsp"%>


<script type="text/javascript">
	$(document).ready(function() {
		$(function(){

			$.ajax({
				type: 'POST',
				url: '/sys/store/getStores',
				dataType:'json',
				success: function(data){
					for(i=0;i<data.length;i++){
						$('#storeId').append('<option value="'+data[i].id+'">'+data[i].storeName+'</option>');
					}

				}
			});

		});
		//高亮左侧菜单
		App.activeMenu("member/list");
		Page.initData(
				{
					url:"${ctx}/member/page",
					tableId : "#sample_1",
					pageSize : 10
				},
				null,
				[{cName:"id",cValue:"会员编号"},
					{cName:"name",cValue:"姓名"},
					{cName:"age",cValue:"年龄"},
					{cName:"gender",cValue:"性别"},
					{cName:"phone",cValue:"手机号"},
					{cName:"openid",cValue:"微信号"},
					{cName:"credit",cValue:"积分"},
					{cName:"canBeConsumed",cValue:"可消费金额"},
					{cName:"account",cValue:"钱包余额"},
					{cName:"principal",cValue:"本金"},
					{cName:"storeId",cValue:"门店编号"}
				]
		);
	});
	function doQuery(){
		var queryObj = {
			search_EQS_phone : App.isEqPlacehoder($("#phone")),
			search_LIKES_name : App.isEqPlacehoder($("#name")),
			search_EQL_storeId : App.isEqPlacehoder($("#storeId"))
		};
		Page.doQuery(queryObj);
	}
</script>
</body>
</html>