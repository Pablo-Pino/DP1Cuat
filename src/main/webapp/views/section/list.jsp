<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<display:table name="sections" id="section" requestURI="section/list.do?tutorialId=${tutorialId}" pagesize="5" >

	<spring:message code="section.title" var="titleTitle" />
	<display:column title="${titleTitle}" sortable="true" property="title" />
	
	<spring:message code="section.text" var="textTitle" />
	<display:column title="${textTitle}" property="text" />
	
	<spring:message code="section.pictures" var="picturesTitle" />
	<display:column title="${picturesTitle}" >
		<jstl:forEach items="${section.pictures}" step="picture" >
			<img src="${picture}" alt="${picture}">
		</jstl:forEach>
	</display:column>
	
	<spring:message code="section.numberOrder" var="numberOrderTitle" />
	<display:column title="${numberOrderTitle}" sortable="true" property="numberOrder" />
	
	<jstl:if test="${isPrincipalAuthorizedEdit}">
		<spring:message code="section.edit" var="editTitle" />
		<display:column>
			<a href="section/edit.do?sectionId=${section.id}">${editTitle}</a>
		</display:column>
	</jstl:if>

</display:table>

<spring:message code="section.create" var="createTitle" />
<button onclick='javascript: relativeRedir("<jstl:out value="section/create.do?tutorialId=${tutorial.id}"></jstl:out>")' >${createTitle}</button>