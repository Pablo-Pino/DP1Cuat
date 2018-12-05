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
	<spring:message code="application.list" />
</p>


	<display:table name="applications" id="application" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
<security:authorize access="hasRole('CUSTOMER')">

	<display:column>
		<a href="application/edit.do?applicationId=${application.id}"><spring:message code="applicarion.edit"></spring:message></a>
	</display:column>
</security:authorize>

<security:authorize access="hasRole('HANDYWORKER')">

	<display:column>
		<a href="application/edit.do?applicationId=${application.id}"><spring:message code="applicarion.edit"></spring:message></a>
	</display:column>
</security:authorize>


	
		<spring:message code="application.price" var="applicationPrice"></spring:message>
		<display:column property="price" title="${applicationPrice}" sortable="true" />
		
		<spring:message code="application.moment" var="applicationMoment"></spring:message>
		<display:column property="moment" title="${applicationMoment}" sortable="true" />
		
		<spring:message code="application.status" var="applicationStatus"></spring:message>
		<display:column property="status" title="${applicationStatus}" sortable="true" />
		
		<spring:message code="application.customer" var="applicationCustomer"></spring:message>
		<display:column property="customer" title="${applicationCustomer.name}" sortable="true" />
		
		<spring:message code="application.handyworker" var="applicationHandyworker"></spring:message>
		<display:column property="customer" title="${applicationHandyworker.name}" sortable="true" />
		
	
		
	</display:table>
