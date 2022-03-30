<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<c:set scope="request" var="baggage" value="${requestScope.application.deliveredBaggage}"/>
<c:set scope="request" var="type" value="${requestScope.baggage.type}"/>
<c:set scope="request" var="volume" value="${requestScope.baggage.volume}"/>
<c:set scope="request" var="weight" value="${requestScope.baggage.weight}"/>
<c:set scope="request" var="description" value="${requestScope.baggage.description}"/>

<c:set scope="request" var="senderAddress" value="${requestScope.application.senderAddress}"/>
<c:set scope="request" var="senderStreet" value="${requestScope.senderAddress.street}"/>
<c:set scope="request" var="senderHouseNumber" value="${requestScope.senderAddress.houseNumber}"/>
<c:set scope="request" var="senderCityName" value="${requestScope.application.senderAddress.city.name}"/>

<c:set scope="request" var="receiverAddress" value="${requestScope.application.receiverAddress}"/>
<c:set scope="request" var="receiverStreet" value="${requestScope.receiverAddress.street}"/>
<c:set scope="request" var="receiverHouseNumber" value="${requestScope.receiverAddress.houseNumber}"/>
<c:set scope="request" var="receiverCityName" value="${requestScope.receiverAddress.city.name}"/>

<c:set scope="request" var="sendingDate" value="${requestScope.application.sendingDate}"/>
<c:set scope="request" var="receivingDate" value="${requestScope.application.receivingDate}"/>

<!DOCTYPE>
<html>
<head>
    <title>Application</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>
    <div class="container mt-4">
        <c:if test="${requestScope.application != null}">
            <div class="row">
                <div class="col d-flex justify-content-center">
                    <div class="card" style="min-width: 740px;">
                        <div class="card-body">
                            <div class="row">
                                <h1 class="d-flex justify-content-center"><fmt:message key="delivery-application.page.head"/> #${requestScope.application.id}</h1>
                            </div>
                            <hr>
                            <c:if test="${requestScope.application.state == 'SUBMITTED'}">
                                <div class="row">
                                    <div class="col d-flex justify-content-end">
                                        <a href="${pageContext.request.contextPath}/application/update?id=${requestScope.application.id}" class="btn btn-primary"><fmt:message key="lang.edit"/></a>
                                    </div>
                                </div>
                            </c:if>
                            <div class="row">
                                <h2 class="d-flex justify-content-center"><fmt:message key="lang.baggage"/></h2>
                            </div>
                            <div class="ms-4 me-4">

                                <div class="row mb-2">
                                    <div class="col">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.type"/>:</label> <label><c:if test="${type != null}"><fmt:message key="baggage.type.${type}"/></c:if></label>
                                    </div>
                                </div>

                                <div class="row mb-2">
                                    <div class="col">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.volume"/>:</label> <label>${volume} <fmt:message key="lang.cm"/><sup><small>3</small></sup></label>
                                    </div>
                                    <div class="col">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.weight"/>:</label> <label>${weight} <fmt:message key="lang.kg"/></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.description"/>:</label> <label>${description}</label>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <h2 class="d-flex justify-content-center"><fmt:message key="lang.address"/></h2>
                            </div>

                            <div class="ms-4 me-4">
                                <div class="row mb-2">
                                    <div class="col">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.sender"/>:</label> <label><c:if test="${senderCityName != null}">${senderCityName}</c:if>, <fmt:message key="address.street"/> ${senderStreet}, <fmt:message key="address.house"/> ${senderHouseNumber}</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.receiver"/>:</label> <label><c:if test="${receiverCityName != null}">${receiverCityName}</c:if>, <fmt:message key="address.street"/> ${receiverStreet}, <fmt:message key="address.house"/> ${receiverHouseNumber}</label>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="ms-4 me-4">
                                <div class="row mb-2">
                                    <div class="col-4">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="delivery-application.page.sending-date"/>:</label>
                                    </div>
                                    <div class="col-2">
                                        <label>${sendingDate}</label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-4">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="delivery-application.page.receiving-date"/>:</label>
                                    </div>
                                    <div class="col-2">
                                        <label>${receivingDate}</label>
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="ms-4 me-4">
                                <div class="row mb-2">
                                    <div class="col">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="delivery-application.state"/>:</label> <label><fmt:message key="delivery-application.state.${requestScope.application.state}"/></label>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.price"/>:</label> <label>${requestScope.application.price} <fmt:message key="lang.UAH"/></label>
                                    </div>
                                </div>
                            </div>
                            <hr>

                            <c:choose>
                                <c:when test="${sessionScope.authorizedUser.isManager()}">

                                    <c:set scope="request" var="login" value="${requestScope.application.customer.login}"/>
                                    <c:set scope="request" var="id" value="${requestScope.application.customer.id}"/>
                                    <c:set scope="request" var="phone" value="${requestScope.application.customer.phone}"/>
                                    <c:set scope="request" var="firstNameLastName" value="${requestScope.application.customer.name} ${requestScope.application.customer.surname}"/>

                                    <c:set scope="request" var="customerAddress" value="${requestScope.application.customer.address}"/>
                                    <c:set scope="request" var="cityName" value="${requestScope.customerAddress.city.name}"/>
                                    <c:set scope="request" var="street" value="${requestScope.customerAddress.street}"/>
                                    <c:set scope="request" var="houseNumber" value="${requestScope.customerAddress.houseNumber}"/>

                                    <div class="ms-4 me-4">
                                        <div class="row">
                                            <div class="col-2">
                                                <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.customer"/>:</label>
                                            </div>
                                            <div class="col-auto mt-1">
                                                <a href="${pageContext.request.contextPath}/profile/review?id=${id}">${firstNameLastName != null ? firstNameLastName : login}</a>.
                                            </div>
                                            <div class="col-auto mt-1">
                                                <label>${cityName}, <fmt:message key="address.street"/> ${street}, <fmt:message key="address.house"/> ${houseNumber}</label>
                                            </div>
                                            <div class="col-auto mt-1">
                                                <label>${phone}</label>
                                            </div>
                                        </div>
                                    </div>

                                    <c:choose>
                                        <c:when test="${requestScope.application.state == \"SUBMITTED\"}">
                                            <div class="row mt-4">
                                                <div class="col d-flex justify-content-center">
                                                    <div class="col-auto me-4">
                                                        <form action="${pageContext.request.contextPath}/application/accept" method="get">
                                                            <input name="id" value="${requestScope.application.id}" hidden/>
                                                            <button class="btn btn-success"><fmt:message key="lang.accept"/></button>
                                                        </form>
                                                    </div>
                                                    <div class="col-auto">
                                                        <form action="${pageContext.request.contextPath}/application/reject" method="post">
                                                            <input name="id" value="${requestScope.application.id}" hidden>
                                                            <button class="btn btn-danger"><fmt:message key="lang.reject"/></button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:when test="${requestScope.application.state == \"CONFIRMED\"}">
                                            <div class="row mt-4">
                                                <div class="col d-flex justify-content-center">
                                                    <div class="col-auto me-4">
                                                        <form action="${pageContext.request.contextPath}/application/complete" method="post">
                                                            <input name="id" value="${requestScope.application.id}" hidden>
                                                            <button class="btn btn-success"><fmt:message key="lang.complete"/></button>
                                                        </form>
                                                    </div>
                                                    <div class="col-auto">
                                                        <form action="${pageContext.request.contextPath}/application/reject" method="post">
                                                            <input name="id" value="${requestScope.application.id}" hidden>
                                                            <button class="btn btn-danger"><fmt:message key="lang.reject"/></button>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </c:when>
                                        <c:otherwise>
                                            <div class="row mt-4">
                                                <div class="col d-flex justify-content-center">
                                                    <a class="btn btn-outline-success" href="${pageContext.request.contextPath}/applications/review">OK</a>
                                                </div>
                                            </div>
                                        </c:otherwise>
                                    </c:choose>

                                </c:when>
                                <c:otherwise>
                                    <div class="row">
                                        <div class="col d-flex justify-content-center">
                                            <a class="btn btn-primary" href="${pageContext.request.contextPath}/profile"><fmt:message key="ref.back-to-profile"/></a>
                                        </div>
                                    </div>
                                </c:otherwise>
                            </c:choose>
                        </div>
                    </div>
                </div>
            </div>
        </c:if>
    </div>

</div>
<script src="${pageContext.request.contextPath}/static/js/formSubmit.js"></script>
<script src="${pageContext.request.contextPath}/static/js/localization.js"></script>
<input name="id" id="applicationIdHiddenInput" value="${requestScope.application.id}" hidden>
<script>
    addSwitchLanguageWithUrlClickListeners("${pageContext.request.contextPath}/application", ['applicationIdHiddenInput'])
</script>
</body>
</html>
