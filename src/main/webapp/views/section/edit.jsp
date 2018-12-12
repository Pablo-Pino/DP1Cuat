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

<form:form action="section/edit.do" modelAttribute="section" method="post">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="tutorial" />
	
	<div>
		<form:label path="title">
			<spring:message code="section.title" />
		</form:label>	
		<form:input path="title" />	
		<form:errors path="title" cssClass="error" />
	</div>

	<div class="form-group">
		<form:label path="text">
			<spring:message code="section.text" />
		</form:label>	
		<form:textarea path="text" />	
		<form:errors path="text" cssClass="error" />
	</div>

	<div>
		<form:label path="numberOrder">
			<spring:message code="section.numberorder" />
		</form:label>	
		<form:input path="numberOrder" />	
		<form:errors path="numberOrder" cssClass="error" />
	</div>
	
<fieldset><legend><spring:message code="section.pictures" /></legend>

	<jstl:forEach begin="0" end="${section.pictures.size}" step="1" varStatus="varStatusPictures" >
		<div>
			<form:label path="pictures[${varStatusPictures.index}]">
				<spring:message code="section.picture" />
			</form:label>	
			<form:input path="pictures[${varStatusPictures.index}]" />	
			<form:errors path="pictures[${varStatusPictures.index}]" cssClass="error" />
		</div>
		
		<spring:message code="section.picture.remove" var="removePictureTitle" />
		<button onclick='javascript: relativeRedir("<jstl:out value="section/removePicture.do?sectionId=${section.id}&url=${section.pictures[varStatusPictures.index]}"></jstl:out>")' >${removePictureTitle}</button>
	</jstl:forEach>

	<spring:message code="section.picture.add" var="addPictureTitle" />
	<button onclick='javascript: relativeRedir("<jstl:out value="section/addPicture.do?sectionId=${section.id}"></jstl:out>")' >${addPictureTitle}</button>

</fieldset>

<jstl:if test="${isPrincipalAuthorizedEdit}">	
	<button type="submit" name="save" class="btn btn-primary">
		<spring:message code="section.save" />
	</button>
	<jstl:if test=${section.id > 0}>
		<button type="submit" name="delete" class="btn btn-primary">
			<spring:message code="section.delete" />
		</button>
	</jstl:if>
</jstl:if>	
	
</form:form>

<button type="button" onclick="javascript: relativeRedir('section/list.do?tutorialId=${tutorial.id}')" >
	<spring:message code="section.cancel" />
</button>