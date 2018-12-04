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
	<spring:message code="category.display" />
</p>


<security:authorize access="hasRole('ADMIN')">
	<spring:message code="category.name"></spring:message>
	<jstl:out value="${category.name}">
	</jstl:out>
	<br />


	<spring:message code="category.parentCategory"></spring:message>
	<jstl:out value="${category.parentCategory.name}">
	</jstl:out>
	<br />


	<fieldset>
		<legend>
			<spring:message code="category.childCategories"></spring:message>
		</legend>
		<jstl:forEach items="${category.childCategories}" var="category">
			<a href="category/administrator/display.do?categoryId=${category.id}"><jstl:out
					value="${category.name}"></jstl:out></a>
			<br />
		</jstl:forEach>
	</fieldset>
	
	
	<fieldset>
	<legend>
		<spring:message code="category.fixupTasks"></spring:message>
	</legend>
	<jstl:forEach items="${category.fixupTasks}" var="fixupTask">
		<a href="fixupTask/display.do?tripId=${FixupTask.id}"><jstl:out
				value="${fixupTask.name}"></jstl:out></a>
		<br />
	</jstl:forEach>



</fieldset>
</security:authorize>

<security:authorize access="hasRole('ADMIN')">
	<spring:message code="category.return" var="returnHeader"></spring:message>
	<input type="button" name="return" value="${returnHeader}"
		onclick="javascript:relativeRedir('category/list.do')" />
</security:authorize>

<security:authorize access="hasRole('ADMIN')">

	<spring:message code="category.edit" var="editHead"></spring:message>
	<input type="button" name="edit" value="${editHead}"
		onclick="javascript:relativeRedir('category/administrator/edit.do?categoryId=${category.id}')" />
</security:authorize>

