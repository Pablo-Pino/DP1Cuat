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

<display:table name="socialProfiles" id="socialProfile" requestURI="socialProfile/list.do?actorId=${actorId}" pagesize="5" >

	<spring:message code="socialprofile.nick" var="nickTitle" />
	<display:column title="${nickTitle}" sortable="true" property="nick" />

	<spring:message code="socialprofile.networkname" var="networkNameTitle" />
	<display:column title="${networkNameTitle}" sortable="true" property="networkName" />

	<spring:message code="socialprofile.profile" var="profileTitle" />
	<display:column title="${profileTitle}" property="profile" />

	<jstl:if test="${isPrincipalAuthorizedEdit}">
		<spring:message code="socialprofile.edit" var="editTitle" />
		<display:column>
			<a href="socialProfile/edit.do?socialProfileId=${socialProfile.id}">${editTitle}</a>
		</display:column>
	</jstl:if>

</display:table>

<spring:message code="socialProfile.create" var="createTitle" />
<button onclick='javascript: relativeRedir("<jstl:out value="socialProfile/create.do?actorId=${actor.id}"></jstl:out>")' >${createTitle}</button>	