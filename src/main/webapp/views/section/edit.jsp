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

<form:form action="section/edit.do" modelAttribute="section" method="post">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="tutorial" />
	
	<acme:textbox code="section.title" path="title" />
	<acme:textarea code="section.text" path="text" />
	<acme:textbox code="section.numberorder" path="numberOrder" />
	
<fieldset><legend><spring:message code="section.pictures" /></legend>

	<jstl:forEach begin="0" end="${section.pictures.size}" step="1" varStatus="varStatusPictures" >
		<acme:textbox code="section.picture" path="pictures[${varStatusPictures.index}]" />
		<acme:button code="section.picture.remove" url="section/removePicture.do?sectionId=${section.id}&url=${section.pictures[varStatusPictures.index]}" />
		<br><img src="${section.pictures[varStatusPictures.index]}" alt="${section.pictures[varStatusPictures.index]}" />
	</jstl:forEach>
	<acme:button url="section/addPicture.do?sectionId=${section.id}" code="section.picture.add" />

</fieldset>

<jstl:if test="${isPrincipalAuthorizedEdit}">	
	<acme:submit code="section.save" name="save" />
	<jstl:if test=${section.id > 0}>
		<acme:submit code="section.delete" name="delete" />
	</jstl:if>
</jstl:if>	
	
</form:form>

<acme:cancel code="section.cancel" url="section/list.do?tutorialId=${tutorial.id}" />