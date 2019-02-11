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

<security:authorize access="hasRole('HANDYWORKER')">
<spring:message code="application.myApplications" var="myApplications"></spring:message>
<h2><jstl:out value="${myApplications}"></jstl:out></h2>
</security:authorize>

<display:table name="applications" id="app" requestURI="${requestURI}" pagesize="5" class="displaytag">



<security:authorize access="hasAnyRole('CUSTOMER','HANDYWORKER')">

	<display:column>
	<jstl:if test="${app.status == 'PENDING'}">
		<a href="application/endorsable/edit.do?applicationId=${app.id}"><spring:message code="application.edit"></spring:message></a>
		</jstl:if>
	</display:column>

</security:authorize>


		<display:column>
		<a href="application/endorsable/display.do?applicationId=${app.id}"><spring:message code="application.display"></spring:message></a>
		</display:column>
		

	
		<spring:message code="application.price" var="applicationPrice"></spring:message>
		<display:column property="price" title="${applicationPrice}" sortable="true" />
		
		<spring:message code="application.moment" var="applicationMoment"></spring:message>
		<display:column property="moment" title="${applicationMoment}" sortable="true" />
		
		
		<jsp:useBean id="now" class="java.util.Date"/>	
		
		
		
		<jstl:if test="${app.status == 'ACCEPTED'}">
		<spring:message code="application.status" var="applicationStatus"></spring:message>
		<display:column property="status" title="${applicationStatus}" sortable="true" style="background-color:green" />		
		</jstl:if>
		
		<jstl:if test="${app.status == 'REJECTED'}">
		<spring:message code="application.status" var="applicationStatus"></spring:message>
		<display:column property="status" title="${applicationStatus}" sortable="true" style="background-color:orange" />		
		</jstl:if>
		
		<jstl:if test="${app.status == 'PENDING' && app.moment >= now}">
		<spring:message code="application.status" var="applicationStatus"></spring:message>
		<display:column property="status" title="${applicationStatus}" sortable="true" style="background-color:scheme" />		
		</jstl:if>
		
		<jstl:if test="${app.status == 'PENDING' && app.moment < now}">
		<spring:message code="application.status" var="applicationStatus"></spring:message>
		<display:column property="status" title="${applicationStatus}" sortable="true" style="background-color:grey" />		
		</jstl:if>
	
		
		<spring:message code="application.handyworker" var="applicationHandyworker"></spring:message>
		<display:column property="handyWorker.name" title="${applicationHandyworker}" sortable="true" /> 
		
		<display:column >
			<a href="fixupTask/endorsable/display.do?fixupTaskId=${app.fixupTask.id}">
			<spring:message code="application.fixupTask"></spring:message></a>
		</display:column>
		
	
		
	</display:table>
	
	<security:authorize access="hasRole('HANDYWORKER')">
            <a href="application/handyworker/create.do"><spring:message code="application.create"></spring:message></a>
            
            
	</security:authorize>
	
	<br />
	<br />
	
<security:authorize access="hasRole('HANDYWORKER')">
<spring:message code="application.allApplications" var="allApplications"></spring:message>
<h2><jstl:out value="${allApplications}"></jstl:out></h2>

<display:table name="allApplications" id="appp" requestURI="${requestURI}" pagesize="5" class="displaytag">


	
		<display:column property="price" title="${applicationPrice}" sortable="true" />
		
		<display:column property="moment" title="${applicationMoment}" sortable="true" />
		
		
		<jsp:useBean id="now2" class="java.util.Date"/>
		
		
		<jstl:if test="${appp.status == 'ACCEPTED'}">
		<display:column property="status" title="${applicationStatus}" sortable="true" style="background-color:green" />		
		</jstl:if>
		
		<jstl:if test="${appp.status == 'REJECTED'}">
		<display:column property="status" title="${applicationStatus}" sortable="true" style="background-color:orange" />		
		</jstl:if>
		
		<jstl:if test="${appp.status == 'PENDING' && appp.moment >= now2}">
		<display:column property="status" title="${applicationStatus}" sortable="true" style="background-color:scheme" />		
		</jstl:if>
		
		<jstl:if test="${appp.status == 'PENDING' && appp.moment < now2}">
		<display:column property="status" title="${applicationStatus}" sortable="true" style="background-color:grey" />		
		</jstl:if>
	
		
		<display:column property="handyWorker.name" title="${applicationHandyworker}" sortable="true" /> 
		
		<display:column >
			<a href="fixupTask/endorsable/display.do?fixupTaskId=${app.fixupTask.id}">
			<spring:message code="application.fixupTask"></spring:message></a>
		</display:column>
		
	
		
	</display:table>




</security:authorize>
	
