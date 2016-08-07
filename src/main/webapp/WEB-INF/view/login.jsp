<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/WEB-INF/taglib.jsp"%>

<!DOCTYPE html>
<html lang="en">
	<head>
		<meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1" />
		<meta charset="utf-8" />
		<title>JC系统登陆</title>

		<meta name="description" content="User login page" />
		<meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0" />

		<!-- bootstrap & fontawesome -->
		<link rel="stylesheet" href="/res/ace-1.3.3/assets/css/bootstrap.css" />
		<link rel="stylesheet" href="/res/ace-1.3.3/assets/css/font-awesome.css" />

		<!-- text fonts -->
		<link rel="stylesheet" href="/res/ace-1.3.3/assets/css/ace-fonts.css" />

		<!-- ace styles -->
		<link rel="stylesheet" href="/res/ace-1.3.3/assets/css/ace.css" />

		<!--[if lte IE 9]>
			<link rel="stylesheet" href="/res/ace-1.3.3/assets/css/ace-part2.css" />
		<![endif]-->
		<link rel="stylesheet" href="/res/ace-1.3.3/assets/css/ace-rtl.css" />

		<!--[if lte IE 9]>
		  <link rel="stylesheet" href="/res/ace-1.3.3/assets/css/ace-ie.css" />
		<![endif]-->

		<!-- HTML5 shim and Respond.js IE8 support of HTML5 elements and media queries -->

		<!--[if lt IE 9]>
		<script src="/res/ace-1.3.3/assets/js/html5shiv.js"></script>
		<script src="/res/ace-1.3.3/assets/js/respond.js"></script>
		<![endif]-->
	</head>

	<body class="login-layout light-login">
		<div class="main-container">
			<div class="main-content">
				<div class="row">
					<div class="col-sm-10 col-sm-offset-1">
						<div class="login-container">
							<div class="center">
								<h1>
									<i class=""></i>
								</h1>
								<!-- h4 class="blue" id="id-company-text">&copy; 缘创信息科技</h4 -->
							</div>

							<div class="space-6"></div>

							<div class="position-relative">
								<div id="login-box" class="login-box visible widget-box no-border">
									<div class="widget-body">
										<div class="widget-main">
											<h4 class="header blue lighter bigger">
												<i class="ace-icon fa fa-coffee green"></i>
												欢迎使用JC管理系统
											</h4>

											<div class="space-6"></div>

											<form>
												<fieldset>
													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="text" class="form-control" placeholder="Username" name="username" id="username"/>
															<i class="ace-icon fa fa-user"></i>
														</span>
													</label>

													<label class="block clearfix">
														<span class="block input-icon input-icon-right">
															<input type="password" class="form-control" placeholder="Password" name="password" id="password"/>
															<i class="ace-icon fa fa-lock"></i>
														</span>
													</label>

													<div class="space"></div>

													<div class="clearfix">
														<button type="button" id="login-btn" class="width-35 pull-right btn btn-sm btn-primary">
															<i class="ace-icon fa fa-key"></i>
															<span class="bigger-110">Login</span>
														</button>
													</div>

													<div class="space-4"></div>
												</fieldset>
											</form>
										</div><!-- /.widget-main -->

										<div class="toolbar clearfix">
											<div>
												<a href="#" data-target="#forgot-box" class="forgot-password-link">
													
												</a>
											</div>

											<div>
												<a href="#" data-target="#signup-box" class="user-signup-link">
													
												</a>
											</div>
										</div>
									</div><!-- /.widget-body -->
								</div><!-- /.login-box -->
							</div><!-- /.position-relative -->
						</div>
					</div><!-- /.col -->
				</div><!-- /.row -->
			</div><!-- /.main-content -->
		</div><!-- /.main-container -->

		<!-- basic scripts -->

		<!--[if !IE]> -->
		<script type="text/javascript">
			window.jQuery || document.write("<script src='/res/ace-1.3.3/assets/js/jquery.js'>"+"<"+"/script>");
		</script>

		<!-- <![endif]-->

		<!--[if IE]>
<script type="text/javascript">
 window.jQuery || document.write("<script src='/res/ace-1.3.3/assets/js/jquery1x.js'>"+"<"+"/script>");
</script>
<![endif]-->
		<script type="text/javascript">
			if('ontouchstart' in document.documentElement) document.write("<script src='/res/ace-1.3.3/assets/js/jquery.mobile.custom.js'>"+"<"+"/script>");
		</script>

		<!-- inline scripts related to this page -->
		<script type="text/javascript">
			jQuery(function($) {
				 $('#login-btn').click(function(event) {
				      event.stopPropagation();
				      var $btn = $(this);
				      if ($btn.hasClass("disabled")) {
				        return false;
				      }
				      var $loginname = $('#username');
				      var $password = $('#password');
				      if (!$loginname.val()) {
				        alert('请输入用户名！');
				        $loginname.focus();
				        return false;
				      }
				      if (!$password.val()) {
				        alert('请输入密码！');
				        $password.focus();
				        return false;
				      }
				      var submitData = {
				   		username : $loginname.val(),
				      	password : $password.val(),
				      	url:"${url}"
				      };
				      $btn.addClass("disabled");
				      $.post("/dologin", submitData, function(data) {
								$btn.removeClass("disabled");
								if (data.code == 0) {
									window.top.location.href = "/index#/";
								} else {
									alert(data.msg);
								}
							}, "json");
				      return false;
				    });
			});
		</script>
	</body>
</html>


