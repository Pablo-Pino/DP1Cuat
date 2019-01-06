<%--
 * action-1.jsp
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

<p><spring:message code="endorserRecord.display" /></p>

<spring:message code="endorserRecord.fullName"></spring:message>:
<jstl:out value="${endorserRecord.fullName}"></jstl:out>
<br/>

<spring:message code="endorserRecord.photo"></spring:message>:
<jstl:out value="${endorserRecord.photo}"></jstl:out>
<br/>

<spring:message code="endorserRecord.email"></spring:message>:
<jstl:out value="${endorserRecord.email}"></jstl:out>
<br/>

<spring:message code="endorserRecord.phone"></spring:message>:
<jstl:out value="${endorserRecord.phone}"></jstl:out>
<br/>

<spring:message code="endorserRecord.linkedinProfile"></spring:message>:
<jstl:out value="${endorserRecord.linkedinProfile}"></jstl:out>
<br/>




<input type="submit" name="edit" value="<spring:message code="endorserRecord.edit"></spring:message>" />	
<input type="button" name="cancel" value="${cancel}" onclick="javascript:relativeRedir('endorserRecord/display.do')" />	










