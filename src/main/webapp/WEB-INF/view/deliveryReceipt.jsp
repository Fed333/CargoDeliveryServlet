<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<c:set scope="request" var="firstNameLastName" value="${requestScope.customer.name} ${requestScope.customer.surname}"/>

<!DOCTYPE>
<html>
<head>
    <title>Make Delivery Receipt</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>
    <div class="container mt-4">
        <div class="row">
            <div class="col d-flex justify-content-center">
                <form action="${pageContext.request.contextPath}/application/accept" method="post" style="min-width: 480px;">
                    <input name="id" id="applicationIdHiddenInput" value="${requestScope.application.id}" hidden/>
                    <div class="card">
                        <div class="card-body">
                            <h1 class="d-flex justify-content-center"><fmt:message key="delivery-receipt.head"/></h1>
                            <hr>
                            <div class="row mb-2">
                                <div class="col fs-5 fw-bolder"><fmt:message key="delivery.application"/>: </div>
                                <div class="col"> <a href="${pageContext.request.contextPath}/application?id=${requestScope.application.id}">#${requestScope.application.id}</a> </div>
                            </div>
                            <div class="row mb-2">
                                <div class="col fs-5 fw-bolder"><fmt:message key="lang.customer"/>: </div>
                                <div class="col"> <a href="${pageContext.request.contextPath}/profile/review?id=${requestScope.customer.id}">${firstNameLastName}</a> </div>
                            </div>
                            <div class="row mb-2">
                                <div class="col fs-5 fw-bolder"><fmt:message key="lang.manager"/>: </div>
                                <div class="col"> <a href="${pageContext.request.contextPath}/profile/review?id=${requestScope.manager.id}">${requestScope.manager.name} ${requestScope.manager.surname}</a> </div>
                            </div>
                            <div class="row mb-2">
                                <div class="col fs-5 fw-bolder"><fmt:message key="delivery-receipt.total-price"/>:</div>
                                <div class="col">
                                    <div class="row">
                                        <div class="col validation-container">
                                            <input type="text" class="form-control" name="price" value="${requestScope.price}" <c:if test="${requestScope.receiptErrorMessage != null}">data-error="${requestScope.receiptErrorMessage}"</c:if> placeholder="<fmt:message key="lang.price"/>">
                                        </div>
                                        <div class="col">
                                            <label class="col-form-label"><fmt:message key="lang.UAH"/></label>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <div class="col d-flex justify-content-center">
                                    <button class="btn btn-success"><fmt:message key="delivery-receipt.make.button"/></button>
                                </div>
                            </div>
                        </div>
                    </div>
                </form>
            </div>
        </div>
    </div>

</div>
<script src="${pageContext.request.contextPath}/static/js/formSubmit.js"></script>
<script src="${pageContext.request.contextPath}/static/js/localization.js"></script>
<script>
    addSwitchLanguageWithUrlClickListeners('${pageContext.request.contextPath}/application/accept', ['applicationIdHiddenInput'])
</script>
</body>
</html>
