<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div class="home">
	<!--MainStart-->
	<div id="main">
		<!--HeaderStart-->
		<div id="header">
			<!--HolderStart-->
			<div class="holder">
				<ul class="top-menu">
					<li class="about"><a href="<c:url value="/home" /> " >4youby.com</a> </li>
					<%-- temp comment
					<li class="login"><a href="<c:url value="/join" />"><spring:message code="join.now.free" /></a></li>
					--%>
				</ul>
			</div>
			<!--HolderEnd-Logo-->
			<strong class="logo"> <a href="<c:url value="/home" />"><font color="#ffa1e9">4youby.com</font></a> 
				<strong><spring:message code="amazing.ultra.sexy.cyber.world" /></strong>
			</strong>
			<!--MenuStart-->
			<nav>
				<ul class="menu">
					<li class="home"><a href="<c:url value="/home" />"><spring:message code="home" /></a></li>
				</ul>
			</nav>
			<!--MenuEnd-->
		</div>
		<!--HeaderEnd-ContentStart-->
		<div id="content">
			<p class="promo">
				<strong><spring:message code="image.gallery" /></strong>
				<%--
				<span style="text-align: center; padding-left: 30px; padding-right: 30px; display: inline;">|</span>
				<strong><spring:message code="title" />:</strong> ${gallery.name}
				<span style="text-align: center; padding-left: 30px; padding-right: 30px; display: inline;">|</span>
				--%>
			</p>
			<!--BoxBlockStart-->
			<c:if test="${!empty gallery.images}">
				<div class="box">
					<div class="small-holder">
						<!-- first images -->
						<ul id="featurelist">
							<!-- main image -->
							<c:forEach var="image" items="${gallery.images}" end="0" varStatus="status">
								<li class="item${status.count}">
									<a href="<c:url value="${galleryUrl}/${gallery.name}/image/${status.current.id}/${status.current.name}"><c:param name="imageNum" value="${status.count}"/></c:url>" title="${status.current.alt}">
										<img src="${domainUrl}/${status.current.path}" alt="${status.current.alt}" width="300" height="" />
									</a>
								</li>
							</c:forEach>
							<!-- left-right images -->
							<c:forEach var="image" items="${gallery.images}" begin="1" end="4" varStatus="status">
								<li class="item${status.count + 1}">
									<a href="<c:url value="${galleryUrl}/${gallery.name}/image/${status.current.id}/${status.current.name}"><c:param name="imageNum" value="${status.count + 1}"/></c:url>" title="${status.current.alt}">
										<img src="${domainUrl}/${image.thumbPath}" alt="${image.alt}" />
									</a>
								</li>
							</c:forEach>
						</ul>
						<!-- other images -->
						<ul class="small-gallery">
							<li>
								<ul>
									<c:forEach var="image" items="${gallery.images}" begin="5" varStatus="status">
										<li class="item${status.count + 5}">
											<a href="<c:url value="${galleryUrl}/${gallery.name}/image/${status.current.id}/${status.current.name}"><c:param name="imageNum" value="${status.count + 5}"/></c:url>" title="${status.current.alt}">
												<img src="${domainUrl}/${image.thumbPath}" alt="${image.alt}" />
											</a>
										</li>
									</c:forEach>
								</ul>
							</li>
						</ul>
					</div>
				</div>
			</c:if>
			<!--BoxBlockEnd-->		
		</div>
		<!--ContentEnd-FooterStart-->
		<div id="footer">
			<div id="prefooter">
				<p>
					${gallery.description}
				</p>
				<br />
										
				<!--tags -->
				<div>
					<c:forEach var="tag" items="${tags}">
						<a href="<c:url value="/tag/${tag}/stories" />" class="promo" 
							title="<spring:message code="all.stories.with.tag" arguments="${tag}" />" rel="tag">${tag}</a>
					</c:forEach>											
				</div>
				<br />	
				<p>
					<spring:message code="feel.free" />.
				</p>
			</div>
			<div class="holder">
				<nav>
					<ul class="menu">
						<li><a href="<c:url value="/home" />"><spring:message code="home" /></a></li>
					</ul>
				</nav>
			</div>
			<nav>
				<ul class="list">
					<li><a href="<c:url value="/home" />"><spring:message code="from.with.love" /></a></li>
					<li><a href="<c:url value="/home" />"><spring:message code="all.good" /></a></li>
				</ul>
			</nav>
		</div>
		<!--FooterEnd-->
	</div>
	<!--MainEnd-->
</div>