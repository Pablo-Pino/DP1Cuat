<%--
 * create.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<p>
	<spring:message code="message.edit" />
</p>


	<div>
		<form:form action="message/edit.do" method="post" id="formCreate"
			name="formCreate" modelAttribute="message">

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="moment" />

			<form:label path="subject">
				<spring:message code="message.subject"></spring:message>
			</form:label>
			<form:textarea path="subject" id="subject" name="subject" />
			<form:errors cssClass="error" path="subject"></form:errors>
			
				<form:label path="body">
				<spring:message code="message.body"></spring:message>
			</form:label>
			<form:textarea path="body" id="body" name="body" />
			<form:errors cssClass="error" path="body"></form:errors>
			
				<form:label path="priority">
				<spring:message code="message.priority"></spring:message>
			</form:label>
			<form:textarea path="priority" id="priority" name="priority" />
			<form:errors cssClass="error" path="priority"></form:errors>
			
				<form:label path="folder">
				<spring:message code="message.folder"></spring:message>
			</form:label>
			<form:textarea path="folder" id="folder" name="folder" />
			<form:errors cssClass="error" path="folder"></form:errors>
			
				<form:label path="tags">
				<spring:message code="message.tags"></spring:message>
			</form:label>
			<form:textarea path="tags" id="tags" name="tags" />
			<form:errors cssClass="error" path="tags"></form:errors>
			
				<form:label path="receiver">
				<spring:message code="message.receiver"></spring:message>
			</form:label>
			<form:textarea path="receiver" id="receiver" name="receiver" />
			<form:errors cssClass="error" path="receiver"></form:errors>
		

			<input type="submit" name="save"
				value="<spring:message code="message.save"></spring:message>" />
			<spring:message code="message.cancel" var="cancel"></spring:message>
			<input type="button" name="cancel" value="${cancel}"
				onclick="javascript:relativeRedir('message/display.do')" />
		</form:form>

	</div>

