<%--
 * action-1.jsp
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
<%@taglib prefix="acme" tagdir="/WEB-INF/tags"%>

<fieldset><legend><spring:message code="referee.personalinformation" /></legend>

	<img src="${referee.photo}" />
	<p><spring:message code="referee.name" />${referee.name}</p>
	<p><spring:message code="referee.middlename" />${referee.middleName}</p>
	<p><spring:message code="referee.surname" />${referee.surname}</p>
	<p><spring:message code="referee.email" />${referee.email}</p>
	<p><spring:message code="referee.address" />${referee.address}</p>
	<p><spring:message code="referee.phone" />${referee.phone}</p>

</fieldset>

<jstl:if test="${isPrincipalAuthorizedEdit}">
	<acme:button url="referee/edit.do?refereeId=${referee.id}" code="referee.edit" />
</jstl:if>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<jstl:choose>
		<jstl:when test="${referee.banned}">
			<acme:button url="actor/unban.do?actorId=${referee.id}" code="referee.unban" />
		</jstl:when>
		<jstl:otherwise>
			<acme:button url="actor/ban.do?actorId=${referee.id}" code="referee.ban" />
		</jstl:otherwise>
	</jstl:choose>
</security:authorize>