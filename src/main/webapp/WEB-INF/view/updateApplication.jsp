<%@ taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core' %>
<%@ taglib prefix='fmt' uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<fmt:setLocale value="${sessionScope.lang}"/>
<fmt:setBundle basename="resources/messages"/>

<c:set scope="request" var="url" value="${pageContext.request.contextPath}/application/update"/>

<c:set scope="request" var="baggage" value="${requestScope.application.deliveredBaggage}"/>
<c:set scope="request" var="type" value="${requestScope.baggage.type}"/>
<c:set scope="request" var="volume" value="${requestScope.baggage.volume}"/>
<c:set scope="request" var="weight" value="${requestScope.baggage.weight}"/>
<c:set scope="request" var="description" value="${requestScope.baggage.description}"/>

<c:set scope="request" var="senderAddress" value="${requestScope.application.senderAddress}"/>
<c:set scope="request" var="senderStreet" value="${requestScope.senderAddress.street}"/>
<c:set scope="request" var="senderHouseNumber" value="${requestScope.senderAddress.houseNumber}"/>
<c:set scope="request" var="senderCityId" value="${requestScope.senderAdress.city.id}"/>
<c:set scope="request" var="senderCityName" value="${requestScope.senderAdress.city.name}"/>

<c:set scope="request" var="receiverAddress" value="${requestScope.application.receiverAddress}"/>
<c:set scope="request" var="receiverStreet" value="${requestScope.receiverAddress.street}"/>
<c:set scope="request" var="receiverHouseNumber" value="${requestScope.receiverAddress.houseNumber}"/>
<c:set scope="request" var="receiverCityId" value="${requestScope.receiverAdress.city.id}"/>
<c:set scope="request" var="receiverCityName" value="${requestScope.receiverAdress.city.name}"/>

<c:set scope="request" var="sendingDate" value="${requestScope.application.sendingDate}"/>
<c:set scope="request" var="receivingDate" value="${requestScope.application.receivingDate}"/>

<!DOCTYPE>
<html>
<head>
    <title>Update Application</title>
    <%@include file="jspf/head.jspf"%>
</head>
<body>
<div class="content">
    <%@include file="jspf/navbar.jspf"%>
    <div class="container mt-4">
        <div class="row mb-2">
            <div class="col">
                <h1 class="d-flex justify-content-center"><fmt:message key="lang.update"/></h1>
            </div>
        </div>
        <div class="row">
            <div class="col d-flex justify-content-center">
                <div class="card" style="min-width: 740px;">
                    <div class="card-body">
                        <form action="${url}" method="post" id="updateApplicationForm">
                            <c:set scope="request" var="applicationId" value="${requestScope.application.id}"/>
                            <input name="id" id="applicationIdHiddenInput" value="${applicationId}" hidden>
                            <div class="row">
                                <h1 class="d-flex justify-content-center"><fmt:message key="delivery-application.page.head"/> #${applicationId}</h1>
                            </div>
                            <hr>
                            <div class="row mb-3">
                                <h2 class="d-flex justify-content-center"><fmt:message key="lang.baggage"/></h2>
                            </div>

                            <div class="ms-4 me-4">
                                <div class="row mb-2">
                                    <div class="col-3">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.type"/>:</label>
                                    </div>
                                    <div class="col-3">
                                        <select class="form-select" name="deliveredBaggageRequest.type" id="baggageTypeSelect">
                                            <c:forEach items="${requestScope.baggageTypes}" var="baggageType">
                                                <option value="${baggageType}"><fmt:message key="baggage.type.${baggageType}"/></option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                </div>
                                <div class="row mb-2">
                                    <div class="col">
                                        <div class="row">
                                            <div class="col-6">
                                                <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.volume"/>:</label>
                                            </div>
                                            <div class="col-4 validation-container" <c:if test="${requestScope.volumeErrorMessage != null}">data-error="${requestScope.volumeErrorMessage}"</c:if>>
                                                <input type="text" class="form-control" name="deliveredBaggageRequest.volume" value="${volume}">
                                            </div>
                                            <div class="col-1">
                                                <label><fmt:message key="lang.cm"/><sup><small>3</small></sup></label>
                                            </div>
                                        </div>
                                    </div>
                                    <div class="col">
                                        <div class="row">
                                            <div class="col">
                                                <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.weight"/>:</label>
                                            </div>
                                            <div class="col validation-container" <c:if test="${requestScope.weightErrorMessage != null}">data-error="${requestScope.weightErrorMessage}"</c:if>>
                                                <input type="text" class="form-control" name="deliveredBaggageRequest.weight" value="${weight}">
                                            </div>
                                            <div class="col">
                                                <label><fmt:message key="lang.kg"/></label>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-3">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.description"/>:</label>
                                    </div>
                                    <div class="col">
                                        <input type="text" class="form-control" name="deliveredBaggageRequest.description" value="${description}">
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="row">
                                <h2 class="d-flex justify-content-center"><fmt:message key="lang.address"/></h2>
                            </div>
                            <div class="ms-4 me-4">
                                <div class="row mb-2">
                                    <div class="col align-self-center">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.sender"/>:</label>
                                    </div>
                                    <div class="col pseudo-element" pseudo-content="<fmt:message key="lang.city"/>">
                                        <select name="senderAddress.cityId" id="senderCitySelect" class="form-select">
                                            <c:forEach items="${requestScope.cities}" var="city">
                                                <option value="${city.id}" <c:if test="${senderCityId == city.id}">selected </c:if>>${city.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col pseudo-element validation-container" pseudo-content="<fmt:message key="lang.street"/>" <c:if test="${requestScope.senderStreetErrorMessage != null}" >data-error=${requestScope.senderStreetErrorMessage}</c:if>>
                                        <input class="form-control" type="text" name="senderAddress.streetName" value="${senderStreet}">
                                    </div>
                                    <div class="col pseudo-element validation-container" pseudo-content="<fmt:message key="lang.house-number"/>" <c:if test="${requestScope.senderHouseNumberErrorMessage != null}">data-error=${requestScope.senderHouseNumberErrorMessage}</c:if>>
                                        <input class="form-control" type="text" name="senderAddress.houseNumber" value="${senderHouseNumber}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col align-self-center">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="lang.receiver"/>:</label>
                                    </div>
                                    <div class="col pseudo-element" pseudo-content="<fmt:message key="lang.city"/>">
                                        <select name="receiverAddress.cityId" id="receiverCitySelect" class="form-select">
                                            <c:forEach items="${requestScope.cities}" var="city">
                                                <option value="${city.id}" <c:if test="${receiverCityId == city.id}"> selected </c:if>>${city.name}</option>
                                            </c:forEach>
                                        </select>
                                    </div>
                                    <div class="col pseudo-element validation-container" pseudo-content="<fmt:message key="lang.street"/>" <c:if test="${requestScope.receiverHouseNumberErrorMessage != null}">data-error=${requestScope.receiverHouseNumberErrorMessage}</c:if>>
                                        <input class="form-control" type="text" name="receiverAddress.streetName" value="${receiverStreet}">
                                    </div>
                                    <div class="col pseudo-element validation-container" pseudo-content="<fmt:message key="lang.house-number"/>" <c:if test="${requestScope.receiverHouseNumberErrorMessage != null}">data-error=${requestScope.receiverHouseNumberErrorMessage}</c:if>>
                                        <input class="form-control" type="text" name="receiverAddress.houseNumber"  value="${receiverHouseNumber}">
                                    </div>
                                </div>
                            </div>
                            <hr>
                            <div class="ms-4 me-4">
                                <div class="row mb-2">
                                    <div class="col-4">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="delivery-application.page.sending-date"/>:</label>
                                    </div>
                                    <div class="col-4 validation-container" <c:if test="${requestScope.sendingDateErrorMessage != null}">data-error="${requestScope.sendingDateErrorMessage}"</c:if>>
                                        <input type="date" class="form-control" name="sendingDate" value="${sendingDate}">
                                    </div>
                                </div>
                                <div class="row">
                                    <div class="col-4">
                                        <label class="fs-5 fw-bolder me-2"><fmt:message key="delivery-application.page.receiving-date"/>:</label>
                                    </div>
                                    <div class="col-4 validation-container" <c:if test="${requestScope.receivingDateErrorMessage != null}">data-error="${requestScope.receivingDateErrorMessage}"</c:if>>
                                        <input type="date" class="form-control" name="receivingDate" value="${receivingDate}">
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
                            <c:if test="${requestScope.application.customer != null}">
                                <c:set scope="request" var="customer" value="${requestScope.application.customer}"/>
                                <c:set scope="request" var="login" value="${requestScope.customer.login}"/>
                                <c:set scope="request" var="id" value="${requestScope.customer.id}"/>
                                <c:set scope="request" var="phone" value="${requestScope.customer.phone}"/>
                                <c:set scope="request" var="firstNameLastName" value="${requestScope.customer.name} ${requestScope.customer.surname}"/>
                                <c:set scope="request" var="customerAddress" value="${requestScope.customer.address}"/>
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
                                            <label>${cityName}", <fmt:message key="address.street"/> ${street}, <fmt:message key="address.house"/> ${houseNumber}</label>
                                        </div>
                                        <div class="col-auto mt-1">
                                            <label>${phone}</label>
                                        </div>
                                    </div>
                                </div>
                            </c:if>

                            <div class="row mt-4">
                                <div class="col d-flex justify-content-center">
                                    <div class="col-auto me-4">
                                        <button class="btn btn-outline-success" type="submit"><fmt:message key="lang.update"/></button>
                                    </div>
                                    <div class="col-auto">
                                        <a class="btn btn-outline-primary" href="${pageContext.request.contextPath}/applications/review?id=${applicationId}" type="submit"><fmt:message key="lang.cancel"/></a>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    </div>
</div>
<script src="${pageContext.request.contextPath}/static/js/formSubmit.js"></script>
<script src="${pageContext.request.contextPath}/static/js/localization.js"></script>
<script src="${pageContext.request.contextPath}/static/js/validationError.js"></script>

<script>
    addSwitchLanguageWithUrlClickListeners('${url}', ['applicationIdHiddenInput'])
    addRemoveErrorAttributeListener()
</script>
</body>
</html>
