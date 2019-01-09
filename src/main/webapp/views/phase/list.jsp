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


<display:table name="phase" id="phaseId" requestURI="phase/list.do" pagesize="5" class="displaytag">

	<%--  Primero compruebo que es un admin --%>
	<security:authorize access="hasRole('HANDYWORKER)">


		<display:column>
			<a href="phase/edit.do?phaseId=${PhaseId.id}"><spring:message
						code="phase.edit"></spring:message></a>
		</display:column>


		<spring:message code="phaseId.title" var="phaseTitle"></spring:message>
		<display:column property="title" title="${phaseTitle}" sortable="true" />
		
		<spring:message code="phaseId.description" var="phaseDescription"></spring:message>
		<display:column property="description" title="${phaseDescription}" sortable="true" />

		<spring:message code="phaseId.startDate" var="phaseStarDate"></spring:message>
		<display:column property="startDate" title="${phaseStartDate}" sortable="true" />
		
		<spring:message code="phaseId.endDate" var="phaseEndDate"></spring:message>
		<display:column property="endDate" title="${phaseEndDate}" sortable="true" />
		
		
		<display:column>
		<a href="workplan/display.do?workplanId=${phase.workplan.id}"><spring:message
		   code="phase.workPlan"></spring:message></a>
		</display:column>




	</security:authorize>
</display:table>


<security:authorize access="hasRole('HANDYWORKER')">

<a href="phase/create.do"><spring:message code="phase.create"></spring:message></a>

</security:authorize>


