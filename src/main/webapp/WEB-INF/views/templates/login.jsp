<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<!--modal login user-->
<div class="modal fade" id="login-modal" tabindex="-1" role="dialog" aria-labelledby="myModalLabel" aria-hidden="true">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                <h1 class="text-center"><spring:message code="loginPage.header"/></h1>
            </div>
            <div class="modal-body">
                <div class="col-md-12">
                    <div class="panel panel-default">
                        <div class="panel-body">
                            <form class="" action="<c:url value='/signIn'/>" method="POST">
                                <div class="form-group">
                                    <input class="form-control input-lg" type="text" name="username"
                                           placeholder="<spring:message code="loginPage.user.name"/>">
                                </div>
                                <div class="form-group">
                                    <input class="form-control input-lg" type="password" name="password"
                                           placeholder="<spring:message code="loginPage.user.password"/>" required="required">
                                </div>
                                <input type="hidden" name="${_csrf.parameterName}"
                                       value="${_csrf.token}"/>
                                <input type="submit" name="login" class="btn btn-lg btn-primary btn-block"
                                       value="<spring:message code="loginPage.submit"/>" required="required">
                            </form>
                            <div>
                                <div>
                                    With Facebook: <a href="/mas/connect/facebook">click here</a>
                                </div>
                            </div>
                            <div>
                                <div>
                                    <a href="${pageContext.request.contextPath}/connect/google"><button class="btn btn-facebook"><i class="icon-facebook"></i> | <spring:message code="loginPage.facebook.sign.in.button"/></button></a>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="login-help">
                    <a href="<c:url value="/user/registration" />"><spring:message code="loginPage.register"/></a>
                    -
                    <a href="<c:url value="/user/sendPassword" />"><spring:message code="loginPage.password.forgot"/></a>
                </div>
            </div>
        </div>
    </div>
</div>

