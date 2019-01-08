<%--
 * action-1.jsp
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
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>



<div>
	<form:form action="endorsement/edit.do" method="post" id="formCreate"
		name="formCreate" modelAttribute="endorsement">

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="sender" />
		<form:hidden path="moment" />


		<form:label path="receiver">
			<spring:message code="endorsement.receiver" />:
			</form:label>
		<form:select id="receivers" path="endorsable">
			<form:option value="0" label="----" />
			<form:options items="${receivers}" itemValue="id" itemLabel="title" />
		</form:select>
		<form:errors cssClass="error" path="receivers" />



		<form:label path="comments">
			<spring:message code="endorsement.comments" />
		</form:label>
		<form:input path="comments" />
		<form:errors cssClass="error" path="comments" />
		<br />


	</form:form>




</div>
<!--  Botones -->

<input type="submit" name="save"
	value="<spring:message code="endorsement.save"></spring:message>" />
<input type="button" name="cancel"
	value="$<spring:message code="endorsement.cancel"></spring:message>"
	onclick="javascript:relativeRedir('endorsement//list.do')" />

