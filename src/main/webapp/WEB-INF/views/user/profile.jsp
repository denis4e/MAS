</html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!-- header-->
<spring:message code="user.profile.page.title" var="title"/>
<tag:headWrapper pageTitle="${title}"/>

<html>
<body>

<!-- Top Menu-->
<c:import url="../templates/topNavigationMenu.jsp"/>

<!-- Login Page-->
<c:import url="../templates/login.jsp"/>

<!-- Messages-->
<tag:messages successMessage="${successMessage}" infoMessage="${infoMessage}" errorMessage="${errorMessage}"/>

<!-- Page Content -->
<div class="container">
    <div class="row">
            <spring:message code="user.profile.login"/>
            <spring:message code="user.profile.email"/>
            <spring:message code="user.profile.phone"/>
        
            ${user.login}
            ${user.password}
            ${user.email}
            ${user.phone}
    </div>
</div>

<!-- Bootstrap core JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>