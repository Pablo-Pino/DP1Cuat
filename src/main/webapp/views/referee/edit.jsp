<%--
 * action-2.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
 * 
 * The use of this project is hereby constrained to the conditions of the 
 * TDG Licence, a copy of which you may download from 
 * http://www.tdg-seville.info/License.html
 --%>

<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl"	uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security" uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@taglib prefix="acme" tagdir="/WEB-INF/tags" %>

<form:form action="referee/edit" modelAttribute="referee" method="post">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="complaints" />
	
	<fieldset><legend><spring:message code="referee.useraccount" /></legend>
		<acme:textbox code="referee.useraccount.username" path="useraccount.username" />
		<acme:textbox code="referee.useraccount.password" path="useraccount.password" />
	</fieldset>
	
	<acme:textbox code="referee.name" path="name" />
	<acme:textbox code="referee.middleName" path="middleName" />
	<acme:textbox code="referee.surname" path="surname" />
	<acme:textbox code="referee.photo" path="photo" />
	<acme:textbox code="referee.email" path="email" />
	<acme:textbox code="referee.phone" path="phone" />
	<acme:textbox code="referee.address" path="address" />

<jstl:if test=${principal.id eq referee.id}>	
	<acme:submit code="referee.save" name="save" />
	<jstl:if test=${referee.id > 0}>
		<acme:submit code="referee.delete" name="delete" />
	</jstl:if>
</jstl:if>	
	
</form:form>

<acme:cancel code="referee.cancel" url="referee/profile?refereeId=${referee.id}" />