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

<security:authorize access="hasRole('HANDYWORKER')">
	<div>

		<form:form action="endorserRecord/create.do" method="POST"
			id="formCreate" name="formCreate" modelAttribute="endorserRecord">

			<!-- Atributos hidden-->

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="curriculum" />
			

			<fieldset>
				<!-------------------Form ------------------------------------>
				<div>
					<form:label path="fullName">
						<spring:message code="endorserRecord.fullName"></spring:message>
					</form:label>
					<form:input path="diplomaTitle" id="fullName" name="fullName" />
					<form:errors cssClass="error" path="fullName" />
					<br />
				</div>
				
				<div>
					<form:label path="photo">
						<spring:message code="endorserRecord.photo"></spring:message>
					</form:label>
					<form:input path="photo" id="photo" name="photo" />
					<form:errors cssClass="error" path="photo" />
					<br />
				</div>
				
				<div>
					<form:label path="email">
						<spring:message code="endorserRecord.email"></spring:message>
					</form:label>
					<form:input path="email" id="email" name="email" />
					<form:errors cssClass="error" path="email" />
					<br />
				</div>
				
				<div>
					<form:label path="phone">
						<spring:message code="endorserRecord.phone"></spring:message>
					</form:label>
					<form:input path="phone" id="phone" name="phone" />
					<form:errors cssClass="error" path="phone" />
					<br />
				</div>
				
				<div>
					<form:label path="linkedinProfile">
						<spring:message code="endorserRecord.linkedinProfile"></spring:message>
					</form:label>
					<form:input path="linkedinProfile" id="linkedinProfile" name="linkedinProfile" />
					<form:errors cssClass="error" path="linkedinProfile" />
					<br />
				</div>
				

				
				



			</fieldset>



		</form:form>

	</div>
	
	<!--  Botones -->
	
		<input type="submit" name="save" value="<spring:message code="endorserRecord.save"></spring:message>" />	
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('endorserRecord/display.do')" />	




</security:authorize>