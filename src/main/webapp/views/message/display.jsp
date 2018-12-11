<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="message.display" />
</p>


<jstl:if test="${message.subject != null }">

	<spring:message code="message.subject"></spring:message>
	<jstl:out value="${message.subject}"></jstl:out>
</jstl:if>

<jstl:if test="${message.priority != null }">

	<spring:message code="message.priority"></spring:message>
	<jstl:out value="${message.priority}"></jstl:out>
</jstl:if>

<jstl:if test="${message.body != null }">

	<spring:message code="message.body"></spring:message>
	<jstl:out value="${message.body}"></jstl:out>
</jstl:if>

<jstl:if test="${message.tags != null }">

	<spring:message code="message.tags"></spring:message>
	<jstl:out value="${message.tags}"></jstl:out>
</jstl:if>

<jstl:if test="${message.folder != null }">

	<spring:message code="message.folder"></spring:message>
	<jstl:out value="${message.folder}"></jstl:out>
</jstl:if>

