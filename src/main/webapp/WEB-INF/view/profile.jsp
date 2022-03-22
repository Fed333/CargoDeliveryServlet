<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="tag" tagdir="/WEB-INF/tags" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<c:set scope="application" var="cityName" value="${requestScope.user.address.city.name}"/>
<c:set scope="application" var="street" value="${requestScope.user.address.street}"/>
<c:set scope="application" var="houseNumber" value="${requestScope.user.address.houseNumber}"/>

<c:set scope="session" var="activePill" value="${requestScope.activePill != null ? requestScope.activePill : sessionScope.activePill}"/>
<c:set scope="application" var="notificationsPill" value="pills-notifications-tab"/>
<c:set scope="application" var="applicationsPill" value="pills-applications-tab"/>
<c:set scope="application" var="receiptsPill" value="pills-receipts-tab"/>

<!DOCTYPE>
<html>
<head>
    <title>Profile</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>
    <div class="container mt-4">
        <div class="row">
            <div class="col-3">
                <h1 class="d-flex justify-content-center mb-4"><fmt:message key="lang.profile"/></h1>

                <ul class="list-group">
                    <li class="list-group-item">
                        <fmt:message key="lang.login"/>: ${requestScope.user.login}
                    </li>
                    <li class="list-group-item">
                        <fmt:message key="lang.name"/>: ${requestScope.user.name != null ? requestScope.user.name : "-"}
                    </li>
                    <li class="list-group-item">
                        <fmt:message key="lang.surname"/>: ${requestScope.user.surname != null ? requestScope.user.surname : "-"}
                    </li>
                    <li class="list-group-item">
                        <fmt:message key="lang.phone"/>: ${requestScope.user.phone != null ? requestScope.user.phone : "-"}
                    </li>
                    <li class="list-group-item">
                        <fmt:message key="lang.email"/>: ${requestScope.user.email != null ? requestScope.user.email : "-"}
                    </li>
                    <li class="list-group-item">
                        <fmt:message key="lang.address"/>: ${cityName} <fmt:message key="address.street"/> ${street}, <fmt:message key="address.house"/> ${houseNumber}
                    </li>
                    <li class="list-group-item">
                        <fmt:message key="lang.balance"/>: ${requestScope.user.cash != null ? requestScope.user.cash : "0.0"} <fmt:message key="lang.UAH"/>
                    </li>
                </ul>
            </div>
            <div class="col">
                <form action="${pageContext.request.contextPath}/profile" method="get">
                    <input name="lang" value="${sessionScope.lang}" id="langInput" hidden>
                    <button type="submit" id="submitButton" hidden></button>
                    <input name="activePill" id="activePillHiddenInput" value="${sessionScope.activePill}" hidden>

                    <div class="row">
                        <ul class="nav nav-pills justify-content-center mt-2 mb-4" id="profileMenuItems">
                            <li class="nav-item">
                                <button class="nav-link <c:if test="${sessionScope.activePill == applicationScope.notificationsPill}">active</c:if>" id="pills-notifications-tab" data-bs-toggle="pill" data-bs-target="#pills-notifications" type="button" role="tab" aria-controls="pills-notifications" aria-selected="${activePill == applicationScope.notificationsPill ? true : false}" ><fmt:message key="profile-menu-pills.notifications"/></button>
                            </li>
                            <li class="nav-item">
                                <button class="nav-link <c:if test="${sessionScope.activePill == applicationScope.applicationsPill}">active</c:if>" id="pills-applications-tab" data-bs-toggle="pill" data-bs-target="#pills-applications" type="button" role="tab" aria-controls="pills-applications" aria-selected="${activePill == applicationScope.applicationsPill ? true : false}"><fmt:message key="profile-menu-pills.applications"/></button>
                            </li>
                            <li class="nav-item">
                                <button class="nav-link <c:if test="${sessionScope.activePill == applicationScope.receiptsPill}">active</c:if>" id="pills-receipts-tab" data-bs-toggle="pill" data-bs-target="#pills-receipts" type="button" role="tab" aria-controls="pills-receipts" aria-selected="${activePill == applicationScope.receiptsPill ? true : false}"><fmt:message key="profile-menu-pills.receipts"/></button>
                            </li>
                        </ul>

                        <div class="tab-content" id="pills-tab-content">
                            <div class="tab-pane fade <c:if test="${sessionScope.activePill == applicationScope.notificationsPill}">active show</c:if>" id="pills-notifications" role="tabpanel" aria-labelledby="pills-notifications-tab">
                                There is no notifications for you yet.
                            </div>

                            <div class="tab-pane fade <c:if test="${sessionScope.activePill == applicationScope.applicationsPill}">active show</c:if>" id="pills-applications" role="tabpanel" aria-labelledby="pills-applications-tab">
                                <c:forEach items="${requestScope.applications.content}" var="application">
                                    <div class="row alert alert-primary mb-2">
                                        <div class="col-1">
                                            <a class="link disabled" href="#">#${application.id}</a>
                                        </div>
                                        <div class="col">
                                                ${application.senderAddress.city.name} - ${application.receiverAddress.city.name}
                                        </div>
                                        <div class="col-auto">
                                                ${application.sendingDate} - ${application.receivingDate}
                                        </div>
                                        <div class="col-2">
                                                ${application.price} <fmt:message key="lang.UAH"/>
                                        </div>
                                        <div class="col-2">
                                            <fmt:message key="delivery-application.state.${application.state}"/>
                                        </div>
                                    </div>
                                </c:forEach>
                                <tag:pager prefix="applications_" page="${requestScope.applications}" submitButtonId="submitButton"/>
                            </div>

                            <div class="tab-pane fade <c:if test="${sessionScope.activePill == applicationScope.receiptsPill}">active show</c:if>" id="pills-receipts" role="tabpanel" aria-labelledby="pills-receipts-tab">
                                <div class="row alert alert-dark mb-2">
                                    <div class="col-1">
                                        #id
                                    </div>
                                    <div class="col-2">
                                        <fmt:message key="delivery.application"/>
                                    </div>
                                    <div class="col-3">
                                        <fmt:message key="lang.manager"/>
                                    </div>
                                    <div class="col-2" style="min-width: 110px;">
                                        <fmt:message key="delivery.application.receipt.formation-date"/>
                                    </div>
                                    <div class="col-2">
                                        <fmt:message key="lang.price"/>
                                    </div>
                                </div>
                                <c:forEach items="${requestScope.receipts.content}" var="receipt">
                                    <div class="row alert alert-primary mb-2">
                                        <div class="col-1 d-flex align-self-center">
                                            <a class="link disabled" href="#">#${receipt.id}</a>
                                        </div>
                                        <div class="col-2 d-flex align-self-center">
                                            <a class="link disabled" href="#">#${receipt.application.id}</a>
                                        </div>
                                        <div class="col-3 d-flex align-self-center">
                                                ${receipt.manager.name} ${receipt.manager.surname}
                                        </div>
                                        <div class="col-2 d-flex align-self-center" style="min-width: 110px;">
                                                ${receipt.formationDate}
                                        </div>
                                        <div class="col-2 d-flex align-self-center">
                                                ${receipt.totalPrice} <fmt:message key="lang.UAH"/>
                                        </div>
                                        <div class="col d-flex justify-content-end me-2 align-self-center">
                                            <c:choose>
                                                <c:when test="${receipt.paid == true}">
                                                    <button class="btn btn-primary disabled"><fmt:message key="lang.paid"/></button>
                                                </c:when>
                                                <c:otherwise>
                                                    <a class="btn btn-success" href="/receipt/${receipt.id}/pay"><fmt:message key="lang.pay"/></a>
                                                </c:otherwise>
                                            </c:choose>
                                        </div>
                                    </div>
                                </c:forEach>
                                <tag:pager prefix="receipts_" page="${requestScope.receipts}" submitButtonId="submitButton"/>
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
    function clickSubmitButtonHandler(){
        clickSubmitButton('submitButton')
    }
    addSwitchLanguageWithFormListeners(clickSubmitButtonHandler)
</script>

<script src="${pageContext.request.contextPath}/static/js/profile.js"></script>

<script>
    setChangingMenuItemsHandlers('profileMenuItems')
</script>

</body>
</html>
