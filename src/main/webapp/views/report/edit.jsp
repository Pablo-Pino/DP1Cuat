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

<form:form action="report/edit.do" modelAttribute="report" method="post">
	
	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />
	<form:hidden path="complaint" />
		
	<div>
		<form:label path="description">
			<spring:message code="report.description" />
		</form:label>	
		<form:input path="description" />	
		<form:errors path="description" cssClass="error" />
	</div>
	
	<fieldset><legend><spring:message code="report.attachments" /></legend>
		<jstl:forEach begin="0" end="${report.attachments.size}" step="1" varStatus="varStatusAttachments" >
			<div>
				<form:label path="attachments[${varStatusAttachments.index}]">
					<spring:message code="report.attachment" />
				</form:label>	
				<form:input path="attachments[${varStatusAttachments.index}]" />	
				<form:errors path="attachments[${varStatusAttachments.index}]" cssClass="error" />
			</div>
			
			<spring:message code="report.attachment.remove" var="removeAttachmentTitle" />
			<button onclick='javascript: relativeRedir("<jstl:out value="report/removeAttachment.do?reportId=${report.id}&url=${report.attachments[varStatusAttachments.index]}"></jstl:out>")' >${removeAttachmentTitle}</button>
		</jstl:forEach>
	
		<spring:message code="report.attachment.add" var="addAttachmentTitle" />
		<button onclick='javascript: relativeRedir("<jstl:out value="report/addAttachment.do?reportId=${report.id}"></jstl:out>")' >${addAttachmentTitle}</button>
	</fieldset>
	
	<div>
		<form:label path="draft">
			<spring:message code="report.draft" />
		</form:label>	
		<form:checkbox path="draft" />	
		<form:errors path="draft" cssClass="error" />
	</div>

<jstl:if test="${isPrincipalAuthorizedEdit}">	
	<button type="submit" name="save" class="btn btn-primary">
		<spring:message code="report.save" />
	</button>
</jstl:if>
	
</form:form>

<jstl:choose>
	<jstl:when test="${report.id > 0}">
		<button type="button" onclick="javascript: relativeRedir('report/profile.do?reportId=${report.id}')" >
			<spring:message code="report.cancel" />
		</button>
	</jstl:when>
	<jstl:when test="${report.id == 0}">
		<button type="button" onclick="javascript: relativeRedir('')" >
			<spring:message code="report.cancel" />
		</button>
	</jstl:when>
</jstl:choose>
