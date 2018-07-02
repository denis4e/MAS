<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page import="com.mas.util.Links" %>

<!-- top menu -->
<nav class="navbar navbar-inverse">
    <div class="container-fluid">
        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse" data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
            <a class="navbar-brand" href="#">MAS</a>
        </div>
        <div class="collapse navbar-collapse" id="myNavbar">
            <ul class="nav navbar-nav">
                <li class="active"><a href="<c:url value="/" />"><spring:message code="homePage.page.title"/></a></li>
                <li class="dropdown">
                    <a class="dropdown-toggle" data-toggle="dropdown" href="#">Page 1 <span class="caret"></span></a>
                    <ul class="dropdown-menu">
                        <li><a href="#">Page 1-1</a></li>
                        <li><a href="#">Page 1-2</a></li>
                        <li><a href="#">Page 1-3</a></li>
                    </ul>
                </li>
                <li><a href="#">Page 2</a></li>
                <li><a href="#">Page 3</a></li>
            </ul>
            <ul class="nav navbar-nav navbar-right">
                <c:if test="${pageContext.request.userPrincipal.name != null}">
                    <li class="dropdown">
                        <a class="dropdown-toggle" data-toggle="dropdown" href="#">
                            <span class="glyphicon glyphicon-user"></span>
                                ${pageContext.request.userPrincipal.name}
                            <span class="caret"></span>
                        </a>
                        <ul class="dropdown-menu">
                            <security:authorize access="hasRole('ROLE_USER')">
                                <li><a href="<c:url value="/user/userProfile" />"><spring:message
                                        code="homePage.profile"/></a></li>
                            </security:authorize>
                            <security:authorize access="hasRole('ROLE_ADMIN')">
                                <li><a href="<c:url value="/admin/usersList" />"> <spring:message
                                        code="homePage.users"/></a></li>
                            </security:authorize>
                            <c:if test="${pageContext.request.userPrincipal.name != null}">
                                <li>
                                    <a href="javascript:;" onclick="$('#logOutForm').submit();">
                                        <span class="glyphicon glyphicon-log-out" aria-hidden="true"></span>
                                        <spring:message code="homePage.logout"/>
                                    </a>
                                </li>
                            </c:if>
                        </ul>
                    </li>
                    <form action="<c:url value='/logout'/>" method="post" id="logOutForm">
                        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
                    </form>
                </c:if>
                <c:if test="${pageContext.request.userPrincipal.name == null}">
                    <li>
                        <a href="#" data-toggle="modal" data-target="#login-modal">
                            <span class="glyphicon glyphicon-log-in"></span>
                        </a>
                    </li>
                </c:if>
            </ul>
        </div>
    </div>
</nav>
