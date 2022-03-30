<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<!DOCTYPE>
<html>
<head>
    <title>Receipt</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>
    <div class="container mt-4">
        <div class="row">
            <div class="col d-flex justify-content-center">
                <div class="card">
                    <div class="card-body" style="min-width: 480px;">
                        <h1 class="d-flex justify-content-center"><fmt:message key="delivery.application.receipt.head"/></h1>
                        <hr>
                        <div class="row mb-2">
                            <div class="col">
                                <label class="fw-bolder"><fmt:message key="delivery.application"/>:</label>
                            </div>
                            <div class="col">
                                <label><a href="${pageContext.request.contextPath}/application?id=${requestScope.receipt.application.id}">#${requestScope.receipt.application.id}</a></label>
                            </div>
                        </div>
                        <div class="row mb-2">
                            <div class="col">
                                <label class="fw-bolder"><fmt:message key="lang.customer"/>:</label>
                            </div>
                            <div class="col">
                                <label>${requestScope.receipt.customer.name} ${requestScope.receipt.customer.surname}</label>
                            </div>
                        </div>
                        <div class="row mb-2">
                            <div class="col">
                                <label class="fw-bolder"><fmt:message key="lang.manager"/>:</label>
                            </div>
                            <div class="col">
                                <label>${requestScope.receipt.manager.name} ${requestScope.receipt.manager.surname}</label>
                            </div>
                        </div>
                        <div class="row mb-2">
                            <div class="col">
                                <label class="fw-bolder"><fmt:message key="delivery.application.receipt.formation-date"/>:</label>
                            </div>
                            <div class="col">
                                <label>${requestScope.receipt.formationDate}</label>
                            </div>
                        </div>
                        <div class="row mb-2">
                            <div class="col">
                                <label class="fw-bolder"><fmt:message key="lang.price"/>:</label>
                            </div>
                            <div class="col">
                                <label>${requestScope.receipt.totalPrice} <fmt:message key="lang.UAH"/></label>
                            </div>
                        </div>
                        <div class="row mb-2">
                            <div class="col">
                                <label class="fw-bolder"><fmt:message key="delivery.application.receipt.payment"/>:</label>
                            </div>
                            <div class="col">
                                <label>
                                    <c:choose>
                                        <c:when test="${requestScope.receipt.paid}">
                                            <fmt:message key="lang.paid"/>
                                        </c:when>
                                        <c:otherwise>
                                            <fmt:message key="lang.unpaid"/>
                                        </c:otherwise>
                                    </c:choose>
                                </label>
                            </div>
                        </div>
                        <div class="row mt-4">
                            <div class="col d-flex justify-content-center">
                                <a class="btn btn-success" href="${pageContext.request.contextPath}/profile"><fmt:message key="lang.OK"/></a>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </div>

</div>
<script src="${pageContext.request.contextPath}/static/js/formSubmit.js"></script>
<script src="${pageContext.request.contextPath}/static/js/localization.js"></script>
<script>

</script>
</body>
</html>
