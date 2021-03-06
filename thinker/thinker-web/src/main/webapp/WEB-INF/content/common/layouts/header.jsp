<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/WEB-INF/content/common/common.jsp"%>
<!-- BEGIN HEADER -->
<div class="header navbar navbar-inverse navbar-fixed-top">
	<!-- BEGIN TOP NAVIGATION BAR -->
	<div class="navbar-inner">
		<div class="container-fluid">
			<!-- BEGIN LOGO -->
			<a class="brand" href="index.html"><img src="${ctx}/assets/img/logo.png" alt="logo" /></a>
			<!-- END LOGO -->
			<!-- BEGIN RESPONSIVE MENU TOGGLER -->
			<%-- <a href="javascript:;" class="btn-navbar collapsed" data-toggle="collapse" data-target=".nav-collapse"> 
				<img src="${ctx}/assets/img/menu-toggler.png" alt="" />
			</a> --%>
			<!-- END RESPONSIVE MENU TOGGLER -->
<!-- 			<div class="nav-collapse collapse">
            <ul class="nav">
              <li class="active"><a href="/">首页</a></li>
              <li class="">
                <a href="getting-started.html" class="bootstro" data-bootstro-title="这里介绍了从零开始使用Bootstrap的基本步骤。教你创建一个最简单的Bootstrap页面。" data-bootstro-placement="bottom" data-bootstro-step="0">起步</a>
              </li>
              <li class="">
                <a href="scaffolding.html" class="bootstro" data-bootstro-title="这里详细介绍了Bootstrap的栅格系统。对于栅格系统的理论知识，最好动手谷歌一下！" data-bootstro-placement="bottom" data-bootstro-step="1">脚手架</a>
              </li>
              <li class="">
                <a href="base-css.html" class="bootstro" data-bootstro-title="这里介绍了Bootstrap对HTML标签所设置的默认样式。任何一个符合HTML5规范的页面只要引入Bootstrap就自然具有了优雅的外观，这就是Bootstrap的功劳！" data-bootstro-placement="bottom" data-bootstro-step="2">基本CSS样式</a>
              </li>
              <li class="">
                <a href="components.html" class="bootstro" data-bootstro-title="这里介绍了常用的诸如按钮、弹框、导航条、下拉菜单、进度条等等常见的页面组件。按照例子照猫画虎就可以，熟能生巧。这些都不需要刻意去记忆，需要的时候随时回来翻文档，然后复制、粘贴即可！" data-bootstro-placement="bottom" data-bootstro-step="3">组件</a>
              </li>
              <li class="">
                <a href="javascript.html" class="bootstro" data-bootstro-title="这里介绍了Bootstrap自带的具有交互功能的组件。页面要想与用户交互当然离不开Javascript了。Bootstrap的组件都是基于jQuery的，你熟悉jQuery吗？建议谷歌一下。" data-bootstro-placement="bottom" data-bootstro-step="4">JavaScript插件</a>
              </li>
              <li class="">
                <a href="customize.html" class="bootstro" data-bootstro-title="在这里可以通过设置Bootstrap提供的参数改变Bootstrap的样貌。我们不建议过早接触这里，在你熟悉Bootstrap阶段，最好是通过覆盖样式的方式修改Bootstrap默认的外观。等你需要深入了解Bootstrap时，可以先学习Lesscss ---&gt;&gt;&gt;" data-bootstro-placement="bottom" data-bootstro-step="5">定制</a>
              </li>
              <li class="lesscss">
                <a target="_blank" href="lesscss.html" class="bootstro" data-bootstro-title="Lesscss是一种预编译脚本，编译之后就是你所熟悉的CSS了。Bootstrap就是采用Less语法书写的。想要深入Bootstrap底层的同学一定要看这里打好基础！" data-bootstro-placement="bottom" data-bootstro-step="6">Lesscss教程</a>
              </li>

              <li class="">
                <a href="about.html">关于</a>
              </li>
            </ul>
          </div> -->
			<!-- BEGIN TOP NAVIGATION MENU -->
			<ul class="nav pull-right">
				<!-- BEGIN USER LOGIN DROPDOWN -->
				<li class="dropdown user">
					<a href="#" class="dropdown-toggle" data-toggle="dropdown">
						<img alt="" src="${ctx}/assets/img/avatar1_small.jpg" />
							<span class="username"><shiro:principal/></span> 
							<i class="icon-angle-down"></i>
					</a>
					<ul class="dropdown-menu">
						<li><a href="${ctx}/sys/admin/updateMyself" >
							<i class="icon-user"></i>&nbsp&nbsp&nbsp<span>修改个人信息</span>
							</a>
						</li>
						<li class="divider"></li>
						<li><a href="${ctx }/logout/"><i class="icon-key"></i>退出</a></li>
					</ul>
				</li>
				<!-- END USER LOGIN DROPDOWN -->
			</ul>
			<!-- END TOP NAVIGATION MENU -->
		</div>
	</div>
	<!-- END TOP NAVIGATION BAR -->
</div>
<script src="${ctx}/assets/js/sys/header.js"></script>
<c:if test="${not empty sessionScope.cursite }">
<script type="text/javascript">
	var siteName = "${sessionScope.cursite.name}"
	$(function(){
		$(".notification li").removeClass("active");
		$(".notification li[data-sitename='"+siteName+"']").addClass("active");
	});
</script>
</c:if>