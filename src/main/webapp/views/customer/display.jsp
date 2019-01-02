<%--
 * action-1.jsp
 *
 * Copyright (C) 2018 Universidad de Sevilla
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
	<spring:message code="customer.display" />
</p>

<%-- Si esto da error creo que tambien se puede poner así: access="hasRole('ADMIN') OR hasRole('CUSTOMER')" --%>
<security:authorize access="hasAnyRole('ADMIN', 'CUSTOMER')">

	<b><spring:message code="customer.name"></spring:message>:</b>
	<jstl:out value="${customer.name}"></jstl:out>
	<br />

	<b><spring:message code="customer.middleName"></spring:message>:</b>
	<jstl:out value="${customer.middleName}"></jstl:out>
	<br />

	<b><spring:message code="customer.surname"></spring:message>:</b>
	<jstl:out value="${customer.surname}"></jstl:out>
	<br />

	<b><spring:message code="customer.photo"></spring:message>:</b>
	<jstl:out value="${customer.photo}"></jstl:out>
	<br />

	<b><spring:message code="customer.email"></spring:message>:</b>
	<jstl:out value="${customer.email}"></jstl:out>
	<br />

	<b><spring:message code="customer.phone"></spring:message>:</b>
	<jstl:out value="${customer.phone}"></jstl:out>
	<br />

	<b><spring:message code="customer.address"></spring:message>:</b>
	<jstl:out value="${customer.address}"></jstl:out>
	<br />

	<security:authorize access="hasRole('ADMIN')">
	<b>	<spring:message code="customer.score"></spring:message>:</b>
		<jstl:out value="${customer.score}"></jstl:out>
		<br />
	</security:authorize>

	<fieldset>
		<legend>
			<b><spring:message code="customer.socialProfiles"></spring:message></b>
		</legend>
		<display:table name="socialProfiles" id="socialProfile"
			requestURI="${requestURI}" pagesize="5" class="displaytag">

		<b>	<spring:message code="socialProfile.networkName"
				var="socialProfileNetworkName"></spring:message>:</b>
			<display:column property="networkName"
				title="${socialProfile.networkName}" sortable="true" />

			<b><spring:message code="socialProfile.nick" var="socialProfileNick"></spring:message>:</b>
			<display:column property="nick" title="${social.profileNick}"
				sortable="true" />

			<b><spring:message code="socialProfile.profile"
				var="socialProfileNetworkProfile"></spring:message>:</b>
			<display:column property="profile"
				title="${socialProfile.networkProfile}" sortable="true" />

		</display:table>
	</fieldset>

	<spring:message code="customer.edit" var="edit"></spring:message>
	<input type="button" name="edit" value="${edit}"
		onclick="javascript:relativeRedir('customer/edit.do?customerId=${customer.id}')" />

	<%-- <spring:message code="customer.return" var="return"></spring:message>
	<input type="button" name="return" value="${return}"
		onclick="javascript:relativeRedir('customer/administrator/list.do')" /> --%>


</security:authorize>



