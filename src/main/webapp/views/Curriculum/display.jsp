<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="curriculum.display" />
</p>

<spring:message code="curriculum.ticker" />
<jstl:out value="${curriculum.ticker}" />

<security:authorize access="hasRole('HANDYWORKER')">
	<jstl:out value="${curriculum.handyWorker.id}" value="handyWorkerId"></jstl:out>
	<security:authentication property="principal.username" var="username" />
	<jstl:if test='${curriculum.ranger.userAccount.username} == ${username}'>
		<display:column>
			<a href="curriculum/edit.do?curriculumId=${curriculum.id}"><spring:message
					code="curriculum.edit"></spring:message></a>
		</display:column>
	</jstl:if>
	<security:authentication property="principal.username" />
</security:authorize>
