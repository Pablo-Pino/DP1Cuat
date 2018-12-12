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

<display:table name="socialProfiles" id="socialProfile" requestURI="socialProfile/list.do?actorId=${actorId}" pagesize="5" >

	<acme:column code="socialprofile.nick" value="nick" sortable="true"/>
	<acme:column code="socialprofile.networkname" value="networkName" sortable="true" />
	<acme:column code="socialprofile.profile" value="profile" />
	<jstl:if test="${isPrincipalAuthorizedEdit}">
		<acme:columnButton codeButton="socialprofile.edit" url="socialProfile/edit.do?socialProfileId=${socialProfile.id}" />
	</jstl:if>

</display:table>