<%@taglib prefix="tag" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<!-- header-->
<spring:message code="homePage.page.title" var="title" />
<tag:headWrapper pageTitle="${title}" />

<html>
<body>

<!-- Top Menu-->
<c:import url="templates/topNavigationMenu.jsp"/>

<!-- Login Page-->
<c:import url="templates/login.jsp"/>

<!-- Messages-->
<tag:messages successMessage="${successMessage}" infoMessage="${infoMessage}" errorMessage="${errorMessage}"/>

<!-- Page Content -->
<div class="container">
    <div class="row">
        <div class="col-lg-12">
            test
        </div>
    </div>
</div>

<!-- footer-->
<tag:footerWrapper/>

</body>
</html>

