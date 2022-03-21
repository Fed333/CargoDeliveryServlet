<%@ tag import="java.util.List" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<%@ attribute name="page" type="com.epam.cargo.infrastructure.web.data.page.Page" required="true"%>
<%@ attribute name="prefix" type="java.lang.String" required="true"%>
<%@ attribute name="submitButtonId" type="java.lang.String" required="true"%>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>


<div class="mt-2">
    <ul class="pagination" id="${prefix}Paginator">
        <li class="page-item disabled">
            <a class="page-link" href="#"><fmt:message key="pagination.pages"/></a>
        </li>
        <c:set scope="application" var="totalPages" value="${page.totalPages}"/>
        <c:set scope="application" var="pageNumber" value="${page.number + 1}"/>

        <c:set scope="application" var="head" value="${[]}"/>
        <c:set scope="application" var="head" value="${pageNumber == 2 || pageNumber == 4 ? [1] : head}"/>
        <c:set scope="application" var="head" value="${pageNumber > 4 ? [1, -1] : head}"/>

        <c:set scope="application" var="tail" value="${[]}"/>
        <c:set scope="application" var="tail" value="${pageNumber == totalPages - 3 || pageNumber == totalPages - 1 ? [totalPages] : tail}"/>
        <c:set scope="application" var="tail" value="${(pageNumber < totalPages - 3) ? [-1, totalPages] : tail}"/>

        <c:set scope="application" var="bodyBefore" value="${(pageNumber >= 3 && pageNumber <= totalPages) ? [pageNumber - 2, pageNumber - 1] : []}"/>
        <c:set scope="application" var="bodyAfter" value="${(pageNumber < totalPages - 1) ? [pageNumber + 1, pageNumber + 2] : []}"/>

        <c:set scope="application" var="activePage" value="${page.number}"/>

        <jsp:useBean id="pagesList" scope="request" class="java.util.ArrayList"/>

        <%
            pagesList.addAll((List)application.getAttribute("head"));
            pagesList.addAll((List)application.getAttribute("bodyBefore"));
            pagesList.add(application.getAttribute("pageNumber"));
            pagesList.addAll((List)application.getAttribute("bodyAfter"));
            pagesList.addAll((List)application.getAttribute("tail"));
        %>

        <c:set scope="application" var="pages" value="${pagesList}"/>
        <c:forEach items="${applicationScope.pages}" var="page">
            <c:choose>
                <c:when test="${page == -1}">
                    <li class="page-item disabled">
                        <a class="page-link" href="#" tabindex="-1">...</a>
                    </li>
                </c:when>
                <c:when test="${page == pageNumber}">
                    <li class="page-item active">
                        <a class="page-link" href="#" tabindex="-1">${page}</a>
                    </li>
                </c:when>
                <c:otherwise>
                    <li class="page-item <c:if test="${activePage == page-1}">active</c:if>">
                        <a class="page-link" role="button" href="#" tabindex="-1" page-number="${page-1}">${page}</a>
                    </li>
                </c:otherwise>
            </c:choose>

        </c:forEach>
    </ul>
    <input type="text" name="${prefix}page" id="pageNumber" value="${activePage != null ? activePage : 0}" hidden>
    <input type="text" name="${prefix}size" id="pageSize" value="" hidden>
    <script src="${pageContext.request.contextPath}/static/js/pagination.js"></script>
    <script>
        paginate('${prefix}Paginator', ()=>{document.getElementById('${submitButtonId}').click()})
    </script>
</div>
