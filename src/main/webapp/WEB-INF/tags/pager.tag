<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>

<%@ attribute name="url" type="java.lang.String" required="true" %>
<%@ attribute name="prefix" type="java.lang.String" required="false"%>
<%@ attribute name="submitButtonId" type="java.lang.String" required="true"%>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<div class="mt-2">
    <ul class="pagination" id="${prefix != null ? prefix : ''}Paginator">
        <li class="page-item disabled">
            <a class="page-link" href="#"><fmt:message key="pagination.pages"/></a>
        </li>
        <c:set scope="application" var="activePage" value="${requestScope.pageable.pageNumber}"/>
        <c:set scope="application" var="pages" value="${[1,2,3,4,5]}"/>
        <c:forEach items="${pages}" var="page">
            <li class="page-item <c:if test="${activePage == page-1}">active</c:if>">
                <a class="page-link" role="button" href="#" tabindex="-1" page-number="${page-1}">${page}</a>
            </li>
        </c:forEach>
    </ul>
    <input type="text" name="${prefix != null ? prefix + "_page" : "page"}" id="pageNumber" value="${activePage != null ? activePage : 0}" hidden>
    <input type="text" name="size" id="pageSize" value="" hidden>
    <script src="static/js/pagination.js"></script>
    <script>
        paginate('${prefix != null ? prefix : ''}Paginator', ()=>{document.getElementById('${submitButtonId}').click()})
    </script>
</div>
