<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<%@ page pageEncoding="UTF-8" contentType="text/html; charset=UTF-8" %>
<!-- header-->
<spring:message code="label.registered.users.title" var="title"/>
<tag:headWrapper pageTitle="${title}"/>

<html>
<body>
<c:import url="../templates/topNavigationMenu.jsp"/>
<c:import url="../templates/login.jsp"/>

<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <h3><spring:message code="label.users"/></h3>
            <div class="panel-body">

                <table class="table table-hover">
                    <thead>
                    <tr>
                        <th scope="col">#</th>
                        <th scope="col"><spring:message code="label.login"/></th>
                        <th scope="col"><spring:message code="label.email"/></th>
                        <th scope="col"><spring:message code="label.telephone"/></th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${userList}" var="user">
                        <tr>
                            <th scope="row">${user.id_user}</th>
                            <td>${user.login}</td>
                            <td>${user.email}</td>
                            <td>${user.phone}</td>
                            <td><a href="admin/delete/${user.id_user}">Delete</a></td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <!-- pagination-->
                <tag:pagination/>
            </div>
        </div>
    </div>
</div>
<!-- footer-->
<tag:footerWrapper/>
</body>
</html>