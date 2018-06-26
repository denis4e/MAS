<%@tag description="Pagination" pageEncoding="UTF-8" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<link href="<c:url value="/resources/css/custom/footer.css" />" rel="stylesheet">

<nav aria-label="...">
    <ul class="pagination">
        <c:choose>
            <c:when test="${currentIndex == beginIndex}">
                <li class="page-item disabled">
                    <a class="page-link" href="" tabindex="-1">Previous</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <a class="page-link" href="${link}?page=${currentIndex - 1}" tabindex="-1">Previous</a>
                </li>
            </c:otherwise>
        </c:choose>
        <c:forEach var="i" begin="${beginIndex}" end="${endIndex}">
            <c:url var="pageUrl" value="/pages/${i}"/>
            <c:choose>
                <c:when test="${i == currentIndex}">
                    <li class="page-item active">
                        <a class="page-link" href="">${i} <span class="sr-only">(current)</span></a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item">
                        <a class="page-link" href="${link}?page=${i}">${i} <span
                                class="sr-only"></span></a>
                    </li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
        <c:choose>
            <c:when test="${currentIndex == endIndex}">
                <li class="page-item disabled">
                    <a class="page-link" href="" tabindex="-1">Previous</a>
                </li>
            </c:when>
            <c:otherwise>
                <li class="page-item">
                    <a class="page-link" href="${link}?page=${currentIndex + 1}" tabindex="-1">Next</a>
                </li>
            </c:otherwise>
        </c:choose>
    </ul>
</nav>
