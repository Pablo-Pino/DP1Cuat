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

<security:authorize access="hasRole('CUSTOMER')">
	<div>

		<form:form action="application/create.do" method="POST" id="formCreate" name="formCreate" modelAttribute="customer">

			<!-- Atributos hidden-->
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="referee" />
			
			
				<!-- Form -->
				<div>
					<spring:message code="complaint.moment" var="moment" />
					<display:column property="moment" title="${moment}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
					<br />
				</div>
				
				<textarea>
					<form:label path="description"> <spring:message code="complaint.description"></spring:message></form:label>
					<form:input path="description" id="description" name="description" /><form:errors cssClass="error" path="description" />
				</textarea>
				<br />
				
				
				<textarea>
					<form:label path="attachments"> <spring:message code="complaint.attachments"></spring:message></form:label>
					<form:input path="attachments" id="attachments" name="attachments" /><form:errors cssClass="error" path="attachments" />
				</textarea>
				
				<br>
							
				<form:label path="fixupTask"><spring:message code="complaint.fixupTask"></spring:message></form:label>
				<form:select id="fixupTask" path="fixupTask">
				<form:option value="${FIXUPTASK}" label="------"></form:option>
				<form:options items="${FIXUPTASK}" itemLabel="name" itemValue="id" />
				</form:select>
				<form:errors cssClass="error" path="fixuptask" />
				
				
				<form:label path="report"><spring:message code="complaint.report"></spring:message></form:label>
				<form:select id="report" path="report">
				<form:option value="${REPORT}" label=""></form:option>
				<form:options items="${REPORT}" itemLabel="name" itemValue="id" />
				</form:select>
				<form:errors cssClass="error" path="report" />
				
		
		</form:form>

	</div>
	
	
	<!--  Los botones de crear y cancelar -->

		<input type="submit" name="save" value="<spring:message code="ccomplaint.save"></spring:message>" />
			
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('complaint/list.do')" />		
</security:authorize>


