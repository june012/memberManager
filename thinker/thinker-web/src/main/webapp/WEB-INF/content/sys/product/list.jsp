<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/content/common/common.jsp"%>
<html>
<head>
	<title>产品列表</title>
</head>
<body>
<div class="page-content">
	<div class="container-fluid">
		<!-- 页面导航 -->
		<tool:navBar pageTitle="产品列表" pageTitleContent="系统管理-产品管理-产品列表" titleIcon="icon-home"/>
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

							<shiro:hasPermission name="sys:store:add">
							<form class="queryForm span8">
								<div class="row-fluid">

										<div class="span2">
											<div class="control-group">
												<div class="controls">
													<input type="text" id="productName" class="m-wrap span12" placeholder="产品名称">
												</div>
											</div>
										</div>

									<div class="span4">
										<select id="typeId" class="span4" >
											<option value="">全部</option>
										</select>
									</div>

									<div class="span5">
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
							</shiro:hasPermission>
							<tool:operBtns modelKey="store"></tool:operBtns>
							<div class="span4 pull-right">
								<shiro:hasAnyRoles name="administrator,shopowner">
									<a class="btn green" href="${ctx }/sys/product/typePage">
										添加产品类型 <i class="icon-plus"></i>
									</a>
								</shiro:hasAnyRoles>
							</div>
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
		//高亮左侧菜单
		App.activeMenu("/sys/product/list");
		$.ajax({
			type: 'POST',
			url: '/sys/product/getTypes',
			dataType:'json',
			success: function(data){
				for(i=0;i<data.length;i++){
					$('#typeId').append('<option value="'+data[i].id+'">'+data[i].typeName+'</option>');
				}

			}
		});

		Page.initData(
				{
					url:"${ctx}/sys/product/page",
					tableId : "#sample_1",
					pageSize : 10
				},
				null,
				[{cName:"id",cValue:"产品编号"},
					{cName:"productName",cValue:"产品名"},
					{cName:"typeName",cValue:"产品类型"},
					{cName:"price",cValue:"产品价格"}
				]
		);
	});
	function doQuery(){
		var queryObj = {
			search_LIKES_productName : App.isEqPlacehoder($("#productName")),
			search_EQL_typeId : App.isEqPlacehoder($("#typeId"))
		};
		Page.doQuery(queryObj);
	}
</script>
</body>
</html>