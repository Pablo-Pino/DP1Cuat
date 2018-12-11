<%--
 * create.jsp
 *
 * Copyright (C) 2017 Universidad de Sevilla
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
	<spring:message code="fixupTask.create" />
</p>

<security:authorize access="hasRole('CUSTOMER')">
	<div>
		<form:form action="fixupTask/customer/edit.do" method="POST"
			id="formCreate" name="formCreate" modelAttribute="fixupTask">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="ticker" />
			<form:hidden path="moment" />
			<form:hidden path="description" />
			<form:hidden path="address" />
			<form:hidden path="maximunPrice" />
			<form:hidden path="start" />
			<form:hidden path="end" />

			<form:label path="applications">
				<spring:message code="fixupTask.applications"></spring:message>
			</form:label>

			<form:input path="applications" id="applications" name="applications" />
			<form:errors cssClass="error" path="comments"></form:errors>
			
			<form:label path="complaints">
				<spring:message code="fixupTask.complaints"></spring:message>
			</form:label>

			<form:input path="complaints" id="complaints" name="complaints" />
			<form:errors cssClass="error" path="complaints"></form:errors>

			<input type="submit" name="save"
				value="<spring:message code="fixupTask"></spring:message>" />

			<spring:message code="fixupTask.cancel" var="cancelHeader"></spring:message>
			<input type="button" name="cancel" value="${cancelHeader}"
				onclick="javascript:relativeRedir('fixupTask/customer/list.do')" />
		</form:form>

	</div>
</security:authorize>