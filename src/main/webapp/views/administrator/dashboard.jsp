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

<security:authorize access="hasRole('ADMIN')">

	<display:table name="actors" id="actor" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
		<spring:message code="actor.name" var="actorName"></spring:message>
		<display:column property="name" title="${actorName}" sortable="true" />
		
		<spring:message code="actor.middleName" var="actorMiddleName"></spring:message>
		<display:column property="middleName" title="${actorMiddleName}" sortable="true" />
		
		<spring:message code="actor.surName" var="actorSurName"></spring:message>
		<display:column property="surName" title="${actorSurName}" sortable="true" />
		
		<spring:message code="actor.email" var="actorEmail"></spring:message>
		<display:column property="email" title="${customerEmail}" sortable="true" />
		
		<spring:message code="actor.phone" var="actorPhone"></spring:message>
		<display:column property="phone" title="${actorPhone}" sortable="true" />
		
		<display:column>
			<a href="actor/administrator/edit.do?actorId=${actor.id}"> 
			<spring:message code="customer.fisxupTask.list"></spring:message></a>
		</display:column>
	
	
	</display:table>

</security:authorize>
