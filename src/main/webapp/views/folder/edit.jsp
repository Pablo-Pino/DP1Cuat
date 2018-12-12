
<%@page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<form:form action="folder/actor/edit.do" modelAttribute="folder">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="system"/>
	<form:hidden path="folders"/>
	<form:hidden path="messages"/>
	
	<div>
	<form:label path="name">
		<spring:message code="folder.name"/>
	</form:label>
	</div>
	<form:input path="name" id="name" name="name"/>
	
	
	<div>
		<spring:message code="folder.save" var="saveHeader"></spring:message>
		<input type="submit" name="save" value="${saveHeader}"/>
		<spring:message code="folder.delete" var="deleteHeader"></spring:message>
		<input type="submit" name="delete" value="${deleteHeader}" />
		<spring:message code="folder.cancel" var="cancelHeader"></spring:message>
		<input type="button" name="cancel" value="${cancelHeader}"
			onClick="javascript:relativeRedir('folder/actor/list.do');" />
	</div>
</form:form>
