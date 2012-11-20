<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="page-view-gall">
	<div id="main">
		<div class="container">
			<nav>
				<ul class="big-gallery">
					<li><a href="<c:url value="/home" />">Atenwood.Com</a></li>
					<li><a href="<c:url value="${galleryUrl}" />">${galleryName}</a></li>
				</ul>
			</nav>
			<strong class="image-num">${imageNumStr}</strong>
			<nav>
				<ul class="page-list">
					<li class="prev"><a id="previous" href="<c:url value="${prevImageUrl}" />"><span><spring:message code="prev" /></span></a></li>
					<li class="next"><a id="next" href="<c:url value="${nextImageUrl}" />"> <span><spring:message code="next" /></span></a></li>
				</ul>
			</nav>
		</div>
		<figure>
			<a id="img_next" href="<c:url value="${nextImageUrl}" />"><img id="gallery_image" alt="${image.alt}" src="${domainUrl}/${image.path}" /></a>
			<br />
			<figcaption><p style="text-align:center;">${image.alt}</p></figcaption>			
		</figure>
		<!--tags -->
		<div style="text-align:center;">
			<c:forEach var="tag" items="${tags}">
				<a href="<c:url value="/tag/${tag}/stories" />" class="promo"
					title="<spring:message code="all.stories.with.tag" arguments="${tag}" />" rel="tag">${tag}</a>
			</c:forEach>										
		</div>
	</div>
</div>