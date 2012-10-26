<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://www.springframework.org/tags" prefix="spring"%>

<spring:theme var="extraCss" code="page.css" />

<c:url var="extraCssStyle" value="${extraCss}" />

<link rel="stylesheet" type="text/css" href="${extraCssStyle}" />