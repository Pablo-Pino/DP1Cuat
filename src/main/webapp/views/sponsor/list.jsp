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

<display:table pagesize="5" class="displaytag" keepStatus="true" name="sponsors" requestURI="${requestURI}" id="row">
	
	<!-- Action links -->

	
	<!-- Attributos -->
	
	<display:table name="sponsors" id="sponsor" requestURI="${requestURI}" pagesize="5" class="displaytag">
	
		<spring:message code="sponsor.name" var="sponsorName"></spring:message>
		<display:column property="name" title="${sponsorName}" sortable="true" />
		
		<spring:message code="sponsor.middleName" var="sponsorMiddleName"></spring:message>
		<display:column property="middleName" title="${sponsorMiddleName}" sortable="true" />
		
		<spring:message code="sponsor.surName" var="sponsorSurName"></spring:message>
		<display:column property="surName" title="${sponsorSurName}" sortable="true" />
		
		<spring:message code="sponsor.email" var="sponsorEmail"></spring:message>
		<display:column property="email" title="${sponsorEmail}" sortable="true" />
		
		<spring:message code="sponsor.phone" var="sponsorPhone"></spring:message>
		<display:column property="phone" title="${sponsorPhone}" sortable="true" />
		
		<spring:message code="sponsor.sponsorships" var="sponsorships" />
	<display:column title="${sponsorships}">
		<jstl:forEach items="${Sponsor.sponsorships}" var="sponsorSponsorships">
			<a href="sponsorship/sponsor/list.do?sponsorId= ${sponsorSponsorships.id}"> <jstl:out value="sponsor.sponsorship"></jstl:out></a>
		</jstl:forEach>
	</display:column>
	</display:table>
</display:table>

<!-- Botones  -->
