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

		<form:form action="complaint/create.do" method="POST" id="formCreate" name="formCreate" modelAttribute="complaint">

			<!-- Atributos hidden-->
			
			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="referee" />

			
			
			
				<!-- Form -->
				
				<form:label path="moment"><spring:message code="complaint.moment"></spring:message></form:label>
				<form:input path="moment" id="moment" name="moment" />
				<form:errors cssClass="error" path="moment" />
				<br>
				
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
							
				<form:label path="fixuptask"><spring:message code="complaint.fixuptask"></spring:message></form:label>
				<form:select id="fixuptask" path="fixuptask">
				<form:option value="${FIXUPTASK}" label="------"></form:option>
				<form:options items="${FIXUPTASK}" itemLabel="name" itemValue="id" />
				</form:select>
				<form:errors cssClass="error" path="fixuptask" />
				
				
<%-- 				<form:label path="report"><spring:message code="complaint.report"></spring:message></form:label>
				<form:select id="report" path="report">
				<form:option value="${REPORT}" label=""></form:option>
				<form:options items="${REPORT}" itemLabel="name" itemValue="id" />
				</form:select>
				<form:errors cssClass="error" path="report" /> --%>
				
		
		</form:form>

	</div>
	
	
	<!--  Los botones de crear y cancelar -->

		<input type="submit" name="save" value="<spring:message code="complaint.save"></spring:message>" />
			
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('complaint/list.do')" />		
</security:authorize>


