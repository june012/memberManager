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
		<tool:navBar pageTitle="管理员列表" pageTitleContent="系统管理-管理员管理-管理员列表" titleIcon="icon-home"/>
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
									<!-- <div class="span3">
                                        <div class="control-group">
                                           <div class="controls">
                                              <input type="text" id="email" class="m-wrap span12" placeholder="邮箱">
                                           </div>
                                        </div>
                                     </div> -->

									<div class="span2">
										<div class="control-group">
											<div class="controls">
												<input type="text" id="name" class="m-wrap span12" placeholder="姓名">
											</div>
										</div>
									</div>

									<div class="span4">
										<select id="storeId" class="span4">
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
							<tool:operBtns modelKey="admin"></tool:operBtns>
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
		App.activeMenu("sys/admin/list");
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

		Page.initData(
				{
					url:"${ctx}/sys/admin/page",
					tableId : "#sample_1",
					pageSize : 10
				},
				null,
				[{cName:"name",cValue:"姓名"},
					{cName:"loginId",cValue:"登录名"},
					{cName:"gender",cValue:"性别"},
					{cName:"storeName",cValue:"所属门店"}
				]
		);
	});

	function doQuery(){
		var queryObj = {
			search_LIKES_name : App.isEqPlacehoder($("#name")),
			search_LIKES_storeId : App.isEqPlacehoder($("#storeId")),

		};
		Page.doQuery(queryObj);
	}
</script>
</body>
</html>