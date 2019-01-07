			
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

		<form:form action="fixupTask/customer/create.do" method="POST"
			id="formCreate" name="formCreate" modelAttribute="fixupTask">

			<!-- Atributos hidden-->

				<form:hidden path="id" />
				<form:hidden path="version" />
				<form:hidden path="moment" />
				<form:hidden path="category" />
				<form:hidden path="warranty" />
				<form:hidden path="customer" />
				<form:hidden path="application" />
				<form:hidden path="workplan" />
				<form:hidden path="complaints" />
			

			<fieldset>
				<!-------------------Form ------------------------------------>

				<div>
					<form:label path="maximumPrice">
						<spring:message code="fixupTask.maximumPrice"></spring:message>
					</form:label>
					<form:input path="maximumPrice" id="maximumPrice" name="maximumPrice" />
					<form:errors cssClass="error" path="maximumPrice" />
					<br />
				</div>
				
				<div>
					<form:label path="applications">
						<spring:message code="fixupTask.applications"></spring:message>
					</form:label>
					<form:input path="applications" id="applications" name="applications" />
					<form:errors cssClass="error" path="applications" />
					<br />
				</div>
				
				<div>
					<form:label path="complaints">
						<spring:message code="fixupTask.complaints"></spring:message>
					</form:label>
					<form:input path="complaints" id="complaints" name="complaints" />
					<form:errors cssClass="error" path="complaints" />
					<br />
				</div>
				
				<div>
					<form:label path="address">
						<spring:message code="fixupTask.address"></spring:message>
					</form:label>
					<form:input path="address" id="address" name="address" />
					<form:errors cssClass="error" path="address" />
					<br />
				</div>
				
				<div>
					<form:label path="description">
						<spring:message code="fixupTask.description"></spring:message>
					</form:label>
					<form:input path="description" id="description" name="description" />
					<form:errors cssClass="error" path="description" />
					<br />
				</div>

			</fieldset>

	
	<!--  Botones -->
	
		<input type="submit" name="save" value="<spring:message code="fixupTask.save"></spring:message>" />	
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('fixupTask/list.do')" />	

		</form:form>

	</div>





</security:authorize>

