<%--
 * list.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="fixupTasks" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->

	<security:authorize access="hasRole('CUSTOMER')">
		<display:column>
			<a href="fixupTask/customer/edit.do?fixupTaskId=${row.id}">
				<spring:message	code="fixupTask.edit" />
			</a>
		</display:column>		
	</security:authorize>
	
	<!-- Attributos -->
	
	<spring:message code="fixupTask.ticker" var="ticker" />
	<display:column property="ticker" title="${ticker}" sortable="true" />

	<spring:message code="fixupTask.moment" var="moment" />
	<display:column property="moment" title="${moment}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="fixupTask.description" var="description" />
	<display:column property="description" title="${description}" sortable="false" />
	
	<spring:message code="fixupTask.address" var="address" />
	<display:column property="address" title="${address}" sortable="false" />
	
	<spring:message code="fixupTask.maximunPrice" var="maximunPrice" />
	<display:column property="maximunPrice" title="${maximunPrice}" sortable="false" />
	
	<spring:message code="fixupTask.start" var="start" />
	<display:column property="start" title="${start}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	
	<spring:message code="fixupTask.end" var="end" />
	<display:column property="end" title="${end}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />
	

</display:table>

<!-- Botones  -->

<security:authorize access="hasRole('CUSTOMER')">
	<div>
		<a href="fixupTask/customer/create.do"> <spring:message
				code="fixupTask.create" />
		</a>
	</div>
</security:authorize>