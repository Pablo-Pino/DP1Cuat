<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<%@taglib prefix="jstl" uri="http://java.sun.com/jsp/jstl/core"%>
<%@taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@taglib prefix="tiles" uri="http://tiles.apache.org/tags-tiles"%>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@taglib prefix="security"
	uri="http://www.springframework.org/security/tags"%>
<%@taglib prefix="display" uri="http://displaytag.sf.net"%>

<fieldset>
<spring:message code="fixupTask.ticker"></spring:message>:
<jstl:out value="${ft.ticker}"></jstl:out>
</fieldset>

<fieldset>
<spring:message code="fixupTask.moment"></spring:message>:
<jstl:out value="${ft.moment}"></jstl:out>
</fieldset>

<fieldset>
<spring:message code="fixupTask.description"></spring:message>:
<jstl:out value="${ft.description}"></jstl:out>
</fieldset>

<fieldset>
<spring:message code="fixupTask.address"></spring:message>:
<jstl:out value="${ft.address}"></jstl:out>
</fieldset>

<fieldset>
<spring:message code="fixupTask.maximunPrice"></spring:message>:
<jstl:out value="${ft.description}"></jstl:out>
</fieldset>

<fieldset>
<spring:message code="fixupTask.start"></spring:message>:
<jstl:out value="${ft.description}"></jstl:out>
</fieldset>

<fieldset>
<spring:message code="fixupTask.end"></spring:message>:
<jstl:out value="${ft.description}"></jstl:out>
</fieldset>


	


<spring:message code="fixupTask.cancel" var="cancelHeader"></spring:message>
			<input type="button" name="cancel" value="${cancelHeader}"
				onclick="javascript:relativeRedir('fixupTask/customer/list.do')" />
