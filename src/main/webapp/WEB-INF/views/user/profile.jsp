<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib prefix="spring" uri="http://www.springframework.org/tags" %>

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
        <div class="panel panel-default">
            <div class="panel-body">
                <form:form method="post" action="updateUser" modelAttribute="user" cssClass="form-horizontal">
                    <input type="text" class="hidden" name="userId" value="${user.id_user}" id="userId">
                    <div class="form-group ">
                        <label for="login" class="col-sm-4 control-label">
                            <spring:message code="user.profile.login"/>
                        </label>
                        <div class="col-sm-4">
                            <input type="text" disabled class="form-control" name="login" value="${user.login}" id="login">
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="email" class="col-sm-4 control-label">
                            <spring:message code="user.profile.email"/>
                        </label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="email" value="${user.email}" id="email">
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="userName" class="col-sm-4 control-label">
                            <spring:message code="user.profile.userName"/>
                        </label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="userName" value="${user.userName}" id="userName">
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="lastName" class="col-sm-4 control-label">
                            <spring:message code="user.profile.lastName"/>
                        </label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="lastName" value="${user.lastName}" id="lastName">
                        </div>
                    </div>
                    <div class="form-group ">
                        <label for="phone" class="col-sm-4 control-label">
                            <spring:message code="user.profile.phone"/>
                        </label>
                        <div class="col-sm-4">
                            <input type="text" class="form-control" name="phone" value="${user.phone}" id="phone">
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="text-center">
                                <button class="btn btn-primary btn-lg">
                                    <spring:message code="user.profile.save"/></button>
                            </div>
                        </div>
                    </div>
                </form:form>
            </div>
        </div>
    </div>
</div>

<!-- footer-->
<tag:footerWrapper/>
</body>
</html>