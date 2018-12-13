<%--
 * edit.jsp
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

<form:form action="workplan/customer/edit.do" modelAttribute="workplan">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="handyWorker" />
	<form:hidden path="sections" />
	<form:hidden path="sponsorships" />

	<form:label path="title"> <spring:message code="workplan.title" />:</form:label>
	<form:input path="title" /><form:errors cssClass="error" path="title" /><br />

	<form:label path="summary">
		<spring:message code="workplan.summary" />:
	</form:label>
	<form:input path="summary" />
	<form:errors cssClass="error" path="summary" />
	<br />
	
	<form:label path="pictures">
		<spring:message code="workplan.pictures" />:
	</form:label>
	<form:input path="pictures" />
	<form:errors cssClass="error" path="pictures" />
	<br />
	

	<input type="submit" name="save"
		value="<spring:message code="workplan" />" />&nbsp; 
	<jstl:if test="${workplan.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="workplan.delete" />"
			onclick="return confirm('<spring:message code="workplan.confirm.delete" />')" />&nbsp;
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="workplan.cancel" />"
		onclick="javascript: relativeRedir('workplan/handyWorker/list.do');" />
	<br />

</form:form>
