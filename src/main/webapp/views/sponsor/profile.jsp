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

<fieldset><legend><spring:message code="sponsor.personalinformation" /></legend>

	<img src="${sponsor.photo}" />
	<p><spring:message code="sponsor.name" />${sponsor.name}</p>
	<p><spring:message code="sponsor.middlename" />${sponsor.middleName}</p>
	<p><spring:message code="sponsor.surname" />${sponsor.surname}</p>
	<p><spring:message code="sponsor.email" />${sponsor.email}</p>
	<p><spring:message code="sponsor.address" />${sponsor.address}</p>
	<p><spring:message code="sponsor.phone" />${sponsor.phone}</p>

</fieldset>

<jstl:if test="${isPrincipalAuthorizedEdit}">
	<spring:message code="sponsor.edit" var="editMessage" />
	<button onclick='javascript: relativeRedir("<jstl:out value="sponsor/edit.do?sponsorId=${sponsor.id}"></jstl:out>")' >${editMessage}</button>
</jstl:if>

<security:authorize access="hasRole('ADMINISTRATOR')">
	<jstl:choose>
		<jstl:when test="${sponsor.banned}">
			<spring:message code="sponsor.unban" var="unbanMessage" />
			<button onclick='javascript: relativeRedir("<jstl:out value="actor/unban.do?actorId=${sponsor.id}"></jstl:out>")' >${unbanMessage}</button>
		</jstl:when>
		<jstl:otherwise>
			<spring:message code="sponsor.ban" var="banMessage" />
			<button onclick='javascript: relativeRedir("<jstl:out value="actor/ban.do?actorId=${sponsor.id}"></jstl:out>")' >${banMessage}</button>
		</jstl:otherwise>
	</jstl:choose>
</security:authorize>