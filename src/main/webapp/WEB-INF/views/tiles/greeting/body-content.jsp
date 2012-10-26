<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div id="ac_background" class="ac_background">
	<img class="ac_bgimage" src="<c:url value="/static/site-sections/greeting/images/background/game-on.jpg" />" alt="Background" />
	<div class="ac_overlay"></div>
	<div class="ac_loading"></div>
</div>
<div id="ac_content" class="ac_content">
	<h1>
		<span>4YouBY</span>City17
	</h1>
	<div class="ac_menu">
		<ul>
			<li><a href="<c:url value="/static/site-sections/greeting/images/background/tron-1.jpg" />"><spring:message code="new.content" /></a>
				<div class="ac_subitem">
					<span class="ac_close"></span>
					<h2><spring:message code="news" /></h2>
					<ul>
						<li><spring:message code="amazing.hot.content.around" />!</li>
						<li><a href="<c:url value="/news" />"><spring:message code="news.feed" /></a></li>
					</ul>
				</div>
			</li>
									
			<li><a href="<c:url value="/static/site-sections/greeting/images/background/tron-2.jpg" />"><spring:message code="streams" /></a>
				<div class="ac_subitem">
					<span class="ac_close"></span>
					<h2><spring:message code="content.streams" /></h2>
					<ul>
						<li><spring:message code="what.do.you.want.to.read.about" /></li>
						<li><a href="<c:url value="/news" />"><spring:message code="general" /></a></li>
						<li><a href="<c:url value="/tag/glamour/stories" />"><spring:message code="glamour" /></a></li>
						<li><a href="<c:url value="/tag/games/stories" />"><spring:message code="games" /></a></li>
					</ul>
				</div>
			</li>
		</ul>
	</div>
</div>
<div class="ac_footer">
	<a class="ac_left" href="?locale=en"><span>&laquo; EN</span> lang</a> <a class="ac_left" href="?locale=ru"><span>||&nbsp;&nbsp;&nbsp;
			RU</span> lang <span>&raquo;</span></a> <a href="<c:url value="/news" />">news feed</a>
</div>