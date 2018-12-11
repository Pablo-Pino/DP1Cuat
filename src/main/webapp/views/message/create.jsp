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


	<div>
		<form:form action="message/create.do" method="POST"
			id="formCreate" name="formCreate" modelAttribute="message">

			<!-- Atributos hidden-->

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="moment" />

			

			<fieldset>
				<!-------------------Form ------------------------------------>

				<div>
					<form:label path="subject">
						<spring:message code="message.subject"></spring:message>
					</form:label>
					<form:input path="subject" id="subject" name="subject" />
					<form:errors cssClass="error" path="subject" />
					<br />
				</div>
				
				<div>
					<form:label path="body">
						<spring:message code="message.body"></spring:message>
					</form:label>
					<form:input path="body" id="body" name="body" />
					<form:errors cssClass="error" path="body" />
					<br />
				</div>
				
				<div>
					<form:label path="priority">
						<spring:message code="message.priority"></spring:message>
					</form:label>
					<form:input path="priority" id="priority" name="priority" />
					<form:errors cssClass="error" path="priority" />
					<br />
				</div>
				
				<div>
					<form:label path="tags">
						<spring:message code="message.tags"></spring:message>
					</form:label>
					<form:input path="tags" id="tags" name="tags" />
					<form:errors cssClass="error" path="tags" />
					<br />
				</div>
				
				
				

			</fieldset>



		</form:form>

	</div>
	
	<!--  Botones -->
	
		<input type="submit" name="save" value="<spring:message code="message.save"></spring:message>" />	
		<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('message/display.do')" />	


