<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<c:set scope="request" var="url" value="${pageContext.request.contextPath}/receipt/pay?id=${requestScope.receipt.id}"/>

<!DOCTYPE>
<html>
<head>
    <title>Pay Receipt</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>
    <div class="container mt-4">
        <form action="${pageContext.request.contextPath}/receipt/pay" method="post">
            <input name="id" value="${requestScope.receipt.id}" hidden>
            <div class="row">
                <div class="col d-flex justify-content-center">
                    <div class="card" style="min-width: 480px;">
                        <div class="card-body">
                            <h1 class="d-flex justify-content-center"><fmt:message key="delivery.application.receipt.pay-receipt"/></h1>
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
                            <div class="row mt-4">
                                <div class="col d-flex justify-content-center">
                                    <button class="btn btn-success" type="submit"><fmt:message key="lang.pay"/></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </form>
    </div>

</div>
<script src="${pageContext.request.contextPath}/static/js/formSubmit.js"></script>
<script src="${pageContext.request.contextPath}/static/js/localization.js"></script>
<script>
    addSwitchLanguageWithUrlClickListeners('${url}', [])
</script>
</body>
</html>
