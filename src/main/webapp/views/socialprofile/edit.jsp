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

<form:form action="socialProfile/edit.do" modelAttribute="socialProfile" method="post">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="actor" />
	
	<acme:textbox code="socialprofile.nick" path="nick" />
	<acme:textarea code="socialprofile.networkname" path="networkName" />
	<acme:textbox code="socialprofile.profile" path="profile" />

<jstl:if test="${isPrincipalAuthorizedEdit}">	
	<acme:submit code="socialprofile.save" name="save" />
	<jstl:if test=${socialProfile.id > 0}>
		<acme:submit code="socialprofile.delete" name="delete" />
	</jstl:if>
</jstl:if>	
	
</form:form>

<acme:cancel code="socialProfile.cancel" url="socialProfile/list.do?actorId=${socialProfile.actor.id}" />