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



<display:table name="applications" id="ass" requestURI="application/list.do" pagesize="5" class="displaytag">
	

<security:authorize access="hasAnyRole('CUSTOMER','HANDYWORKER')">

	<display:column>
		<a href="application/edit.do?applicationId=${application.id}"><spring:message code="application.edit"></spring:message></a>
	</display:column>
</security:authorize>
		

	
		<spring:message code="application.price" var="applicationPrice"></spring:message>
		<display:column property="price" title="${applicationPrice}" sortable="true" />
		
		<spring:message code="application.moment" var="applicationMoment"></spring:message>
		<display:column property="moment" title="${applicationMoment}" sortable="true" />
		
		
		<jsp:useBean id="now" class="java.util.Date"/>	
		
		
		
		<jstl:if test="${application.status == 'ACCEPTED'}">
		<spring:message code="application.status" var="applicationStatus"></spring:message>
		<display:column property="status" title="${applicationStatus}" sortable="true" style="background-color:green" />		
		</jstl:if>
		
		<jstl:if test="${application.status == 'REJECTED'}">
		<spring:message code="application.status" var="applicationStatus"></spring:message>
		<display:column property="status" title="${applicationStatus}" sortable="true" style="background-color:orange" />		
		</jstl:if>
		
		<jstl:if test="${application.status == 'PENDING' && application.moment >= now}">
		<spring:message code="application.status" var="applicationStatus"></spring:message>
		<display:column property="status" title="${applicationStatus}" sortable="true" style="background-color:scheme" />		
		</jstl:if>
		
		<jstl:if test="${application.status == 'PENDING' && application.moment < now}">
		<spring:message code="application.status" var="applicationStatus"></spring:message>
		<display:column property="status" title="${applicationStatus}" sortable="true" style="background-color:grey" />		
		</jstl:if>
	
		
		<spring:message code="application.handyworker" var="applicationHandyworker"></spring:message>
		<display:column property="handyWorker" title="${application.Handyworker.name}" sortable="true" /> 
		
		<display:column>
			<a href="fixupTask/display.do?fixupTaskId=${applicationFixupTask.id}">
			<spring:message code="application.fixupTask"></spring:message></a>
		</display:column>
		
	
		
	</display:table>
	
	<security:authorize access="hasRole('HANDYWORKER')">
            <a href="application/create.do"><spring:message code="application.create"></spring:message></a>
	</security:authorize>
