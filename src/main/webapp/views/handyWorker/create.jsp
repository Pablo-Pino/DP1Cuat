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


<div>

	<form:form action="handyWorker/create.do" method="POST" id="formCreate"
		name="formCreate" modelAttribute="handyWorker">

		<!-- Atributos hidden-->

		<form:hidden path="id" />
		<form:hidden path="version" />
		<form:hidden path="userAccount" />
		<form:hidden path="banned" />
		<form:hidden path="suspicious" />
		<form:hidden path="senderMessages" />
		<form:hidden path="recipientMessages" />
		<form:hidden path="applications" />
		<form:hidden path="workplan" />
		<form:hidden path="tutorials" />
		<form:hidden path="curriculum" />
		<form:hidden path="folders" />
		<form:hidden path="finder" />
		<form:hidden path="socialProfiles" />

		<fieldset>
			<!-------------------Form ------------------------------------>
			<div>
				<form:label path="name">
					<spring:message code="handyWorker.name"></spring:message>
				</form:label>
				<form:input path="name" id="name" name="name" />
				<form:errors cssClass="error" path="name" />
				<br />
			</div>

			<div>
				<form:label path="middleName">
					<spring:message code="handyWorker.middleName"></spring:message>
				</form:label>
				<form:input path="middleName" id="middleName" name="middleName" />
				<form:errors cssClass="error" path="middleName" />
				<br />
			</div>

			<div>
				<form:label path="surname">
					<spring:message code="handyWorker.surname"></spring:message>
				</form:label>
				<form:input path="surname" id="surname" name="surname" />
				<form:errors cssClass="error" path="surname" />
				<br />
			</div>

			<div>
				<form:label path="photo">
					<spring:message code="handyWorker.photo"></spring:message>
				</form:label>
				<form:input path="photo" id="photo" name="photo" />
				<form:errors cssClass="error" path="photo" />
				<br />
			</div>

			<div>
				<form:label path="email">
					<spring:message code="handyWorker.email"></spring:message>
				</form:label>
				<form:input path="email" id="email" name="email"
					placeholder="user@email.com" />
				<form:errors cssClass="error" path="email" />
				<br />
			</div>

			<div>
				<form:label path="phone">
					<spring:message code="handyWorker.phone"></spring:message>
				</form:label>
				<form:input path="phone" id="phone" name="phone"
					placeholder="600000000" />
				<form:errors cssClass="error" path="phone" />
				<br />
			</div>

			<div>
				<form:label path="address">
					<spring:message code="handyWorker.address"></spring:message>
				</form:label>
				<form:input path="address" id="address" name="address" />
				<form:errors cssClass="error" path="address" />
				<br />
			</div>

			<div>
				<form:label path="make">
					<spring:message code="handyWorker.make"></spring:message>
				</form:label>
				<form:input path="make" id="make" name="make" />
				<form:errors cssClass="error" path="make" />
				<br />
			</div>


			<!-- UserAccount  -->
			<div>
				<form:label path="userAccount.username">
					<spring:message code="userAccount.username" />:
				</form:label>
				<form:input path="userAccount.username" />
				<form:errors cssClass="error" path="userAccount.username" />
				<br />
			</div>

			<div>
				<form:label path="userAccount.password">
					<spring:message code="userAccount.password" />:
				</form:label>
				<form:password path="userAccount.password" />
				<form:errors cssClass="error" path="userAccount.password" />
				<br />
			</div>


		</fieldset>

<!--  Botones -->

<input type="submit" name="save"
	value="<spring:message code="handyWorker.save"></spring:message>" />
<input type="button" name="cancel" value="${cancel}"
	onclick="javascript:relativeRedir('handyWorker/list.do')" />


	</form:form>

</div>
