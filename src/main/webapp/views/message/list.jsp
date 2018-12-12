<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<display:table name="messages" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">

	<display:column>
		<a href="message/actor/edit.do?mensajeId=${row.id}">
			<spring:message code="message.edit" />
		</a>
	</display:column>

	<spring:message code="message.date" var="date"></spring:message>
	<display:column property="moment" titleKey="message.moment" sortable="true"
		format="{0,date, ${date}}"></display:column>

	
	<display:column property="subject" titleKey="${subject}" sortable="true"></display:column>


	<display:column property="body" titleKey="message.subject" sortable="false"></display:column>

	
	<display:column property="priority" titleKey="message.priority" sortable="true"></display:column>
	
	
	<display:column property="tags" titleKey="message.tags" sortable="true"></display:column>


	<display:column>
		<a href="folder/actor/display.do?folderId=${row.folder.id}">
		<jstl:out value="${row.folder.name}"></jstl:out>
		</a>
	</display:column>

	
	<display:column property="sender.name" titleKey="message.sender" sortable="true"></display:column>

	<display:column property="receiver.name" titleKey="message.receiver" sortable="true"></display:column>
</display:table>