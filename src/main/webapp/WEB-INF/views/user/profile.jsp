</html>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <meta name="description" content="">
    <meta name="author" content="">
    <!-- Bootstrap core CSS -->
    <link href="<c:url value="/resources/css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="/resources/css/custom/loginStyle.css" />" rel="stylesheet">
</head>

<body>
<c:import url="../tempates/topNavigationMenu.jsp"/>
<c:import url="../tempates/login.jsp"/>

<!-- Page Content -->
<div class="container">
    <div class="row">

        <h3><spring:message code="label.users"/></h3>
        <c:if test="${!empty userList}">
            <table class="data">
                <tr>
                    <th><spring:message code="label.login"/></th>
                    <th><spring:message code="label.password"/></th>
                    <th><spring:message code="label.email"/></th>
                    <th><spring:message code="label.telephone"/></th>
                    <th>&nbsp;</th>
                </tr>
                <c:forEach items="${userList}" var="user">
                    <tr>
                        <td>${user.login}</td>
                        <td>${user.password}</td>
                        <td>${user.email}</td>
                        <td>${user.telephone}</td>
                        <td><a href="delete/${user.id_user}">Delete</a></td>
                    </tr>
                </c:forEach>
            </table>
        </c:if>
    </div>
</div>

<!-- Bootstrap core JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>