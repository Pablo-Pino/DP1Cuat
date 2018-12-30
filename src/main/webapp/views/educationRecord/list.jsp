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
	<spring:message code="educationRecord.list" />
</p>


<display:table name="educationRecord" id="educationRecord"
	requestURI="educationRecord/handyWorker/list.do" pagesize="5"
	class="displaytag">

	<%--  Primero compruebo que es un handyWorker --%>
	<security:authorize access="hasRole('HANDYWORKER')">


		<%--  La columna que va a la vista edit de las educationRecord --%>
		<display:column>
			<a href="educationRecord/handyWorker/edit.do?educationRecordId=${EducationRecord.id}"><spring:message
					code="educationRecord.edit"></spring:message></a>
		</display:column>


		<%--  La columna que va a la vista display de las educationRecord --%>
		<display:column>
			<a href="educationRecord/handyWorker/display.do?educationRecordId=${EducationRecord.id}"><spring:message
					code="educationRecord.display"></spring:message></a>
		</display:column>

		<spring:message code="educationRecord.diplomaTitle" var="educationRecordDiplomaTitle"></spring:message>
		<display:column property="diplomaTitle" title="${educationRecordDiplomaTitle}"
			sortable="true" />

		<spring:message code="educationRecord.endDate" var="endDate"></spring:message>
		<display:column property="endDate"
			title="${endDate}" sortable="true" />
			
			<spring:message code="educationRecord.institution" var="institution"></spring:message>
		<display:column property="institution"
			title="${institution}" sortable="true" />


	


	</security:authorize>
</display:table>


<%--  Boton de creacion --%>
<security:authorize access="hasRole('HANDYWORKER')">
	<a href="educationRecord/handyWorker/create.do"><spring:message
			code="educationRecord.create"></spring:message></a>
</security:authorize>


