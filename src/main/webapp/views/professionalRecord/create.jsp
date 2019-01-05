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
	<spring:message code="professionalRecord.create" />
</p>

<security:authorize access="hasRole('HANDYWORKER')">
	<div>

		<form:form action="professionalRecord/create.do" method="POST"
			id="formCreate" name="formCreate" modelAttribute="professionalRecord">

			<!-- Atributos hidden-->

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="start" />
			<form:hidden path="end" />
			

			<fieldset>
				<!-------------------Form ------------------------------------>
				<div>
					<form:label path="company">
						<spring:message code="professionalRecord.company"></spring:message>
					</form:label>
					<form:input path="company" id="company" name="company" />
					<form:errors cssClass="error" path="company" />
					<br />
				</div>


				<div>
					<form:label path="role">
						<spring:message code="professionalRecord.role"></spring:message>
					</form:label>
					<form:input path="role" id="role" name="role" />
					<form:errors cssClass="error" path="role" />
					<br />
				</div>
				
					<div>
					<form:label path="attachment">
						<spring:message code="professionalRecord.attachment"></spring:message>
					</form:label>
					<form:input path="attachment" id="attachment" name="attachment" />
					<form:errors cssClass="error" path="attachment" />
					<br />
				</div>

				<div>
					<form:label path="comments">
						<spring:message code="professionalRecord.comments"></spring:message>
					</form:label>
					<form:input path="comments" id="comments" name="comments" />
					<form:errors cssClass="error" path="comments" />
					<br />
				</div>
				



			</fieldset>



		</form:form>

	</div>
	
	<!--  Botones -->
	
		<input type="submit" name="save" value="<spring:message code="professionalRecord.save"></spring:message>" />	
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('professionalRecord/display.do')" />	




</security:authorize>