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
	<spring:message code="handyWorker.edit" />
</p>


	<div>
		<form:form action="handyWorker/edit.do" method="post" id="formCreate"
			name="formCreate" modelAttribute="handyWorker"></form:form>

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="banned" />
			<form:hidden path="suspicious" />
			<form:hidden path="senderMessages" />
			<form:hidden path="recipientMessages" />
			<form:hidden path="applications" />
			<form:hidden path="workplan" />
			<form:hidden path="tutorials" />
			<form:hidden path="curriculum" />
			<form:hidden path="folders" />
			<form:hidden path="socialProfiles" />

			<form:label path="name"> <spring:message code="handyWorker.name" /></form:label>
			<form:input path="name" /><form:errors cssClass="error" path="name" /><br />
			
			<form:label path="middleName"> <spring:message code="handyWorker.middleName" /></form:label>
			<form:input path="middleName" /><form:errors cssClass="error" path="middleName" /><br />
			
			
			<form:label path="surname"> <spring:message code="handyWorker.surname" /></form:label>
			<form:input path="surname" /><form:errors cssClass="error" path="surname" /><br />
			
			<form:label path="photo"> <spring:message code="handyWorker.photo" /></form:label>
			<form:input path="photo" /><form:errors cssClass="error" path="photo" /><br />
			
			<form:label path="email"> <spring:message code="handyWorker.email" /></form:label>
			<form:input path="email" /><form:errors cssClass="error" path="email" /><br />
			
			<form:label path="phone"> <spring:message code="handyWorker.phone" /></form:label>
			<form:input path="phone" /><form:errors cssClass="error" path="phone" /><br />
			
			<form:label path="address"> <spring:message code="handyWorker.address" /></form:label>
			<form:input path="address" /><form:errors cssClass="error" path="address" /><br />
			
			<form:label path="make"> <spring:message code="handyWorker.make" /></form:label>
			<form:input path="make" /><form:errors cssClass="error" path="make" /><br />
			
			<form:label path="finder"> <spring:message code="handyWorker.finder" /></form:label>
			<form:input path="finder" /><form:errors cssClass="error" path="finder" /><br />
			
			<form:label path="userAccount.username">
			<spring:message code="userAccount.username" />:
			</form:label>
			<form:input path="userAccount.username" />
			<form:errors cssClass="error" path="userAccount.username" />
		

	</div>
	<!--  Botones -->

		<input type="submit" name="save" value="<spring:message code="handyWorker.save"></spring:message>" />	
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('handyWorker/administrator/list.do')" />	

