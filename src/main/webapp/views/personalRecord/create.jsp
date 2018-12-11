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

		<form:form action="personalRecord/create.do" method="POST"
			id="formCreate" name="formCreate" modelAttribute="personalRecord">

			<!-- Atributos hidden-->

			<form:hidden path="id" />
			<form:hidden path="version" />
			

			<fieldset>
				<!-------------------Form ------------------------------------>
				<div>
					<form:label path="fullName">
						<spring:message code="personalRecord.fullName"></spring:message>
					</form:label>
					<form:input path="fullName" id="fullName" name="fullName" />
					<form:errors cssClass="error" path="fullName" />
					<br />
				</div>


				<div>
					<form:label path="photo">
						<spring:message code="personalRecord.photo"></spring:message>
					</form:label>
					<form:input path="photo" id="photo" name="photo" />
					<form:errors cssClass="error" path="photo" />
					<br />
				</div>
					
				<div>
					<form:label path="email">
						<spring:message code="personalRecord.email"></spring:message>
					</form:label>
					<form:input path="email" id="email" name="email"
						placeholder="user@email.com" />
					<form:errors cssClass="error" path="email" />
					<br />
				</div>

				<div>
					<form:label path="phone">
						<spring:message code="personalRecord.phone"></spring:message>
					</form:label>
					<form:input path="phone" id="phone" name="phone"
						placeholder="600000000" />
					<form:errors cssClass="error" path="phone" />
					<br />
				</div>

				<div>
					<form:label path="linkedInProfile">
						<spring:message code="personalRecord.linkedInProfile"></spring:message>
					</form:label>
					<form:input path="linkedInProfile" id="linkedInProfile" name="linkedInProfile" />
					<form:errors cssClass="error" path="linkedInProfile" />
					<br />
				</div>
				



			</fieldset>



		</form:form>

	</div>
	
	<!--  Botones -->
	
		<input type="submit" name="save" value="<spring:message code="personalRecord.save"></spring:message>" />	
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('personalRecord/display.do')" />	




</security:authorize>