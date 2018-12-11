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
	<spring:message code="folder.edit" />
</p>


	<div>
		<form:form action="folder/referee/edit.do" method="post" id="formCreate"
			name="formCreate" modelAttribute="folder">

			<form:hidden path="id" />
			<form:hidden path="version" />

			<form:label path="name">
				<spring:message code="folder.name"></spring:message>
			</form:label>
			<form:textarea path="name" id="name" name="name" />
			<form:errors cssClass="error" path="name"></form:errors>
			
			<form:label path="system">
				<spring:message code="folder.system"></spring:message>
			</form:label>
			<form:textarea path="system" id="system" name="system" />
			<form:errors cssClass="error" path="system"></form:errors>

			<input type="submit" name="save"
				value="<spring:message code="folder.save"></spring:message>" />
			<spring:message code="folder.cancel" var="cancel"></spring:message>
			<input type="button" name="cancel" value="${cancel}"
				onclick="javascript:relativeRedir('folder/display.do')" />
		</form:form>

	</div>
