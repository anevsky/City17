<!-- Main Directives -->
<!-- Best Wishes to You, My Dear Hacker Friend! -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://tiles.apache.org/tags-tiles" prefix="tiles"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<!-- Extra Directives -->
<tiles:insertAttribute name="extra-directives" />

<!-- Vars -->
<c:url var="staticImagesIcon" value="/static/common/images/icon.jpg" />

<spring:theme var="themeAllCss" code="all.css" text="" />

<spring:message var="mainTitle" code="main.title" />

<!-- html -->
<!DOCTYPE html>
<html>
<head>
<!-- Main Meta -->
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<meta name="distribution" content="web, global" />
<meta name="robots" content="follow, all" />
<meta name="generator" content="Alex Web Engine 0.1" />
<meta name="description" content="<spring:message code="meta.description" />" />
<meta name="keywords" content="<spring:message code="meta.keywords" />"/>
<!-- Extra Meta -->
<tiles:insertAttribute name="extra-meta" />

<!-- Main Link Rel -->
<link rel="shortcut icon" href="${staticImagesIcon}" />
<link rel="stylesheet" type="text/css" href="<c:url value="${themeAllCss}" />" />
<!-- Extra Link Rel -->
<tiles:insertAttribute name="extra-link-rel" />

<!-- Main Scripts -->
<script type="text/javascript">
  var _gaq = _gaq || [];
  _gaq.push(['_setAccount', 'UA-21698633-1']);
  _gaq.push(['_setDomainName', 'atenwood.com']);
  _gaq.push(['_trackPageview']);

  (function() {
    var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
    ga.src = ('https:' == document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
    var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
  })();
</script>
<!-- Extra Scripts -->
<tiles:insertAttribute name="extra-scripts" />

<title><c:out value="${pageTitle}" default="${mainTitle}" escapeXml="false" /></title>
</head>
<body>
<div id="fb-root"></div>
<script>(function(d, s, id) {
  var js, fjs = d.getElementsByTagName(s)[0];
  if (d.getElementById(id)) return;
  js = d.createElement(s); js.id = id;
  js.src = "//connect.facebook.net/en_US/all.js#xfbml=1&appId=403667559706541";
  fjs.parentNode.insertBefore(js, fjs);
}(document, 'script', 'facebook-jssdk'));</script>

	<a id="top"></a>
	<tiles:insertAttribute name="body-content" />
	<a id="bottom"></a>
</body>
</html>