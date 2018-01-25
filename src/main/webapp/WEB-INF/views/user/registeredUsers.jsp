<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<!-- header-->
<tag:headWrapper/>

<html>
<body>
<c:import url="../templates/topNavigationMenu.jsp"/>
<c:import url="../templates/login.jsp"/>

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
                <td>${user.phone}</td>
                <td><a href="delete/${user.id_user}">Delete</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>

<!-- Bootstrap core JavaScript -->
<script src="${pageContext.request.contextPath}/resources/js/jquery-3.2.1.min.js" type="text/javascript"></script>
<script src="${pageContext.request.contextPath}/resources/js/bootstrap.min.js" type="text/javascript"></script>
</body>
</html>