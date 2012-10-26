<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" 
    "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<link rel="shortcut icon" href="<c:url value="/static/common/images/icon.jpg" />"/>
<link rel="stylesheet" type="text/css" href="<c:url value="/static/site-sections/auth/css/form.css" />"/>
<script src="<c:url value="/static/site-sections/auth/scripts/cufon-yui.js" />" type="text/javascript"></script>
<script src="<c:url value="/static/site-sections/auth/scripts/ChunkFive_400.font.js" />" type="text/javascript"></script>
<script type="text/javascript">
	Cufon.replace('h1', {
		textShadow : '1px 1px #fff'
	});
	Cufon.replace('h2', {
		textShadow : '1px 1px #fff'
	});
	Cufon.replace('h3', {
		textShadow : '1px 1px #000'
	});
	Cufon.replace('.back');
</script>
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-21698633-4']);
  _gaq.push(['_setDomainName', '4youby.com']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
<title>Sign up</title>
</head>

<body>

	<div class="wrapper">
		<h1>Sign in</h1>

		<br />

		<h2>
			<a href="signup">Be social! <span>Join today!</span>
			</a>
		</h2>

		<div class="content">
			<div id="form_wrapper" class="form_wrapper">

				<form class="register active" name="signup" action="doSignup" method="post">
					<h3>Register</h3>
					<div class="column">
						<div>
							<label>First Name:</label> <input type="text" /> <span class="error">This is an error</span>
						</div>
						<div>
							<label>Last Name:</label> <input type="text" /> <span class="error">This is an error</span>
						</div>
						<div>
							<label>Website:</label> <input type="text" value="http://" /> <span class="error">This is an error</span>
						</div>
					</div>
					<div class="column">
						<div>
							<label>Username:</label> <input type="text" /> <span class="error">This is an error</span>
						</div>
						<div>
							<label>Email:</label> <input type="text" /> <span class="error">This is an error</span>
						</div>
						<div>
							<label>Password:</label> <input type="password" /> <span class="error">This is an error</span>
						</div>
					</div>
					<div class="bottom">
						<div class="remember">
							<input type="checkbox" /> <span>Send me updates</span>
						</div>
						<input type="submit" value="Register" /> <a href="login" rel="login" class="linkform">You have an account
							already? Log in here</a>
						<div class="clear"></div>
					</div>
				</form>

			</div>
			<div class="clear"></div>
		</div>

		<a class="back" href="<c:url value="/home" />">back home</a>
	</div>

	<!-- The JavaScript -->
	<script type="text/javascript" src="https://ajax.googleapis.com/ajax/libs/jquery/1.4.2/jquery.min.js"></script>
	<script type="text/javascript">
		$(function() {
			//the form wrapper (includes all forms)
			var $form_wrapper = $('#form_wrapper'),
			//the current form is the one with class active
			$currentForm = $form_wrapper.children('form.active') //,
			//the change form links
			//$linkform = $form_wrapper.find('.linkform');
			;

			//get width and height of each form and store them for later						
			$form_wrapper.children('form').each(function(i) {
				var $theForm = $(this);
				//solve the inline display none problem when using fadeIn fadeOut
				if (!$theForm.hasClass('active'))
					$theForm.hide();
				$theForm.data({
					width : $theForm.width(),
					height : $theForm.height()
				});
			});

			//set width and height of wrapper (same of current form)
			setWrapperWidth();

			function setWrapperWidth() {
				$form_wrapper.css({
					width : $currentForm.data('width') + 'px',
					height : $currentForm.data('height') + 'px'
				});
			}
		});
	</script>

</body>

</html>