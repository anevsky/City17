<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt"%>
<%@ taglib uri="/customtags" prefix="ctg"%>

<div class="super-site-page">

	<div id="custom-header"></div>
	
	<!-- container start -->
	<div class="container">

		<header>
			<!-- header start -->
			<div id="header">
				<nav>
					<div id="portalLink">
						<a class="genu" href="<c:url value="/" />" title="<spring:message 
							code="amazing.ultra.sexy.cyber.world" />"><spring:message code="amazing.ultra.sexy.cyber.world" /></a>
					</div>
				</nav>
				 
				<br class="cbt" />
				
				<div id="hlogo">
					<a href="<c:url value="/home" />"><spring:message code="main.title" /></a>
				</div>			
				
				<nav>
					<div id="hmenus">
						<div class="nav mainnavs">
							<ul>
								<li class="youarehere"><a href="<c:url value="/home" />"><spring:message code="home" /></a></li>
								<li><a href="<c:url value="/tag/glamour/stories" />"><spring:message code="glamour" /></a></li>						
								<li><a href="<c:url value="/tag/games/stories" />"><spring:message code="games" /></a></li>
							</ul>
						</div>
					</div>
				</nav>
				
			</div>
			<!-- header end -->			
		</header>
		
		<section>
			<!-- content start -->
			<div id="content">
				
				<div id="mainbar-full" class="super-site-show-new">
					<header>
						<div class="subheader">
							<h1 id="super-site-displayname">
								<c:choose>
									<c:when test="${not empty pageTitle}">${pageTitle}</c:when>
									<c:otherwise><spring:message code="news.feed" /></c:otherwise>
								</c:choose> 
							</h1>
							<span class="toggle-summary-wrap">
								<a href="<c:url value="/" />" class="toggle-summary"><spring:message 
									code="from" /> Dr. Gordon Freeman <spring:message code="with.love" /> :)</a>
							</span>
						</div>
					</header>
					
					<div>
						<div id="super-site-panel-paragraphs" class="super-site-panel super-site-panel-left">
							
							<!-- start -->
							<c:forEach var="story" items="${stories}">
								<article>
									<div class="subheader">
										<header>
											<h1>										
												<a href="<c:url value="/story/${story.id}/${story.title}" />"> 
													<span class="count">
														<time pubdate="pubdate"><fmt:formatDate pattern="dd MMMMM yyyy" value="${story.publishedDate}" /></time>
													</span> - ${story.title}
												</a>
											</h1>
										</header>
									</div>
									
									<!-- story content start -->
									<div class="super-site-panel-content">
										<!-- images start -->
										<section>
											<c:forEach var="firstGallery" items="${story.galleries}" end="0">
												<c:forEach var="image" items="${firstGallery.images}" end="0" varStatus="status">
													<p style="text-align:center;">
														<a href="<c:url value="/story/${story.id}/${story.title}" />" title="${firstGallery.name}">
															<img src="${domainUrl}/${status.current.path}" alt="${status.current.alt}" 
																<c:choose>
																	<c:when test="${status.current.width > 720}">
																		width="720"
																	</c:when>
																	<c:when test="${status.current.width > 500}">
																		width="720"
																	</c:when>
																	<c:when test="${status.current.width < 500}">
																		width="720"
																	</c:when>
																	<c:otherwise>width="720"</c:otherwise>
																</c:choose> 
															/>
														</a>
													</p>
												</c:forEach>
											</c:forEach>
										</section>
										<!-- images end -->
										
										<!-- story text start -->
										<section>
											<ctg:displayStory story="${story}" showMore="false" />
										</section>
										<!-- story text end -->
										
									</div>
									<!-- story content end -->
									
									
									<footer>
										<div class="super-site-panel-footer">
											<nav>
												<!-- view links -->
												<c:if test="${not empty story.galleries[0]}">
													 <a href="<c:url value="/gallery/${story.galleries[0].id}/${story.galleries[0].name}" />"><spring:message code="view.gallery" /> &rsaquo;</a> | 
												</c:if>
												<a href="<c:url value="/story/${story.id}/${story.title}" />#more"><spring:message code="view.more" /> &rsaquo;</a>												
												
												<!--tags -->
												<c:if test="${not empty story.tags}">
													<br /><br />
													<div class="tags">
														<c:forEach var="tag" items="${story.tags}">
															<a href="<c:url value="/tag/${tag}/stories" />" class="post-tag" 
																title="<spring:message code="all.stories.with.tag" arguments="${tag}" />" rel="tag">${tag}</a>
														</c:forEach>											
													</div>
													<br /><br />
												</c:if>
												
												<p style="text-align: right;">
													<a href="<c:url value="#top" />" title="<spring:message code="go.to.top" />" style="text-decoration: none;">&uarr;</a>
													<a href="<c:url value="#bottom" />" title="<spring:message code="go.to.bottom" />" style="text-decoration: none;">&darr;</a>
												</p>
											</nav>
										</div>												
										<hr />									
									</footer>
																	
								</article>
							</c:forEach>
							
							<!-- end -->
							
							<nav>
								<p>
									<a href="<c:url value="/news/page/1" />"><spring:message code="first" /></a>
									 | 
									 <a href="<c:url value="/news/page/${prevPage}" />"><spring:message code="prev" /></a>							  
									 -| ${pagesNumStr} |- 
									 <a href="<c:url value="/news/page/${nextPage}" />"><spring:message code="next" /></a> 
									 | 
									 <a href="<c:url value="/news/page/${lastPage}" />"><spring:message code="last" /></a>
								</p>
							</nav>
							
						</div>	
						
						<div id="super-site-panel-right-col-feed" class="super-site-panel-right-col">
							<article>
								<div class="subheader">
									<h1>
										<a href="<c:url value="#" />"> 
											<span class="count">Social</span> Networks
										</a>
									</h1>
								</div>
								<div class="super-site-panel-right-col-content">
									<div class="fb-like-box" data-href="http://www.facebook.com/atenwood" data-width="250" data-show-faces="true" data-stream="true" data-header="true"></div>
								</div>
								<hr />
								<div class="subheader">
									<h1>
										<a href="<c:url value="#" />"> 
											<span class="count">Tags</span> Cloud
										</a>
									</h1>
									<div class="subtabs super-site-panel-right-col-subtabs">
										<a href="javascript:void(0)">popular</a> <a href="javascript:void(0)" class="youarehere">tags</a>
									</div>
								</div>
								<div class="super-site-panel-right-col-content">
									<p style="text-align: left;">
										<c:forEach var="tagMap" items="${tagsCloud}">
											<a href="<c:url value="/tag/${tagMap.key}/stories" />" class="post-tag" 
												title="<spring:message code="all.stories.with.tag" arguments="${tagMap.key}" />" rel="tag">${tagMap.key} (${tagMap.value})</a>
										</c:forEach>
									</p>
								</div>
								<hr />
								<div class="super-site-panel-right-col-footer">
									<a href="<c:url value="#top" />">top</a>
								</div>
							</article>
						</div>	
						
					</div>
	
					<div id="feed-link">
						<div id="feed-link-text">
							<a href="<c:url value="#top" />" title="<spring:message code="go.to.top" />"><spring:message code="top" /></a>
						</div>					
					</div>
					
				</div>
			</div>
			<!-- content end -->
		</section>
		
	</div>
	<!-- container end -->
	
	<!-- footer start -->
	<footer>
		<div id="footer">
			<div class="footerwrap">
				<div id="copyright">
					<spring:message code="from" /> <a style="text-decoration: none;" href="<c:url value="/home" />" 
						rel="license">Dr. Gordon Freeman</a> <spring:message code="with.love" /> / City 17
				</div>
			</div>
		</div>
	</footer>
	<!-- footer end -->
	
</div>