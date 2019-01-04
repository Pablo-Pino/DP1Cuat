<%--
 * edit.jsp
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
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="fixupTask.edit" />
</p>

<security:authorize access="hasRole('CUSTOMER')">
<form:form action="fixupTask/customer/edit.do" method="post" id="formCreate" 
		name="formCreate" modelAttribute="fixupTask">

	<form:hidden path="id" />
	<form:hidden path="version" />
	<form:hidden path="moment" />

	<form:label path="ticker"> <spring:message code="fixupTask.ticker" />:</form:label>
	<form:input path="ticker" /><form:errors cssClass="error" path="ticker" /><br />


	<form:label path="description">
		<spring:message code="fixupTask.description"></spring:message>
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="description"></form:errors>
	<br />
	
	<form:label path="address">
		<spring:message code="fixupTask.address" ></spring:message>
	</form:label>
	<form:input path="address" />
	<form:errors cssClass="error" path="address" ></form:errors>
	<br />
	
	<form:label path="maximumPrice">
		<spring:message code="fixupTask.maximumPrice" >:</spring:message>
	</form:label>
	<form:input path="description" />
	<form:errors cssClass="error" path="maximumPrice" ></form:errors>
	<br />
	
	<form:label path="start">
		<spring:message code="fixupTask.start" >: </spring:message>
	</form:label>
	<form:input path="start" />
	<form:errors cssClass="error" path="start" ></form:errors>
	<br />
	
	<form:label path="end">
		<spring:message code="fixupTask.end" >:</spring:message>
	</form:label>
	<form:input path="end" />
	<form:errors cssClass="error" path="end" /> 
	<br />
	


<!--  Botones -->

<input type="submit" name="save"
	value="<spring:message code="fixupTask.save"></spring:message>" />
<spring:message code="fixupTask.cancel" var="cancel"></spring:message>
<input type="button" name="cancel" value="${cancel}"
	onclick="javascript:relativeRedir('fixupTask/customer/list.do')" />
	
</form:form>
	
</security:authorize>