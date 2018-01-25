<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>


<!-- header-->
<spring:message code="sendPassword.page.title" var="title"/>
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
                <h1 class="text-center"><spring:message code="sendPassword.page.title"/></h1>
                <div class="text-center">
                    <p><spring:message code="sendPassword.page.header"/></p>
                </div>
                <form method="GET" action="<c:url value='/user/sendPasswordToEmail'/>" class="form-horizontal">
                    <div class="col-md-6 col-md-offset-3 text-center">
                        <div class="input-group">
                            <input class="form-control input-lg" placeholder="E-mail Address"
                                   name="email" type="email" required="required"/>
                              <span class="input-group-btn">
                                        <input type="submit" class="btn btn-primary btn-lg">
                                            <spring:message code="sendPassword.send"/>
                                        </input>
                              </span>
                        </div><!-- /input-group -->
                    </div>
                </form>
            </div>
            </form>
        </div>
    </div>
</div>

<!-- footer-->
<tag:footerWrapper/>
</body>
</html>