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

<form:form action="warranty/administrator/edit.do" modelAttribute="warranty">

	<form:hidden path="id" />
	<form:hidden path="version" />

	<form:label path="title"> <spring:message code="warranty.title" />:</form:label>
	<form:input path="title" /><form:errors cssClass="error" path="title" /><br />

	<form:label path="terms">
		<spring:message code="warranty.terms" />:
	</form:label>
	<form:input path="terms" />
	<form:errors cssClass="error" path="terms" />
	<br />
	
	<form:label path="laws">
		<spring:message code="warranty.laws" />:
	</form:label>
	<form:input path="laws" />
	<form:errors cssClass="error" path="laws" />
	<br />
	
	<form:label path="draft">
		<spring:message code="warranty.draft" />:
	</form:label>
	<form:input path="draft" />
	<form:errors cssClass="error" path="draft" />
	<br />

	<input type="submit" name="save"
		value="<spring:message code="warranty.save" />" />&nbsp; 
	<jstl:if test="${warranty.id != 0}">
		<input type="submit" name="delete"
			value="<spring:message code="warranty.delete" />"
	onclick="return confirm('<spring:message code="warranty.confirm.delete" />')" />&nbsp;
	
	</jstl:if>
	<input type="button" name="cancel"
		value="<spring:message code="warranty.cancel" />"
		onclick="javascript: relativeRedir('warranty/administrator/list.do');" />
	<br />

</form:form>
