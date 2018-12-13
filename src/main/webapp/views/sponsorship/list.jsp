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
	name="sponsorships" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->

	<security:authorize access="hasRole('HANDYWORKER')">
		<display:column>
			<a href="sponsorship/handyWorker/edit.do?sponsorshipId=${row.id}">
				<spring:message	code="sponsorship.edit" />
			</a>
		</display:column>		
	</security:authorize>
	
	<!-- Attributos -->
	
	<spring:message code="sponsorship.ticker" var="ticker" />
	<display:column property="ticker" title="${ticker}" sortable="true" />

	<spring:message code="sponsorship.moment" var="moment" />
	<display:column property="moment" title="${moment}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="sponsorship.summary" var="summary" />
	<display:column property="summary" title="${summary}" sortable="false" />
	
	<spring:message code="sponsorship.pictures" var="pictures" />
	<display:column property="pictures" title="${pictures}" sortable="false" />
	
	<spring:message code="sponsorship.sections" var="tutsec" />
	<display:column title="${tutsec}">
		<jstl:forEach items="${Tutorial.sections}" var="sponsorshipSections">
			<a href="sponsorship/handyworker/list.do?sectionId= ${sponsorshipSections.id}"> <jstl:out value="sponsorship.section"></jstl:out></a>
		</jstl:forEach>
	</display:column>
	

</display:table>

<!-- Botones  -->

<security:authorize access="hasRole('HANDYWORKER')">
	<div>
		<a href="sponsorship/handyWorker/create.do"> <spring:message
				code="sponsorship.create" />
		</a>
	</div>
</security:authorize>