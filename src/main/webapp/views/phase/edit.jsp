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
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
<script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>

<script>
	$(function() {
		$("#datepicker1").datepicker({dateFormat: 'dd/mm/yy'});
	});
	$(function() {
		$("#datepicker2").datepicker({dateFormat: 'dd/mm/yy'});
	});
</script>
<security:authorize access="hasRole('HANDYWORKER')">
	<div>
	

		<form:form action="phase/handyWorker/edit.do" method="post"
			id="formCreate" name="formCreate" modelAttribute="phase">
<security:authentication property="principal.username" var="username" />
<jstl:if test='${phase.workPlan.handyWorker.userAccount.username == username || phase.id == 0}'>

			<!-- Atributos hidden-->

			<form:hidden path="id" />
			<form:hidden path="version" />
			<form:hidden path="workPlan" />


			<fieldset>
				<!-------------------Form ------------------------------------>
				<div>
					<form:label path="title">
						<spring:message code="phase.title"></spring:message>
					</form:label>
					<form:input path="title" id="title"
						name="title" />
					<form:errors cssClass="error" path="title" />
					<br />
				</div>

				<div>
					<form:label path="start">
						<spring:message code="phase.start"></spring:message>
					</form:label>
					<form:input path="start" id="datepicker1" name="start" />
					<form:errors cssClass="error" path="start" />
					<br />
				</div>

				<div>
					<form:label path="end">
						<spring:message code="phase.end"></spring:message>
					</form:label>
					<form:input path="end" id="datepicker2" name="end" />
					<form:errors cssClass="error" path="end" />
					<br />
				</div>

				<div>
					<form:label path="description">
						<spring:message code="phase.description"></spring:message>
					</form:label>
					<form:input path="description" id="description" name="description" />
					<form:errors cssClass="error" path="description" />
					<br />
				</div>


			</fieldset>


			<!--  Los botones de crear y cancelar -->

			<input type="submit" name="save"
				value="<spring:message code="phase.save"></spring:message>" />
	<jstl:if test="${phase.id != 0}">
			<button type="button"
				onclick="javascript: relativeRedir('phase/handyWorker/list.do?workplanId=${phase.workPlan.id}')">
				<spring:message code="phase.cancel" />
			</button>

			
				<input type="submit" name="delete"
					value="<spring:message code="phase.delete" />"
					onclick="return confirm('<spring:message code="phase.confirm.delete" />')" />&nbsp;
	</jstl:if>





</jstl:if>

		</form:form>

	</div>


<jstl:if test='${phase.workPlan.handyWorker.userAccount.username != username && phase.id != 0}'>
	<h1>
		<b><spring:message code="phase.permissions"></spring:message></b>
	</h1>
</jstl:if>


</security:authorize>