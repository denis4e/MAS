<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<!-- header-->
<spring:message code="registration.page.title" var="title"/>
<tag:headWrapper pageTitle="${title}"/>

<html>
<body>

<!-- Top Menu-->
<c:import url="../templates/topNavigationMenu.jsp"/>

<!-- Login Page-->
<c:import url="../templates/login.jsp"/>

<!-- Messages-->
<tag:messages successMessage="${successMessage}" infoMessage="${infoMessage}" errorMessage="${errorMessage}"/>


<div class="container">
    <div class="row">
        <div class="panel panel-default">
            <div class="panel-body">
                <form:form method="post" action="createNewUser" commandName="user" cssClass="form-horizontal">
                    <h2><spring:message code="registration.page.title"/></h2>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <label class="col-sm-4 control-label">
                                <spring:message code="registration.label.login"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input class="form-control" path="login" placeholder="Login"/>
                                <form:errors id="loginError" path="login" cssClass="alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <label class="col-sm-4 control-label">
                                <spring:message code="registration.label.name"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input class="form-control" path="userName" placeholder="Name"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <label class="col-sm-4 control-label">
                                <spring:message code="registration.label.lastName"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input class="form-control" path="lastName" placeholder="Last Name"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <label class="col-sm-4 control-label" for="email">
                                <spring:message code="registration.label.email"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input type="text" id="email" class="form-control" path="email"
                                            placeholder="Email"/>
                                <form:errors id="emailError" path="email" cssClass="alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <label class="col-sm-4 control-label">
                                <spring:message code="registration.label.phone"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input class="form-control" path="phone" placeholder="Phone"/>
                            </div>
                        </div>
                    </div>
                    <div class="form-group">
                        <div class="col-sm-12">
                            <label class="col-sm-4 control-label">
                                <spring:message code="registration.label.password"/>
                            </label>
                            <div class="col-sm-4">
                                <form:input class="form-control" path="password" type="password"
                                            placeholder="Password"/>
                                <form:errors id="passwordError" path="password" cssClass="alert-danger"/>
                            </div>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-12">
                            <div class="text-center">
                                <button class="btn btn-primary btn-lg"><spring:message
                                        code="registration.register.user"/></button>
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