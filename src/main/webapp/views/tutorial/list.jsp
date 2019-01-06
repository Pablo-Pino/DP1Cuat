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


<p>
	<spring:message code="tutorial.list" />
</p>

<!-- Listing grid -->

<display:table pagesize="5" class="displaytag" keepStatus="true"
	name="tutorials" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->

	<security:authorize access="hasRole('HANDYWORKER')">
		<display:column>
			<a href="tutorial/handyWorker/edit.do?tutorialId=${row.id}">
				<spring:message	code="tutorial.edit" />
			</a>
		</display:column>		
	</security:authorize>
	
	<!-- Attributos -->
	

	<spring:message code="tutorial.title" var="title" />
	<display:column property="title" title="${title}" sortable="true"  />
	
	<spring:message code="tutorial.moment" var="moment" />
	<display:column property="moment" title="${moment}" sortable="true" format="{0,date,dd/MM/yyyy HH:mm}" />

	<spring:message code="tutorial.summary" var="summary" />
	<display:column property="summary" title="${summary}" sortable="false" />
	
	<spring:message code="tutorial.pictures" var="pictures" />
	<display:column property="pictures" title="${pictures}" sortable="false" />
	
	<spring:message code="tutorial.sections" var="tutsec" />
	<display:column title="${tutsec}">
		<jstl:forEach items="${tutorial.sections}" var="tutorialSections">
			<a href="tutorial/handyworker/list.do?sectionId= ${tutorialSections.id}"> <jstl:out value="tutorial.section"></jstl:out></a>
		</jstl:forEach>
	</display:column>
	

</display:table>

<!-- Boton crear?  -->

<%-- <security:authorize access="hasRole('HANDYWORKER')"> --%>
<!-- 	<div> -->
<%-- 		<a href="tutorial/handyWorker/create.do"> <spring:message --%>
<%-- 				code="tutorial.create" /> --%>
<!-- 		</a> -->
<!-- 	</div> -->
<%-- </security:authorize> --%>