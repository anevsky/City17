<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<div>
	<h2><spring:message code="error" /> ${errorCode}</h2>
	<h3><spring:message code="error.oops" /></h3>
	<p>
		${errorDetailsMessage}
	</p>
	<p>
		<a href="<c:url value="/home" />">Home</a>
	</p>
</div>