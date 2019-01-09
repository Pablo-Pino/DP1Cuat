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

<%

Cookie[] cookies = request.getCookies();
Cookie languageCookie = null;
for(Cookie c : cookies) {
	if(c.getName().equals("language")) {
		languageCookie = c;
	}
}

String languageValue = languageCookie.getValue();

%>

<p>
	<spring:message code="category.list" />
</p>


<display:table name="category" id="Category"
	requestURI="category/administrator/list.do" pagesize="5"
	class="displaytag">

	<%--  Primero compruebo que es un admin --%>
	<security:authorize access="hasRole('ADMIN')">


		<%--  La columna que va a la vista edit de las categorias --%>
		<display:column>
<%-- 			<jstl:if test='${not (Category.name eq CATEGORY)} '>
 --%>				<a href="category/administrator/edit.do?categoryId=${Category.id}"><spring:message
						code="category.edit"></spring:message></a>
<%-- 			</jstl:if>
 --%>
		</display:column>



		<%--  La columna que va a la vista edit de las categorias --%>
		<display:column>
			<a href="category/administrator/display.do?categoryId=${Category.id}"><spring:message
					code="category.display"></spring:message></a>
		</display:column>

		<spring:message code="category.name" var="categoryName"></spring:message>
		<display:column title="${categoryName}" sortable="true" >
			<% if(languageValue.equals("en")) { %>
				<jstl:out value="${Category.nameEnglish}" />
			<% } else if (languageValue.equals("es")) { %>
				<jstl:out value="${Category.nameSpanish}" />
			<% } %>
		</display:column>
		
		<spring:message code="category.parentCategory" var="ParentCategory"></spring:message>
		<display:column title="${ParentCategory}" sortable="true" >
			<% if(languageValue.equals("en")) { %>
				<jstl:out value="${Category.parentCategory.nameEnglish}" />
			<% } else if (languageValue.equals("es")) { %>
				<jstl:out value="${Category.parentCategory.nameSpanish}" />
			<% } %>
		</display:column>
		 


		<%-- 	<spring:message code="category.childsCategories" var="catId" />
	<display:column title="${catId}">
		<jstl:forEach items="${Category.childsCategories}" var="childsCategories">
			<a href="category/admin/display.do?categoryId= ${childsCategories.id}"> <jstl:out value="${child.Categoriesname}"></jstl:out></a>
		</jstl:forEach>
	</display:column> --%>


	</security:authorize>
</display:table>


<%--  Boton de creacion --%>
<security:authorize access="hasRole('ADMIN')">
	<a href="category/administrator/create.do"><spring:message
			code="category.create"></spring:message></a>
</security:authorize>


