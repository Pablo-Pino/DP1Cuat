<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>

<display:table name="folders" id="row" requestURI="${requestURI}"
	pagesize="5" class="displaytag">


	<display:column titleKey="${folder.edit}">
	<jstl:if test="${row.systemFolder == false}">
		<a href="folder/actor/edit.do?folderId=${row.id}">
			<spring:message code="folder.edit" />
		</a>
	</jstl:if>
	</display:column>
	

	<display:column property="name" titleKey="folder.name" sortable="false"></display:column>

	

	<display:column titleKey="folder.folders">
		<jstl:forEach items="${row.folders}" var="fol">
			<a href="folder/actor/display.do?folderId=${fol.id}"> <jstl:out
					value="${fol.name}"></jstl:out>
			</a>
			<jstl:if
				test="${(fn:length(row.folders)>1)
			&&
			(!((row.folders[fn:length(row.folders)-1])==(fol)))}">
			,
			</jstl:if>
		</jstl:forEach>
		
	</display:column>

	
	<display:column titleKey="${folder.messages}">
		<jstl:forEach items="${row.messages}" var="men">
		<a href="message/actor/display.do?mensajeId=${men.id}">
		<jstl:out value="${men.subject}"></jstl:out>
		</a>
		</jstl:forEach>
	</display:column>
</display:table>
<a href="folder/actor/create.do"> <spring:message
		code="folder.create"></spring:message></a>