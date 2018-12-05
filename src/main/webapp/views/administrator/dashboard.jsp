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


<security:authorize access="hasRole('ADMIN')">

	<fieldset>
		<legend>
			<spring:message code="dashboard.fixuptaskMaxStats"></spring:message>
		</legend>
		<display:table name="minMaxFixupTaskStats" id="minMaxFixupTaskStats" pagesize="5" requestURI="${requestURI}" class="displaytag">	
			<!--  Cada comentario muestra una nueva columna dentro del fieldset -->
			<spring:message code="dashboard.FixStatsMax.min" var="dash1" />
				<display:column title="${dash1}" >
						<jstl:out value="${minMaxFixupTaskStats}" />
				</display:column>
			<!--  Cada comentario muestra una nueva columna dentro del fieldset -->
			<spring:message code="dashboard.FixStatsMax.max" var="dash2" />
				<display:column title= "${dash2}" >
						<jstl:out value="${maxMaxFixupTaskStats}" />
				</display:column>
			<!--  Cada comentario muestra una nueva columna dentro del fieldset -->
			<spring:message code="dashboard.FixStatsMax.avg" var="dash3" />
				<display:column title= "${dash3}" >
						<jstl:out value="${avgFixupTaskStats}" />
				</display:column>
				
			<!--  Cada comentario muestra una nueva columna dentro del fieldset -->
			<spring:message code="dashboard.FixStatsMax.stdev" var="dash4" />
				<display:column title= "${dash4}" >
						<jstl:out value="${stdevMaxFixupTaskStats}" />
				</display:column>
		</display:table>
	
	</fieldset>
	
	
	
		<fieldset>
		<legend>
			<spring:message code="dashboard.appsPerfixuptask"></spring:message>
		</legend>
		<display:table name="minAppsFixupTaskStats" id="minAppsFixupTaskStats" pagesize="5" requestURI="${requestURI}" class="displaytag">	
			<!--  Cada comentario muestra una nueva columna dentro del fieldset -->
			<spring:message code="dashboard.AppsFixStats.min" var="dash5" />
				<display:column title="${dash5}" >
						<jstl:out value="${minAppsFixupTaskStats}" />
				</display:column>
			<!--  Cada comentario muestra una nueva columna dentro del fieldset -->
			<spring:message code="dashboard.AppsFixStats.max" var="dash6" />
				<display:column title= "${dash6}" >
						<jstl:out value="${maxAppsFixupTaskStats}" />
				</display:column>
			<!--  Cada comentario muestra una nueva columna dentro del fieldset -->
			<spring:message code="dashboard.AppsFixStats.avg" var="dash7" />
				<display:column title= "${dash7}" >
						<jstl:out value="${avgAppsFixupTaskStats}" />
				</display:column>
				
			<!--  Cada comentario muestra una nueva columna dentro del fieldset -->
			<spring:message code="dashboard.AppsFixStats.stdev" var="dash8" />
				<display:column title= "${dash8}" >
						<jstl:out value="${stdevAppsFixupTaskStats}" />
				</display:column>
		</display:table>
	
	</fieldset>


	<fieldset>
		<legend>
			<spring:message code="dashboard.fixuptaskPriceStats"></spring:message>
		</legend>
		<display:table name="minFixupTaskPriceStats" id="minFixupTaskPriceStats" pagesize="5" requestURI="${requestURI}" class="displaytag">	
			<!--  Cada comentario muestra una nueva columna dentro del fieldset -->
			<spring:message code="dashboard.fixuptaskPriceStats.min" var="dash9" />
				<display:column title="${dash9}" >
						<jstl:out value="${minFixupTaskPriceStats}" />
				</display:column>
			<!--  Cada comentario muestra una nueva columna dentro del fieldset -->
			<spring:message code="dashboard.fixuptaskPriceStats.max" var="dash10" />
				<display:column title= "${dash10}" >
						<jstl:out value="${maxFixupTaskPriceStats}" />
				</display:column>
			<!--  Cada comentario muestra una nueva columna dentro del fieldset -->
			<spring:message code="dashboard.fixuptaskPriceStats.avg" var="dash11" />
				<display:column title= "${dash11}" >
						<jstl:out value="${avgFixupTaskPriceStats}" />
				</display:column>
				
			<!--  Cada comentario muestra una nueva columna dentro del fieldset -->
			<spring:message code="dashboard.fixuptaskPriceStats.stdev" var="dash12" />
				<display:column title= "${dash12}" >
						<jstl:out value="${stdevMaxFixupTaskPriceStats}" />
				</display:column>
		</display:table>
	
	</fieldset>
	
	
	<!--                 Ahora vamos a ver los ratios de las aplicaciones             -->
	<!-- --------------------------------------------------------------------------------------------- -->
	
		<fieldset>
		<legend>
			<spring:message code="dashboard.ratioPending"> </spring:message>
		</legend>
		
		<display:table name="appsPendingRatio" id="appsPendingRatio" pagesize="5" requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.appsPendingRatio" var="dash13" />
			<display:column title="${dash13}"> <jstl:out value="${appsPendingRatio}" /> </display:column>
		</display:table>
	</fieldset>
	
		<fieldset>
		<legend>
			<spring:message code="dashboard.ratioAccepted"> </spring:message>
		</legend>
		
		<display:table name="appsAcceptedRatio" id="appsAcceptedRatio" pagesize="5" requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.appsAcceptedRatio" var="dash14" />
			<display:column title="${dash14}"> <jstl:out value="${appsAcceptedRatio}" /> </display:column>
		</display:table>
	</fieldset>
	
	<fieldset>
		<legend>
			<spring:message code="dashboard.ratioRejected"> </spring:message>
		</legend>
		
		<display:table name="appsRejectedRatio" id="appsRejectedRatio" pagesize="5" requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.appsRejectedRatio" var="dash15" />
			<display:column title="${dash15}"> <jstl:out value="${appsRejectedRatio}" /> </display:column>
		</display:table>
	</fieldset>

<!-- ----------------------------------------------------------------------------------------- -->

		<fieldset>
		<legend>
			<spring:message code="dashboard.ratioAppsElapsed"> </spring:message>
		</legend>
		
		<display:table name="appsRatioElapsed" id="appsRatioElapsed" pagesize="5" requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.appsRatioElapsed" var="dash16" />
			<display:column title="${dash16}"> <jstl:out value="${appsRatioElapsed}" /> </display:column>
		</display:table>
	</fieldset>
	
<!-- --------------------------------------------------------------------------------------------- -->

	
	<fieldset>
		<legend>
			<spring:message code="dashboard.customerMost"></spring:message>
		</legend>
		<display:table name="customerMostFix" id="customerMostFix" pagesize="5" requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.customerMostFix" var="dash17" />
			<display:column title="${dash17}">
				<jstl:out value="${customerMostFix.name}" />
			</display:column>
		</display:table>
	</fieldset>
	
	<fieldset>
		<legend>
			<spring:message code="dashboard.handyWorkerMost"></spring:message>
		</legend>
		<display:table name="customerMostFix" id="customerMostFix" pagesize="5" requestURI="${requestURI}" class="displaytag">
			<spring:message code="dashboard.customerMostFix" var="dash18" />
			<display:column title="${dash18}">
				<jstl:out value="${customerMostFix.name}" />
			</display:column>
		</display:table>
	</fieldset>







</security:authorize>
