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
	<jstl:if
		test='${curriculum.handyWorker.userAccount.username} == ${username}'>
		
		
		<fieldset>
			<legend>
				<spring:message code="curriculum.personalRecord"></spring:message>
			</legend>
			<spring:message code="curriculum.fullName"></spring:message>
			<jstl:out value="${customer.personalRecord.fullName}"></jstl:out>
			<br />
			<spring:message code="curriculum.linkedinProfile"></spring:message>
			<jstl:out value="${customer.personalRecord.linkedinProfile}"></jstl:out>
			<br />
			<jstl:if test="${curriculum.id}== 0">
				<spring:message code="curriculum.add" var="add"></spring:message>
				<input type="button" name="add" value="${add}"
					onclick="javascript:relativeRedir('personalRecord/create.do?handyWorkerId=${curriculum.handyWorker.id}')" />
			</jstl:if>
			<spring:message code="curriculum.display" var="display"></spring:message>
			<input type="button" name="display" value="${display}"
				onclick="javascript:relativeRedir('personalRecord/display.do?personalRecordId=${curriculum.personalRecord.id}')" />
			<spring:message code="curriculum.edit" var="edit"></spring:message>
			<input type="button" name="edit" value="${edit}"
				onclick="javascript:relativeRedir('personalRecord/edit.do?personalRecordId=${curriculum.personalRecord.id}')" />
		</fieldset>
		
		
		
		
		<fieldset>
			<legend>
				<spring:message code="curriculum.EducationRecords"></spring:message>
			</legend>
			<display:table name="educationRecords" id="eduactionRecord" pagesize="5" class="displaytag">
			
			<spring:message code="curriculum.diplomeTitle" var="diplomeTitle"></spring:message>
		<display:column property="name" title="${diplomeTitle}" sortable="true" />
			
			</display:table>
			
			
			<spring:message code="curriculum.fullName"></spring:message>
			<jstl:out value="${customer.personalRecord.fullName}"></jstl:out>
			<br />
			<spring:message code="curriculum.linkedinProfile"></spring:message>
			<jstl:out value="${customer.personalRecord.linkedinProfile}"></jstl:out>
			<br />
			<jstl:if test="${curriculum.id}== 0">
				<spring:message code="curriculum.add" var="add"></spring:message>
				<input type="button" name="add" value="${add}"
					onclick="javascript:relativeRedir('personalRecord/create.do?handyWorkerId=${curriculum.handyWorker.id}')" />
			</jstl:if>
			<spring:message code="curriculum.display" var="display"></spring:message>
			<input type="button" name="display" value="${display}"
				onclick="javascript:relativeRedir('personalRecord/display.do?personalRecordId=${curriculum.personalRecord.id}')" />
			<spring:message code="curriculum.edit" var="edit"></spring:message>
			<input type="button" name="edit" value="${edit}"
				onclick="javascript:relativeRedir('personalRecord/edit.do?personalRecordId=${curriculum.personalRecord.id}')" />
		</fieldset>
		
		
		
		
		
	</jstl:if>
	<security:authentication property="principal.username" />
</security:authorize>
