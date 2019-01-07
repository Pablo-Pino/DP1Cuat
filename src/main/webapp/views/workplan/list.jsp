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
	name="workplans" requestURI="${requestURI}" id="row">
	
	<!-- Attributos -->

	<spring:message code="workplan.fixupTask" var="fixupTask" />
	<display:column title="${fixupTask}" property="fixupTask.ticker" >
		
	</display:column>
	<spring:message code="workplan.handyWorker" var="handyWorker" />
	<display:column property="handyWorker.name" title="${handyWorker}" sortable="false" />
	
	<spring:message code="workplan.sections" var="worksec" />
	<display:column title="${worksec}">
		<jstl:forEach items="${Workplan.sections}" var="workplanSections">
			<a href="section/handyworker/list.do?sectionId= ${workplanSections.id}"> <jstl:out value="workplan.section"></jstl:out></a>
		</jstl:forEach>
	</display:column>
	

</display:table>

<!-- Botones  -->

<security:authorize access="hasRole('HANDYWORKER')">
	<div>
		<a href="workplan/handyWorker/create.do"> <spring:message
				code="workplan.create" />
		</a>
	</div>
</security:authorize>